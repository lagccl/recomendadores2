package cl.uc.psanabria.sysrec.work1;

import cl.uc.psanabria.sysrec.work1.data.RatingList;
import cl.uc.psanabria.sysrec.work1.data.RatingListFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("data/u.data");
        RatingList ratingList = RatingListFactory.createFrom(inputStream, false, "\t", 1, 0, 2);

        System.out.printf("Rating Count: %d\n", ratingList.size());
        System.out.printf("Users count: %d\n", ratingList.usersCount());
        System.out.printf("Items count: %d\n", ratingList.itemsCount());
    }
}
