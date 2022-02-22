package io.kubesphere.springcloud;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.api.model.KubernetesResource;
import java.util.List;
import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * Description: User: hongming Date: 2022-02-21 Time: 15:53
 */
@JsonDeserialize(
        using = JsonDeserializer.None.class
)
public class GatewayRouteConfigSpec implements KubernetesResource {
    private String gateway;
    private List<RouteDefinition> routes;

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public List<RouteDefinition> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteDefinition> routes) {
        this.routes = routes;
    }
}