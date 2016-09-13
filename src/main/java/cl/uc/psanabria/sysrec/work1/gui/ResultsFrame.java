package cl.uc.psanabria.sysrec.work1.gui;

import cl.uc.psanabria.sysrec.work1.data.RatingList;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ResultsFrame extends JFrame {

    public ResultsFrame(RatingList ratingList, File ratingFile, RatingList rankingList, File rankingFile, String dataFolderPath) {
        initComponents();
        tabbedPane.add("Summary", new SummaryPanel(ratingList));
        tabbedPane.add("Summary-Ranking", new SummaryPanel(rankingList));
        tabbedPane.add("Item-Rating", new ItemRatingPanel(ratingList));
        tabbedPane.add("User-Rating", new UserRatingPanel(ratingList));
        tabbedPane.add("Item-Ranking", new ItemRatingPanel(rankingList));
        tabbedPane.add("User-Ranking", new UserRatingPanel(rankingList));
        tabbedPane.add("Rating", new RatingStatistics(ratingFile, dataFolderPath, false, this));
        tabbedPane.add("Ranking", new RatingStatistics(rankingFile, dataFolderPath, true, this));
        add(tabbedPane);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        tabbedPane = new JTabbedPane();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Results");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        setSize(800, 600);
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JTabbedPane tabbedPane;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
