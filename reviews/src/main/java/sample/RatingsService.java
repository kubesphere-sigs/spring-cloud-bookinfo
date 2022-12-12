package sample;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "ratings-v1")
public interface RatingsService {
    @RequestMapping("/api/v1/reviews/{id}/ratings")
    Rating getRating(@PathVariable("id") int reviewId);
}
