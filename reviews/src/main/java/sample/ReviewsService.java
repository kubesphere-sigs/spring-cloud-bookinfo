package sample;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ReviewsService {

    private static List<Reviews> reviews = Arrays.asList(
            new Reviews(1, 1, "Reviewer1",
                    "An extremely entertaining play by Shakespeare. The slapstick humour is refreshing!"),
            new Reviews(2, 1, "Reviewer2",
                    "Absolutely fun and entertaining. The play lacks thematic depth when compared to other plays by Shakespeare."));

    public List<Reviews> ListReviewsByProductID(int productID) {
        return reviews.stream()
                .filter(reviews -> reviews.getProductID() == productID)
                .collect(Collectors.toList());
    }

}
