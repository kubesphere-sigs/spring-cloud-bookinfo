package sample;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RatingsService {

    private static List<Rating> ratingList = Arrays.asList(
            new Rating(1, 4),
            new Rating(2, 5)
    );

    public Rating getRating(int reviewId) {
        return ratingList.stream()
                .filter(rating -> rating.getReviewId() == reviewId)
                .findFirst()
                .orElse(new Rating(1,5));
    }

}
