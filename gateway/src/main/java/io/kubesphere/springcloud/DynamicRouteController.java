package io.kubesphere.springcloud;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DynamicRouteController implements ApplicationEventPublisherAware,
        ResourceEventHandler<GatewayRouteConfig>, Runnable {

    private static final Logger logger = LoggerFactory.getLogger(DynamicRouteController.class);

    private ExecutorService executorService;

    private final RouteDefinitionWriter routeDefinitionWriter;
    private ApplicationEventPublisher publisher;

    @Value("${spring.cloud.nacos.discovery.namespace}")
    private String namespace;

    public DynamicRouteController(RouteDefinitionWriter routeDefinitionWriter) {
        this.routeDefinitionWriter = routeDefinitionWriter;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {
        if (this.executorService == null) {
            this.executorService = Executors.newSingleThreadExecutor();
            logger.info("create executor service");
            this.executorService.submit(this);
        }
    }

    @EventListener(ContextClosedEvent.class)
    public void onContextClosedEvent(ContextClosedEvent event) {
        if (!this.executorService.isShutdown()) {
            logger.info("shutdown executor service");
            this.executorService.shutdown();
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public void onAdd(GatewayRouteConfig routeConfig) {
        if (!routeConfig.getMetadata().getNamespace().equals(namespace)) {
            return;
        }
        logger.info("{} route added", routeConfig.getMetadata().getName());
        for (RouteDefinition route : routeConfig.getSpec().getRoutes()) {
            routeDefinitionWriter.save(Mono.just(route)).subscribe();
        }
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }


    @Override
    public void onUpdate(GatewayRouteConfig oldRouteConfig,
            GatewayRouteConfig newRouteConfig) {
        if (!oldRouteConfig.getMetadata().getNamespace().equals(namespace)) {
            return;
        }
        logger.info("{} route updated", oldRouteConfig.getMetadata().getName());
        for (RouteDefinition route : oldRouteConfig.getSpec().getRoutes()) {
            routeDefinitionWriter.delete(Mono.just(route.getId())).subscribe();
        }
        for (RouteDefinition route : newRouteConfig.getSpec().getRoutes()) {
            routeDefinitionWriter.save(Mono.just(route)).subscribe();
        }
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public void onDelete(GatewayRouteConfig routeConfig,
            boolean deletedFinalStateUnknown) {
        if (!routeConfig.getMetadata().getNamespace().equals(namespace)) {
            return;
        }
        logger.info("{} route deleted", routeConfig.getMetadata().getName());
        for (RouteDefinition route : routeConfig.getSpec().getRoutes()) {
            routeDefinitionWriter.delete(Mono.just(route.getId())).subscribe();
        }
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public void run() {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            SharedInformerFactory sharedInformerFactory = client.informers();
            SharedIndexInformer<GatewayRouteConfig> informer = sharedInformerFactory
                    .sharedIndexInformerFor(GatewayRouteConfig.class, 5 * 60 * 1000L);
            logger.info("Informer factory initialized.");

            informer.addEventHandler(this);

            sharedInformerFactory.addSharedInformerEventListener(ex ->
                    logger.error("Exception occurred, but caught: {}", ex.getMessage()));

            logger.info("Starting all registered informers");
            sharedInformerFactory.startAllRegisteredInformers();

            while (!Thread.interrupted()) {
                logger.info("GatewayRouteConfigInformer.hasSynced() : {}",
                        informer.hasSynced());
                TimeUnit.MINUTES.sleep(5);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}