package cl.uc.psanabria.sysrec.work1;

import cl.uc.psanabria.sysrec.work1.data.RatingList;
import cl.uc.psanabria.sysrec.work1.data.RatingListFactory;
import cl.uc.psanabria.sysrec.work1.gui.RecSysSplashScreen;
import cl.uc.psanabria.sysrec.work1.gui.ResultsFrame;
import org.grouplens.lenskit.eval.TaskExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static final String TRAINING_RATING_FILE = "rating/training_rating.csv";
    public static final String TRAINING_RANKING_FILE = "ranking/training_ranking.csv";

    public static void main(String[] args) throws TaskExecutionException {
        RecSysSplashScreen splashScreen = new RecSysSplashScreen();
        String dataFolderPath = System.getProperty("dataPath", "data");
        startResultFrame(splashScreen, dataFolderPath);
    }

    private static void startResultFrame(RecSysSplashScreen splashScreen, String dataFolderPath) {
        FileInputStream inputStream = null;
        FileInputStream rankingInputStream = null;

        try {
            splashScreen.updateStatus("Reading Rating Training Set...");
            logger.info("Reading Ratings Training Set");
            logger.info("Setting data folder to: " + dataFolderPath);
            File ratingFile = new File(dataFolderPath, TRAINING_RATING_FILE);
            inputStream = new FileInputStream(ratingFile);
            RatingList ratingList = RatingListFactory.createFrom(inputStream, true, 1, 0, 3);

            splashScreen.updateStatus("Generating rating data summary graphics...");

            logger.info("Reading Ranking Training Set");
            splashScreen.updateStatus("Generating ranking data summary graphics...");

            File rankingFile = new File(dataFolderPath, TRAINING_RANKING_FILE);
            rankingInputStream = new FileInputStream(rankingFile);
            RatingList rankingList = RatingListFactory.createFrom(rankingInputStream, true, 1, 0, 2);
            logger.info("Generating Item/Score Average for ranking");
            ResultsFrame frame = new ResultsFrame(ratingList, ratingFile, rankingList, rankingFile, dataFolderPath);
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
