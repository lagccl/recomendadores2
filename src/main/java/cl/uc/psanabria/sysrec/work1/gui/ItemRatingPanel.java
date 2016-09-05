package cl.uc.psanabria.sysrec.work1.gui;

import javax.swing.border.LineBorder;
import cl.uc.psanabria.sysrec.work1.data.RatingList;
import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.data.EnumeratedData;
import de.erichseifert.gral.data.statistics.Histogram1D;
import de.erichseifert.gral.data.statistics.Statistics;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.Orientation;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.util.MathUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;


class ItemRatingPanel extends JPanel {
    ItemRatingPanel(RatingList ratingList) {
        initComponents();
        BarPlot frequencyPlot = createPlot(ratingList);

        InteractivePanel interactivePanel = new InteractivePanel(frequencyPlot);

        interactivePanel.setZoomable(false);
        interactivePanel.setPannable(false);

        graphicsPanel.add(interactivePanel, BorderLayout.CENTER);
    }

    @SuppressWarnings("unchecked")
    private BarPlot createPlot(RatingList ratingList) {
        DataTable data = new DataTable(Float.class);
        Collection<Integer> itemList = ratingList.getItemList();

        for(int item : itemList) {
            data.add(ratingList.getItemScoreAverage(item));
        }
        Histogram1D histogram = new Histogram1D(data, Orientation.VERTICAL, new Number[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0});
        DataSource histogram2D = new EnumeratedData(histogram, 1.5, 1.0);

        BarPlot plot = new BarPlot(histogram2D);

        plot.getTitle().setText("Item-Rating Frequency");
        plot.setInsets(new Insets2D.Double(20.0, 65.0, 60.0, 40.0));
        plot.getAxisRenderer(BarPlot.AXIS_X).setMinorTicksVisible(false);
        plot.getAxisRenderer(BarPlot.AXIS_X).getLabel().setText("Rating");
        plot.getAxis(BarPlot.AXIS_X).setMin(1);

        // Format y axis
        plot.getAxis(BarPlot.AXIS_Y).setMax(MathUtils.ceil(histogram.getStatistics().get(Statistics.MAX) * 1.1, 25.0));
        plot.getAxisRenderer(BarPlot.AXIS_Y).setTickAlignment(0.0);
        plot.getAxisRenderer(BarPlot.AXIS_Y).setMinorTicksVisible(false);
        plot.getAxisRenderer(BarPlot.AXIS_Y).getLabel().setText("Frequency");
        // Format bars
        PointRenderer barRenderer = plot.getPointRenderers(histogram2D).get(0);
        barRenderer.setColor(GraphicsUtils.deriveWithAlpha(Color.BLUE, 128));
        barRenderer.setValueVisible(true);
        txtAverage.setText(Double.toString(data.getStatistics().get(Statistics.MEAN)));
        double standardDeviation = Math.sqrt(data.getStatistics().get(Statistics.VARIANCE));
        txtStandarDeviation.setText(Double.toString(standardDeviation));

        return plot;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        JLabel label1 = new JLabel();
        txtAverage = new JTextField();
        JLabel label2 = new JLabel();
        txtStandarDeviation = new JTextField();
        graphicsPanel = new JPanel();

        //======== this ========

        //---- label1 ----
        label1.setText("Average");

        //---- txtAverage ----
        txtAverage.setEditable(false);

        //---- label2 ----
        label2.setText("Standard Deviaton");

        //---- txtStandarDeviation ----
        txtStandarDeviation.setEditable(false);

        //======== graphicsPanel ========
        {
            graphicsPanel.setBorder(LineBorder.createBlackLineBorder());
            graphicsPanel.setLayout(new BorderLayout());
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(label1)
                        .addComponent(label2))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(txtAverage, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtStandarDeviation, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(graphicsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(graphicsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(txtAverage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label1))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label2)
                                .addComponent(txtStandarDeviation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(168, 248, Short.MAX_VALUE))))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JTextField txtAverage;
    private JTextField txtStandarDeviation;
    private JPanel graphicsPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
