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

    public static void main(String[] args) throws TaskExecutionException {
        RecSysSplashScreen splashScreen = new RecSysSplashScreen();
        String dataFolderPath = System.getProperty("dataPath", "data");
        startResultFrame(splashScreen, dataFolderPath);
    }

    private static void startResultFrame(RecSysSplashScreen splashScreen, String dataFolderPath) {
        FileInputStream inputStream = null;

        try {
            splashScreen.updateStatus("Reading Rating Training Set...");
            logger.info("Reading Ratings Training Set");
            logger.info("Setting data folder to: " + dataFolderPath);
            File ratingFile = new File(dataFolderPath, TRAINING_RATING_FILE);
            inputStream = new FileInputStream(ratingFile);
            RatingList ratingList = RatingListFactory.createFrom(inputStream, true, 1, 0, 3);

            splashScreen.updateStatus("Generating rating data summary graphics...");
            logger.info("Generating Item/Score Average");
            ResultsFrame frame = new ResultsFrame(ratingList, ratingFile, dataFolderPath);
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
