package cl.uc.psanabria.sysrec.work1.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class RatingTest {
    @Test
    public void testRatingCreated() {
        Rating rating = new Rating();

        assertEquals(0, rating.getUser());
        assertEquals(0, rating.getItem());
        assertEquals(0.0, rating.getScore(), 0.01);
    }

    @Test
    public void testRatingCreatedWithArguments() {
        Rating rating = new Rating(3, 2, 4.5f);

        assertEquals(2, rating.getUser());
        assertEquals(3, rating.getItem());
        assertEquals(4.5, rating.getScore(), 0.01);
    }
}