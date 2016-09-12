package cl.uc.psanabria.sysrec.work1.data;

import org.grouplens.lenskit.RatingPredictor;
import org.grouplens.lenskit.RecommenderBuildException;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.core.LenskitRecommender;
import org.grouplens.lenskit.data.text.DelimitedColumnEventFormat;
import org.grouplens.lenskit.data.text.Formats;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RecommendationPredictionsGenerator {
    private LenskitConfiguration configuration;
    private File inputFile, outputFile;

    public RecommendationPredictionsGenerator(LenskitConfiguration configuration, File inputFile, File outputFile) {
        this.configuration = configuration;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void recommend() throws RecommenderBuildException, IOException {
        DelimitedColumnEventFormat format = Formats.csvRatings();
        LenskitRecommender recommender = LenskitRecommender.build(configuration);
        RatingPredictor predictor = recommender.getRatingPredictor();

        CSVReader reader = new CSVReader(new FileInputStream(inputFile));
        reader.next();
        while (reader.hasNext()) {
            String line[] = reader.next();
            int item = Integer.parseInt(line[0]);
            int styleID = Integer.parseInt(line[1]);
            int user = Integer.parseInt(line[2]);
            if (predictor != null)
                System.out.printf("%d,%d,%d,%.6f\n", item, styleID, user, predictor.predict(user, item));
        }
    }
}
