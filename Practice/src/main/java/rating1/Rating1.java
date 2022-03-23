package rating1;

import java.util.ArrayList;
import java.util.List;

public class Rating1 {

    double points;
    List<Review1> review1s = new ArrayList<>();

    public Rating1() {}

    public void add(Review1 review1) {
        if(review1.points + this.points < 500){
            review1s.add(review1);
            computeRating();
        }
    }

    private double computeRating() {
//        double totalPoints = review1s.stream().map(Review1::getPoints).reduce(0, Integer::sum);
        double totalPoints = review1s.stream()
                .reduce(0, (partialPriceResult, item) ->
                        partialPriceResult + item.getPoints() > 500 ?
                                partialPriceResult :
                                partialPriceResult + item.getPoints(), Integer::sum);

        this.points = totalPoints;
        return this.points;
    }

    public static Rating1 average(Rating1 r1, Rating1 r2) {
        Rating1 combined = new Rating1();
        combined.review1s = new ArrayList<>(r1.review1s);
        combined.review1s.addAll(r2.review1s);
        combined.computeRating();
        return combined;
    }

    public double getPoints() {
        return points;
    }

    public List<Review1> getReviews() {
        return review1s;
    }
}
