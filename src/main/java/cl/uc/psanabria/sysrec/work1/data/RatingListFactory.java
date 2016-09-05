package cl.uc.psanabria.sysrec.work1.data;

import java.io.InputStream;

public class RatingListFactory {
    public static RatingList createFrom(InputStream inputStream, boolean hasHeader) {
        CSVReader reader = new CSVReader(inputStream);

        return processCSVReader(reader, hasHeader, 0, 1, 2);
    }


    public static RatingList createFrom(InputStream inputStream, boolean hasHeader, String separator) {
        CSVReader reader = new CSVReader(inputStream, separator);

        return processCSVReader(reader, hasHeader, 0, 1, 2);
    }

    public static RatingList createFrom(InputStream inputStream, boolean hasHeader, int itemColumn, int userColumn, int ratingColumn) {
        CSVReader reader = new CSVReader(inputStream);

        return processCSVReader(reader, hasHeader, itemColumn, userColumn, ratingColumn);
    }

    public static RatingList createFrom(InputStream inputStream, boolean hasHeader, String separator, int itemColumn, int userColumn, int ratingColumn) {
        CSVReader reader = new CSVReader(inputStream, separator);

        return processCSVReader(reader, hasHeader, itemColumn, userColumn, ratingColumn);
    }

    private static RatingList processCSVReader(CSVReader csvReader, boolean hasHeader, int itemColumn, int userColumn, int ratingColumn) {
        RatingList ratingList = new RatingList();

        if (hasHeader && csvReader.hasNext())
            csvReader.next();

        while (csvReader.hasNext()) {
            String line[] = csvReader.next();
            Rating rating = new Rating(Integer.parseInt(line[itemColumn]),
                    Integer.parseInt(line[userColumn]),
                    Float.parseFloat(line[ratingColumn]));

            ratingList.addRating(rating);
        }

        return ratingList;
    }
}
