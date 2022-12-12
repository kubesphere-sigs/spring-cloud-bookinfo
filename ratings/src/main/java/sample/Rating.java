package sample;

import java.io.Serializable;

public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    private int reviewId;
    private int stars;

    public Rating(int reviewId, int stars) {
        this.reviewId = reviewId;
        this.stars = stars;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
