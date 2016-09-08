package cl.uc.psanabria.sysrec.work1;

import cl.uc.psanabria.sysrec.work1.data.RatingList;
import cl.uc.psanabria.sysrec.work1.data.RatingListFactory;
import cl.uc.psanabria.sysrec.work1.gui.RecSysSplashScreen;
import cl.uc.psanabria.sysrec.work1.gui.ResultsFrame;
import cl.uc.psanabria.sysrec.work1.recommender.AlgorithmConfiguratorFactory;
import cl.uc.psanabria.sysrec.work1.recommender.ConfigurationType;
import cl.uc.psanabria.sysrec.work1.recommender.SimilarityType;
import org.grouplens.lenskit.data.source.CSVDataSourceBuilder;
import org.grouplens.lenskit.data.text.DelimitedColumnEventFormat;
import org.grouplens.lenskit.data.text.Fields;
import org.grouplens.lenskit.data.text.Formats;
import org.grouplens.lenskit.eval.TaskExecutionException;
import org.grouplens.lenskit.eval.algorithm.AlgorithmInstance;
import org.grouplens.lenskit.eval.metrics.predict.RMSEPredictMetric;
import org.grouplens.lenskit.eval.traintest.SimpleEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static final String TRAINING_RATING_FILE = "rating/training_rating.csv";

    public static void main(String[] args) throws TaskExecutionException {
        RecSysSplashScreen splashScreen = new RecSysSplashScreen();
        String dataFolderPath = System.getProperty("dataPath", "data");

        SimpleEvaluator evaluator = new SimpleEvaluator();

        DelimitedColumnEventFormat format = Formats.csvRatings();

        format.setHeaderLines(1);
        format.setFields(Fields.user(), Fields.item(), Fields.ignored(), Fields.rating());
        CSVDataSourceBuilder sourceBuilder = new CSVDataSourceBuilder(new File(dataFolderPath, TRAINING_RATING_FILE));
        sourceBuilder.setFormat(format);

        evaluator.addDataset("RatingData-0.2", sourceBuilder.build(), 5, 0.2);
        evaluator.addDataset("RatingData-0.3", sourceBuilder.build(), 5, 0.3);
        evaluator.addDataset("RatingData-0.4", sourceBuilder.build(), 5, 0.4);
        evaluator.addDataset("RatingData-0.5", sourceBuilder.build(), 5, 0.5);

        evaluator.addAlgorithm(new AlgorithmInstance("UserKNN", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User)));
        evaluator.addAlgorithm(new AlgorithmInstance("UserKNN-5", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User, 5)));
        evaluator.addAlgorithm(new AlgorithmInstance("UserKNN-10", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User, 10)));
        evaluator.addAlgorithm(new AlgorithmInstance("UserKNN-15", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User, 15)));
        evaluator.addAlgorithm(new AlgorithmInstance("UserKNN-0.9 TSim", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User, 0.9)));
        evaluator.addAlgorithm(new AlgorithmInstance("UserKNN-0.8 TSim", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User, 0.8)));
        evaluator.addAlgorithm(new AlgorithmInstance("UserKNN-0.7 TSim", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User, 0.7)));
        evaluator.addAlgorithm(new AlgorithmInstance("UserKNN-0.7 TSim", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User, 0.6)));
        evaluator.addAlgorithm(new AlgorithmInstance("UserKNN-0.7 TSim", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User, 0.5)));
        evaluator.addAlgorithm(new AlgorithmInstance("UserKNN-Cosine", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User, SimilarityType.Cosine)));
        evaluator.addAlgorithm(new AlgorithmInstance("UserKNN-Pearson", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User, SimilarityType.Pearson)));
        evaluator.addAlgorithm(new AlgorithmInstance("UserKNN-Spearman", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User, SimilarityType.Spearman)));

        evaluator.addAlgorithm(new AlgorithmInstance("ItemKNN", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item)));
        evaluator.addAlgorithm(new AlgorithmInstance("ItemKNN-5", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item, 5)));
        evaluator.addAlgorithm(new AlgorithmInstance("ItemKNN-10", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item, 10)));
        evaluator.addAlgorithm(new AlgorithmInstance("ItemKNN-15", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item, 15)));
        evaluator.addAlgorithm(new AlgorithmInstance("ItemKNN-0.9 TSim", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item, 0.9)));
        evaluator.addAlgorithm(new AlgorithmInstance("ItemKNN-0.8 TSim", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item, 0.8)));
        evaluator.addAlgorithm(new AlgorithmInstance("ItemKNN-0.7 TSim", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item, 0.7)));
        evaluator.addAlgorithm(new AlgorithmInstance("ItemKNN-0.7 TSim", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item, 0.6)));
        evaluator.addAlgorithm(new AlgorithmInstance("ItemKNN-0.7 TSim", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item, 0.5)));
        evaluator.addAlgorithm(new AlgorithmInstance("ItemKNN-Cosine", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item, SimilarityType.Cosine)));
        evaluator.addAlgorithm(new AlgorithmInstance("ItemKNN-Pearson", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item, SimilarityType.Pearson)));
        evaluator.addAlgorithm(new AlgorithmInstance("ItemKNN-Spearman", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item, SimilarityType.Spearman)));


        evaluator.addMetric(RMSEPredictMetric.class);
        evaluator.setOutput(new File("results.csv"));

        evaluator.call();
      //  startResultFrame(splashScreen, dataFolderPath);
    }

    private static void startResultFrame(RecSysSplashScreen splashScreen, String dataFolderPath) {
        FileInputStream inputStream = null;

        try {
            splashScreen.updateStatus("Reading Rating Training Set...");
            logger.info("Reading Ratings Training Set");
            logger.info("Setting data folder to: " + dataFolderPath);
            inputStream = new FileInputStream(new File(dataFolderPath, TRAINING_RATING_FILE));
            RatingList ratingList = RatingListFactory.createFrom(inputStream, true, 1, 0, 3);

            splashScreen.updateStatus("Generating rating data summary graphics...");
            logger.info("Generating Item/Score Average");
            ResultsFrame frame = new ResultsFrame(ratingList);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            splashScreen.close();
            frame.setVisible(true);
        } catch (IOException ex) {
            logger.error("I/O exception: " + ex.getMessage(), ex);
        } finally {
            if (splashScreen.isOpen())
                splashScreen.close();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

}
