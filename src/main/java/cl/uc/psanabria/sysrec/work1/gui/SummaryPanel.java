package cl.uc.psanabria.sysrec.work1.gui;

import cl.uc.psanabria.sysrec.work1.data.RatingList;

import javax.swing.*;
import java.awt.*;

class SummaryPanel extends JPanel {
    SummaryPanel(RatingList ratingList) {
        initComponents();
        txtTotalItems.setText(Integer.toString(ratingList.itemsCount()));
        txtTotalUsers.setText(Integer.toString(ratingList.usersCount()));
        txtTotalRankings.setText(Integer.toString(ratingList.size()));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        JLabel label4 = new JLabel();
        JLabel label1 = new JLabel();
        txtTotalItems = new JTextField();
        JLabel label2 = new JLabel();
        txtTotalUsers = new JTextField();
        JLabel label3 = new JLabel();
        txtTotalRankings = new JTextField();

        //======== this ========
        setLayout(new GridBagLayout());

        //---- label4 ----
        label4.setText("Data Summary");
        label4.setFont(new Font("Dialog", Font.BOLD, 18));
        label4.setHorizontalAlignment(SwingConstants.CENTER);
        add(label4, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 15, 0), 0, 0));

        //---- label1 ----
        label1.setText("Total Items");
        label1.setFont(new Font("Dialog", Font.BOLD, 14));
        add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- txtTotalItems ----
        txtTotalItems.setColumns(15);
        txtTotalItems.setEditable(false);
        add(txtTotalItems, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label2 ----
        label2.setText("Total Users");
        label2.setFont(new Font("Dialog", Font.BOLD, 14));
        add(label2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- txtTotalUsers ----
        txtTotalUsers.setColumns(15);
        txtTotalUsers.setEditable(false);
        add(txtTotalUsers, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label3 ----
        label3.setText("Total Rankings");
        label3.setFont(new Font("Dialog", Font.BOLD, 14));
        add(label3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- txtTotalRankings ----
        txtTotalRankings.setColumns(15);
        txtTotalRankings.setEditable(false);
        add(txtTotalRankings, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JTextField txtTotalItems;
    private JTextField txtTotalUsers;
    private JTextField txtTotalRankings;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
