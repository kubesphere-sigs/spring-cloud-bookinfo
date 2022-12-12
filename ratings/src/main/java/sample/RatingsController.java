package sample;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class RatingsController {

    @Resource
    RatingsService ratingsService;

    @RequestMapping("/reviews/{id}/ratings")
    public Rating getRatings(@PathVariable("id") int reviewId) {
        return ratingsService.getRating(reviewId);
    }
}