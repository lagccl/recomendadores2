package cl.uc.psanabria.sysrec.work1.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.grouplens.lenskit.ItemRecommender;
import org.grouplens.lenskit.RatingPredictor;
import org.grouplens.lenskit.RecommenderBuildException;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.core.LenskitRecommender;
import org.grouplens.lenskit.scored.ScoredId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class RecommendationPredictionsGenerator {
    private LenskitConfiguration configuration;
    private File inputFile, outputFile;
    private static final Logger logger = LoggerFactory.getLogger(RecommendationPredictionsGenerator.class);

    public RecommendationPredictionsGenerator(LenskitConfiguration configuration, File inputFile, File outputFile) {
        this.configuration = configuration;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void predict(boolean isRanking) throws RecommenderBuildException, IOException {
        if (isRanking)
            rate();
        else
            recommend();
    }

    private void recommend() throws RecommenderBuildException, IOException {
        logger.info("Creating predictor");
        LenskitRecommender recommender = LenskitRecommender.build(configuration);
        RatingPredictor predictor = recommender.getRatingPredictor();
        PrintStream printStream = new PrintStream(outputFile);
        printStream.printf("%s,%s,%s\n","item_id","user_id","rating");
        CSVReader reader = new CSVReader(new FileInputStream(inputFile));
        reader.next();

        logger.info("Predicting recommendations");
        while (reader.hasNext()) {
            String line[] = reader.next();
            int item = Integer.parseInt(line[0]);
            int user = Integer.parseInt(line[2]);
            if (predictor != null)
                printStream.printf("%d,%d,%.6f\n", item, user, predictor.predict(user, item));
        }

        printStream.close();
        logger.info("Finished predictions");
    }

    private void rate() throws RecommenderBuildException, IOException {
        logger.info("Creating recommender");
        LenskitRecommender recommender = LenskitRecommender.build(configuration);
        ItemRecommender itemRecommender = recommender.getItemRecommender();
        CSVReader reader = new CSVReader(new FileInputStream(inputFile));
        JsonObject response = new JsonObject();

        reader.next();

        logger.info("Generating Top N");
        while (reader.hasNext()) {
            JsonArray predictionsJson = new JsonArray();

            String line[] = reader.next();
            int user = Integer.parseInt(line[0]);
            List<ScoredId> recommendations = itemRecommender.recommend(user, 10);
            for (ScoredId score : recommendations) {
                predictionsJson.add(score.getId());
            }
            response.add(String.valueOf(user), predictionsJson);
        }
        PrintStream printStream = new PrintStream(outputFile);

        printStream.print(response);
        printStream.close();
        logger.info("Finished predictions");
    }
}
