package sample;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class RatingsService {

    final RestTemplate restTemplate;

    public RatingsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Rating getRatingsByReviewsID(@PathVariable int reviewsID) {
        return restTemplate.getForObject(
                String.format("http://localhost:8003/api/v1/reviews/%d/ratings", reviewsID),
                Rating.class);
    }
}
