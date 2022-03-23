package coupon;

import java.util.ArrayList;
import java.util.List;

public class ItemAcumulator {

    double points;
    double pointsMax;
    List<Item> Items = new ArrayList<>();

    public ItemAcumulator() {}

    public void add(Item Item) {
        if(Item.points + this.points < this.pointsMax){
            Items.add(Item);
            computeRating();
        }
    }

    private double computeRating() {
//        double totalPoints = Items.stream().map(Item::getPoints).reduce(0, Integer::sum);
        double totalPoints = Items.stream()
                .reduce(0, (partialPriceResult, item) ->
                        partialPriceResult + item.getPoints() > this.pointsMax ?
                                partialPriceResult :
                                partialPriceResult + item.getPoints(), Integer::sum);

        this.points = totalPoints;
        return this.points;
    }

    public static ItemAcumulator average(ItemAcumulator r1, ItemAcumulator r2) {
        ItemAcumulator combined = new ItemAcumulator();
        combined.Items = new ArrayList<>(r1.Items);
        combined.Items.addAll(r2.Items);
        combined.computeRating();
        return combined;
    }

    public double getPoints() {
        return points;
    }

    public List<Item> getReviews() {
        return Items;
    }

    public double getPointsMax() {
        return pointsMax;
    }

    public void setPointsMax(double pointsMax) {
        this.pointsMax = pointsMax;
    }
}
