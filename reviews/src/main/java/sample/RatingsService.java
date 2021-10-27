package sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class RatingsService {

    @Value("${ratings.server-addr:http://ratings}")
    String serverAddr;

    final RestTemplate restTemplate;

    public RatingsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Rating getRatingsByReviewsID(@PathVariable int reviewsID) {
        return restTemplate.getForObject(
                String.format("%s/api/v1/reviews/%d/ratings", serverAddr, reviewsID),
                Rating.class);
    }
}
