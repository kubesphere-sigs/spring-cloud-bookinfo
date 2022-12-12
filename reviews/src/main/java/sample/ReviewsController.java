package sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReviewsController {

    private final static Boolean RATINGS_ENABLED = Boolean.valueOf(System.getenv("ENABLE_RATINGS"));
    private final static String STAR_COLOR = System.getenv("STAR_COLOR") == null ? "black" : System.getenv("STAR_COLOR");

    @Resource
    private ReviewsService reviewsService;

    @Resource
    private RatingsService ratingsService;

    @RequestMapping("/products/{id}/reviews")
    public List<Review> getReviews(@PathVariable("id") int productId) {
        List<Review> reviews = reviewsService.listReviewsByProductId(productId);
        if (RATINGS_ENABLED) {
            reviews.forEach(item -> {
                Rating rating = ratingsService.getRating(item.getId());
                rating.setColor(STAR_COLOR);
                item.setRating(rating);
            });
        }
        return reviews;
    }
}