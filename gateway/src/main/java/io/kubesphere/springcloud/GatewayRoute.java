package io.kubesphere.springcloud;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.api.model.KubernetesResource;
import java.util.List;

/**
 * Description: User: hongming Date: 2022-02-21 Time: 15:56
 */
@JsonDeserialize(
        using = JsonDeserializer.None.class
)
public class GatewayRoute implements KubernetesResource {
   private String id;
   private String uri;
   private List<JsonNode> filters;
   private List<JsonNode> predicates;
   private int order;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getUri() {
      return uri;
   }

   public void setUri(String uri) {
      this.uri = uri;
   }

   public List<JsonNode> getFilters() {
      return filters;
   }

   public void setFilters(List<JsonNode> filters) {
      this.filters = filters;
   }

   public List<JsonNode> getPredicates() {
      return predicates;
   }

   public void setPredicates(List<JsonNode> predicates) {
      this.predicates = predicates;
   }

   public int getOrder() {
      return order;
   }

   public void setOrder(int order) {
      this.order = order;
   }
}
