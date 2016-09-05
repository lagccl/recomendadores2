package cl.uc.psanabria.sysrec.work1.data;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RatingListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateEmptyList() {
        RatingList ratingList = new RatingList();

        assertEquals(0, ratingList.size());
        assertEquals(0, ratingList.usersCount());
        assertEquals(0, ratingList.itemsCount());
    }

    @Test
    public void testAddNewRating() {
        RatingList ratingList = new RatingList();
        Rating rating = new Rating(1, 1, 3.0f);

        ratingList.addRating(rating);

        assertEquals(1, ratingList.size());
        assertEquals(1, ratingList.usersCount());
        assertEquals(1, ratingList.itemsCount());
    }

    @Test
    public void testAddTwoRatingsForSameItem() {
        RatingList ratingList = new RatingList();
        Rating rating1 = new Rating(1, 1, 3.0f);
        Rating rating2 = new Rating(1, 2, 2.0f);

        ratingList.addRating(rating1);
        ratingList.addRating(rating2);

        assertEquals(2, ratingList.size());
        assertEquals(2, ratingList.usersCount());
        assertEquals(1, ratingList.itemsCount());
    }

    @Test
    public void testAddTwoRatingsForSameUser() {
        RatingList ratingList = new RatingList();
        Rating rating1 = new Rating(1, 1, 3.0f);
        Rating rating2 = new Rating(2, 1, 2.0f);

        ratingList.addRating(rating1);
        ratingList.addRating(rating2);

        assertEquals(2, ratingList.size());
        assertEquals(1, ratingList.usersCount());
        assertEquals(2, ratingList.itemsCount());
    }

    @Test
    public void testAddTwoRatingsForSameUserAndSameItem() {
        RatingList ratingList = new RatingList();
        Rating rating1 = new Rating(1, 1, 3.0f);
        Rating rating2 = new Rating(1, 1, 2.0f);

        ratingList.addRating(rating1);
        ratingList.addRating(rating2);

        assertEquals(1, ratingList.size());
        assertEquals(1, ratingList.usersCount());
        assertEquals(1, ratingList.itemsCount());
    }

    @Test
    public void testRemoveRatingFromUserAndItem() {
        RatingList ratingList = new RatingList();
        Rating rating = new Rating(1, 1, 3.0f);

        ratingList.addRating(rating);

        assertEquals(1, ratingList.size());

        ratingList.removeRating(1, 1);

        assertEquals(0, ratingList.size());
        assertEquals(0, ratingList.usersCount());
        assertEquals(0, ratingList.itemsCount());
    }

    @Test
    public void testTryToRemoveUnExistentRatingFromUserAndItem() {
        RatingList ratingList = new RatingList();
        Rating rating = new Rating(1, 1, 3.0f);

        ratingList.addRating(rating);

        assertEquals(1, ratingList.size());

        thrown.expect(NonExistentRatingException.class);
        thrown.expectMessage(String.format(NonExistentRatingException.MESSAGE_FORMAT, 2, 2));
        ratingList.removeRating(2, 2);
    }

    @Test
    public void testTryToRemoveUnExistentRatingFromUser() {
        RatingList ratingList = new RatingList();
        Rating rating1 = new Rating(1, 1, 3.0f);
        Rating rating2 = new Rating(2, 1, 3.0f);

        ratingList.addRating(rating1);
        ratingList.addRating(rating2);

        assertEquals(2, ratingList.size());

        thrown.expect(NonExistentRatingException.class);
        thrown.expectMessage(String.format(NonExistentRatingException.MESSAGE_FORMAT, 2, 2));
        ratingList.removeRating(2, 2);
    }

    @Test
    public void testGetNullForNonExistentSavedRating() {
        RatingList ratingList = new RatingList();

        Rating savedRating = ratingList.getRating(1, 1);

        assertNull(savedRating);
    }

    @Test
    public void testGetRatingForSavedRating() {
        RatingList ratingList = new RatingList();
        Rating rating = new Rating(1, 1, 3.0f);

        ratingList.addRating(rating);

        Rating savedRating = ratingList.getRating(1, 1);

        assertEquals(1, savedRating.getItem());
        assertEquals(1, savedRating.getUser());
        assertEquals(3.0f, savedRating.getScore(), 0.01f);
    }

    @Test
    public void testGetItemAverageRatingForNonExistentItem() {
        RatingList ratingList = new RatingList();

        assertEquals(0.0f, ratingList.getItemScoreAverage(1), 0.01f);
    }

    @Test
    public void testGetItemAverageRatingForOneScore() {
        RatingList ratingList = new RatingList();
        Rating rating = new Rating(1, 1, 3.0f);

        ratingList.addRating(rating);
        assertEquals(3.0, ratingList.getItemScoreAverage(1), 0.01);
    }

    @Test
    public void testGetItemAverageRatingForMoreThanOneScore() {
        RatingList ratingList = new RatingList();
        Rating rating1 = new Rating(1, 1, 3.0f);
        Rating rating2 = new Rating(1, 2, 1.0f);

        ratingList.addRating(rating1);
        ratingList.addRating(rating2);

        assertEquals(2.0, ratingList.getItemScoreAverage(1), 0.01);
    }

    @Test
    public void testGetUserAverageForNonExistentUser() {
        RatingList ratingList = new RatingList();

        assertEquals(0.0f, ratingList.getUserScoreAverage(1), 0.01f);
    }

    @Test
    public void testGetUserAverageForOneItem() {
        RatingList ratingList = new RatingList();
        Rating rating = new Rating(1, 1, 3.0f);

        ratingList.addRating(rating);
        assertEquals(3.0, ratingList.getUserScoreAverage(1), 0.01);
    }

    @Test
    public void testGetUserAverageRatingForMoreThanOneScore() {
        RatingList ratingList = new RatingList();
        Rating rating1 = new Rating(1, 1, 3.0f);
        Rating rating2 = new Rating(2, 1, 1.0f);

        ratingList.addRating(rating1);
        ratingList.addRating(rating2);

        assertEquals(2.0, ratingList.getUserScoreAverage(1), 0.01);
    }

    @Test
    public void testGetUserAveragesAndItemsAverageForMixedItems() {
        RatingList ratingList = new RatingList();
        Rating rating1 = new Rating(1, 1, 3.0f);
        Rating rating2 = new Rating(1, 2, 5.0f);
        Rating rating3 = new Rating(2, 1, 1.0f);

        ratingList.addRating(rating1);
        ratingList.addRating(rating2);
        ratingList.addRating(rating3);

        assertEquals(4.0, ratingList.getItemScoreAverage(1), 0.01);
        assertEquals(1.0, ratingList.getItemScoreAverage(2), 0.01);
        assertEquals(2.0, ratingList.getUserScoreAverage(1), 0.01);
        assertEquals(5.0, ratingList.getUserScoreAverage(2), 0.01);
    }

    @Test
    public void testGetItemList() {
        RatingList ratingList = new RatingList();
        Rating rating1 = new Rating(2, 1, 1.0f);
        Rating rating2 = new Rating(1, 1, 3.0f);
        Rating rating3 = new Rating(1, 2, 5.0f);

        ratingList.addRating(rating1);
        ratingList.addRating(rating2);
        ratingList.addRating(rating3);

        Collection<Integer> itemList = ratingList.getItemList();

        assertEquals(2, itemList.size());
        assertArrayEquals(new Integer[]{1, 2}, itemList.toArray(new Integer[itemList.size()]));
    }

    @Test
    public void testGetUserList() {
        RatingList ratingList = new RatingList();
        Rating rating1 = new Rating(2, 1, 1.0f);
        Rating rating2 = new Rating(1, 2, 5.0f);
        Rating rating3 = new Rating(1, 1, 3.0f);

        ratingList.addRating(rating1);
        ratingList.addRating(rating2);
        ratingList.addRating(rating3);

        Collection<Integer> userList = ratingList.getUserList();

        assertEquals(2, userList.size());
        assertArrayEquals(new Integer[]{1, 2}, userList.toArray(new Integer[userList.size()]));
    }

    @Test
    public void testGetRatingsFromNonExistentItem() {
        RatingList ratingList = new RatingList();
        Rating rating1 = new Rating(1, 1, 1.0f);
        Rating rating2 = new Rating(1, 2, 5.0f);
        Rating rating3 = new Rating(2, 1, 3.0f);

        ratingList.addRating(rating1);
        ratingList.addRating(rating2);
        ratingList.addRating(rating3);

        Collection<Rating> ratings = ratingList.getRatingsFromItem(3);

        assertEquals(0, ratings.size());
    }

    @Test
    public void testGetRatingsFromItem() {
        RatingList ratingList = new RatingList();
        Rating rating1 = new Rating(1, 1, 1.0f);
        Rating rating2 = new Rating(1, 2, 5.0f);
        Rating rating3 = new Rating(2, 1, 3.0f);

        ratingList.addRating(rating1);
        ratingList.addRating(rating2);
        ratingList.addRating(rating3);

        Collection<Rating> ratings = ratingList.getRatingsFromItem(1);

        assertEquals(2, ratings.size());
        assertArrayEquals(new Rating[] {rating1, rating2}, ratings.toArray());
    }

    @Test
    public void testGetRatingsFromNonExistentUser() {
        RatingList ratingList = new RatingList();
        Rating rating1 = new Rating(1, 1, 1.0f);
        Rating rating2 = new Rating(1, 2, 5.0f);
        Rating rating3 = new Rating(2, 1, 3.0f);

        ratingList.addRating(rating1);
        ratingList.addRating(rating2);
        ratingList.addRating(rating3);

        Collection<Rating> ratings = ratingList.getRatingsFromUser(3);

        assertEquals(0, ratings.size());
    }

    @Test
    public void testGetRatingsFromUser() {
        RatingList ratingList = new RatingList();
        Rating rating1 = new Rating(1, 1, 1.0f);
        Rating rating2 = new Rating(1, 2, 5.0f);
        Rating rating3 = new Rating(2, 1, 3.0f);

        ratingList.addRating(rating1);
        ratingList.addRating(rating2);
        ratingList.addRating(rating3);

        Collection<Rating> ratings = ratingList.getRatingsFromUser(1);

        assertEquals(2, ratings.size());
        assertArrayEquals(new Rating[] {rating1, rating3}, ratings.toArray());
    }
}