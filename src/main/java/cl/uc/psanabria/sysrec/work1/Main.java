package cl.uc.psanabria.sysrec.work1;

import cl.uc.psanabria.sysrec.work1.data.RatingList;
import cl.uc.psanabria.sysrec.work1.data.RatingListFactory;
import cl.uc.psanabria.sysrec.work1.gui.RecSysSplashScreen;
import cl.uc.psanabria.sysrec.work1.gui.ResultsFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        RecSysSplashScreen splashScreen = new RecSysSplashScreen();
        FileInputStream inputStream = null;
        String dataFolderPath = System.getProperty("dataPath", "data");

        try {
            splashScreen.updateStatus("Reading Rating Training Set...");
            logger.info("Reading Ratings Training Set");
            logger.info("Setting data folder to: " + dataFolderPath);
            inputStream = new FileInputStream(new File(dataFolderPath, "rating/training_rating.csv"));
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
