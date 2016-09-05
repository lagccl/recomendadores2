package cl.uc.psanabria.sysrec.work1.gui;

import cl.uc.psanabria.sysrec.work1.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class RecSysSplashScreen {
    private SplashScreen splash;
    private Graphics2D graphics;
    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    public RecSysSplashScreen() {
        splash = SplashScreen.getSplashScreen();

        if (splash == null) {
            logger.warn("SplashScreen.getSplashScreen() returned null");
        }

        if (splash != null)
            graphics = splash.createGraphics();

        if (graphics == null) {
            logger.warn("graphics for SplashScreen is null");
        }
    }

    public void updateStatus(String loadingText) {
        if (graphics == null) {
            logger.warn("graphics for SplashScreen is null, skipping");
            return;
        }

        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(100, 90, 200, 30);
        graphics.setPaintMode();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("SansSerif", Font.BOLD, 10));
        graphics.drawString(loadingText, 105, 100);
        splash.update();
    }

    public void close() {
        if (splash == null) {
            logger.warn("Splash screen was never initialized, skipping");
            return;
        }

        splash.close();
    }

    public boolean isOpen() {
        if (splash == null) {
            logger.warn("Splash screen was never initialized, skipping");
            return false;
        }

        return splash.isVisible();
    }
}
