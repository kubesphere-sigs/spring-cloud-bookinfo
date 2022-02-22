package io.kubesphere.springcloud;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Group(GatewayRouteConfig.GROUP)
@Version(GatewayRouteConfig.VERSION)
public class GatewayRouteConfig extends CustomResource<GatewayRouteConfigSpec,Void> implements Namespaced {
    public static final String GROUP = "springcloud.kubesphere.io";
    public static final String VERSION = "v1alpha1";
}