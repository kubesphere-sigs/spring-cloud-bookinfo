package sample;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ReviewsService {

    private static List<Review> reviews = Arrays.asList(
            new Review(1, 1, "Reviewer1",
                    "An extremely entertaining play by Shakespeare. The slapstick humour is refreshing!"),
            new Review(2, 1, "Reviewer2",
                    "Absolutely fun and entertaining. The play lacks thematic depth when compared to other plays by Shakespeare."));

    public List<Review> listReviewsByProductId(int productId) {
        return reviews.stream()
                .filter(review -> review.getProductId() == productId)
                .collect(Collectors.toList());
    }

}
