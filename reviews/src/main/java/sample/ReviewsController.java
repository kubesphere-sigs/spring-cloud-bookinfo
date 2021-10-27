package sample;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ReviewsController {

    @Value("${enableRatings}")
    boolean enableRatings;

    final ReviewsService reviewsService;

    private final RatingsService ratingsService;

    public ReviewsController(ReviewsService reviewsService,
            RatingsService ratingsService) {
        this.reviewsService = reviewsService;
        this.ratingsService = ratingsService;
    }

    @RequestMapping("/products/{id}/reviews")
    public List<Reviews> getReviews(@PathVariable final int id) {
        List<Reviews> reviews = reviewsService.ListReviewsByProductID(id);
        if (enableRatings) {
            reviews.forEach(item -> {
                item.setRating(ratingsService.getRatingsByReviewsID(item.getId()));
            });
        }
        return reviews;
    }
}