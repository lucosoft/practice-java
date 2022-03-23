package coupon;

import org.junit.Test;
import rating1.Rating1;
import rating1.Review1;
import rating1.User1;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CouponTest {

    @Test
    public void givenUserListWithRatings_whenReduceWithGreaterAgeAccumulator_thenFindsOldest() {
//        User1 john = new User1("John", 30);
        ItemAcumulator acumulator = new ItemAcumulator();
        acumulator.setPointsMax(500);
        acumulator.add(new Item(100, "MLA1"));
        acumulator.add(new Item(210, "MLA2"));
        acumulator.add(new Item(260, "MLA3"));
        acumulator.add(new Item(80, "MLA4"));
        acumulator.add(new Item(90, "MLA5"));
//        User1 julie = new User1("Julie", 35);
//        john.getRating().add(new Review1(4, "great!"));
//        john.getRating().add(new Review1(2, "terrible experience"));
//        john.getRating().add(new Review1(4, ""));
//        List<User1> users = Arrays.asList(john, julie);
//        List<User1> users = Arrays.asList(john);


//        List<Item> itemList = new ArrayList<>();
//        itemList.add(new Item("MLA1", 100));
//        itemList.add(new Item("MLA2", 210));
//        itemList.add(new Item("MLA3", 260));
//        itemList.add(new Item("MLA4", 80));
//        itemList.add(new Item("MLA5", 90));

//        Rating1 averageRating = users.stream().reduce(new Rating1(), (rating, user) -> Rating1.average(rating, user.getRating()), Rating1::average);

        assertTrue(true);
//        assertEquals(acumulator.points, 480.0);
        assertThat(acumulator.getPoints()).isEqualTo(480.0);
    }
}