package sample;

import java.io.Serializable;

public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    private int reviewID;
    private int stars;
    private String color;

    public Rating() {
    }

    public Rating(int reviewID, int stars, String color) {
        this.reviewID = reviewID;
        this.stars = stars;
        this.color = color;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
