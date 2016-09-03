package cl.uc.psanabria.sysrec.work1.data;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class RatingListFactoryTest {
    @Test
    public void createEmptyListFromEmptyCSV() {
        RatingList list = RatingListFactory.createFrom(new ByteArrayInputStream("".getBytes()), false);

        assertEquals(0, list.size());
    }

    @Test
    public void getListFromStandardCSV() {
        String standardCSV = "1,1,3.0\n1,2,1.0";
        RatingList list = RatingListFactory.createFrom(new ByteArrayInputStream(standardCSV.getBytes()), false);

        assertEquals(2, list.size());
        assertEquals(1, list.itemsCount());
        assertEquals(2, list.usersCount());
        assertEquals(2.0, list.getItemScoreAverage(1), 0.001);
    }

    @Test
    public void getListFromStandardCSVWithDifferentSeparator() {
        String standardCSV = "1\t1\t3.0\n1\t2\t1.0";
        RatingList list = RatingListFactory.createFrom(new ByteArrayInputStream(standardCSV.getBytes()), false, "\t");

        assertEquals(2, list.size());
        assertEquals(1, list.itemsCount());
        assertEquals(2, list.usersCount());
        assertEquals(2.0, list.getItemScoreAverage(1), 0.001);
    }

    @Test
    public void getListFromStandardCSVWithHeader() {
        String standardCSV = "item,user,rating\n1,1,3.0\n1,2,1.0";
        RatingList list = RatingListFactory.createFrom(new ByteArrayInputStream(standardCSV.getBytes()), true);

        assertEquals(2, list.size());
        assertEquals(1, list.itemsCount());
        assertEquals(2, list.usersCount());
        assertEquals(2.0, list.getItemScoreAverage(1), 0.001);
    }

    @Test
    public void getEmptyListFromCSVWithHeader() {
        RatingList list = RatingListFactory.createFrom(new ByteArrayInputStream("item,user,rating".getBytes()), true);

        assertEquals(0, list.size());
    }

    @Test
    public void getEmptyListFromCSVWithHeaderAndNoText() {
        RatingList list = RatingListFactory.createFrom(new ByteArrayInputStream("".getBytes()), true);

        assertEquals(0, list.size());
    }

    @Test
    public void getListFromNonStandardCSV() {
        String nonStandardCSV = "user,item,rating\n1,1,3.0\n2,1,1.0";
        RatingList list = RatingListFactory.createFrom(new ByteArrayInputStream(nonStandardCSV.getBytes()), true, 1, 0, 2);

        assertEquals(2, list.size());
        assertEquals(1, list.itemsCount());
        assertEquals(2, list.usersCount());
        assertEquals(2.0, list.getItemScoreAverage(1), 0.001);
    }

    @Test
    public void getListFromNonStandardCSVAndSeparator() {
        String nonStandardCSV = "user\titem\trating\n1\t1\t3.0\n2\t1\t1.0";
        RatingList list = RatingListFactory.createFrom(new ByteArrayInputStream(nonStandardCSV.getBytes()), true, "\t", 1, 0, 2);

        assertEquals(2, list.size());
        assertEquals(1, list.itemsCount());
        assertEquals(2, list.usersCount());
        assertEquals(2.0, list.getItemScoreAverage(1), 0.001);
    }
}