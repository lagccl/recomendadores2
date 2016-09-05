package cl.uc.psanabria.sysrec.work1.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import cl.uc.psanabria.sysrec.work1.data.RatingList;

import javax.swing.*;

public class ResultsFrame extends JFrame {

    public ResultsFrame(RatingList ratingList) {
        initComponents();
        tabbedPane.add("Summary", new SummaryPanel(ratingList));
        tabbedPane.add("Item-Rating", new ItemRatingPanel(ratingList));
        tabbedPane.add("User-Rating", new UserRatingPanel(ratingList));
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
