/*
 * Created by JFormDesigner on Sun Sep 11 16:58:36 CLST 2016
 */

package cl.uc.psanabria.sysrec.work1.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cl.uc.psanabria.sysrec.work1.data.RecommendationPredictionsGenerator;
import cl.uc.psanabria.sysrec.work1.recommender.*;
import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.data.Row;
import de.erichseifert.gral.data.RowSubset;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.Location;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.data.dao.EventDAO;
import org.grouplens.lenskit.data.text.DelimitedColumnEventFormat;
import org.grouplens.lenskit.data.text.Fields;
import org.grouplens.lenskit.data.text.Formats;
import org.grouplens.lenskit.data.text.TextEventDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Pablo Sanabria
 */
class RatingStatistics extends JPanel {
    private File ratingFile;
    private Logger logger = LoggerFactory.getLogger(RatingStatistics.class);
    private Map<String, LenskitConfiguration> algorithms;
    private JFrame parentFrame;
    private File inputFile, outputFile;
    private String dataFolderPath;
    private boolean isRanking;

    @SuppressWarnings("unchecked")
    RatingStatistics(File ratingFile, String dataFolderPath, boolean isRanking, JFrame parentFrame) {
        initComponents();
        this.ratingFile = ratingFile;
        this.parentFrame = parentFrame;
        this.dataFolderPath = dataFolderPath;
        this.isRanking = isRanking;
        algorithms = generateAlgorithms();
        for (String algorithm : algorithms.keySet()) {
            algorithmTypeComboBox.addItem(algorithm);
        }
        inputFile = null;
        outputFile = null;
        if (isRanking)
            predictButton.setText("Generate TopN");
    }

    private void trainingButtonActionPerformed() {
        parentFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        trainingProgressBar.setValue(0);
        trainingButton.setText("Testing...");
        trainingButton.setEnabled(false);
        ratingDataFileTextField.setEnabled(false);
        algorithmTypeComboBox.setEnabled(false);
        outputFileTextBox.setEnabled(false);
        predictButton.setEnabled(false);
        TrainerWorker worker = new TrainerWorker();

        worker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("progress")) {
                    trainingProgressBar.setValue((int) evt.getNewValue());
                }
            }
        });
        worker.execute();
        JOptionPane.showMessageDialog(this, "Wait until we run the evaluations", "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void fileTextFieldMouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && e.getComponent().isEnabled()) {
            JFileChooser fileChooser = new JFileChooser(dataFolderPath);
            int returnValue;
            if (e.getComponent() == outputFileTextBox) {
                returnValue = fileChooser.showSaveDialog(this);
            } else {
                returnValue = fileChooser.showOpenDialog(this);
            }

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                if (e.getComponent() == ratingDataFileTextField) {
                    inputFile = fileChooser.getSelectedFile();
                    ratingDataFileTextField.setText(inputFile.getAbsolutePath());
                    logger.info("File " + inputFile.getAbsolutePath() + " selected as input file");
                } else if (e.getComponent() == outputFileTextBox) {
                    outputFile = fileChooser.getSelectedFile();
                    outputFileTextBox.setText(outputFile.getAbsolutePath());
                    logger.info("File " + outputFile.getAbsolutePath() + " selected as output file");
                }
            }
        }
    }

    private void predictButtonActionPerformed() {
        trainingButton.setEnabled(false);
        ratingDataFileTextField.setEnabled(false);
        algorithmTypeComboBox.setEnabled(false);
        outputFileTextBox.setEnabled(false);
        predictButton.setEnabled(false);
        predictButton.setText("Rating...");
        RaterWorker worker = new RaterWorker();
        worker.execute();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        trainingButton = new JButton();
        JLabel label2 = new JLabel();
        ratingDataFileTextField = new JTextField();
        JLabel label3 = new JLabel();
        algorithmTypeComboBox = new JComboBox();
        JLabel label4 = new JLabel();
        outputFileTextBox = new JTextField();
        predictButton = new JButton();
        JSeparator separator1 = new JSeparator();
        graphicsPanel = new JPanel();
        trainingProgressBar = new JProgressBar();

        //======== this ========

        //---- trainingButton ----
        trainingButton.setText("Start training");
        trainingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trainingButtonActionPerformed();
            }
        });

        //---- label2 ----
        label2.setText("Rating data file:");
        label2.setLabelFor(ratingDataFileTextField);

        //---- ratingDataFileTextField ----
        ratingDataFileTextField.setEditable(false);
        ratingDataFileTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fileTextFieldMouseClicked(e);
            }
        });

        //---- label3 ----
        label3.setText("Method to use:");
        label3.setLabelFor(algorithmTypeComboBox);

        //---- label4 ----
        label4.setText("Output file:");
        label4.setLabelFor(outputFileTextBox);

        //---- outputFileTextBox ----
        outputFileTextBox.setEditable(false);
        outputFileTextBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fileTextFieldMouseClicked(e);
            }
        });

        //---- predictButton ----
        predictButton.setText("Predict Ratings");
        predictButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                predictButtonActionPerformed();
            }
        });

        //======== graphicsPanel ========
        {
            graphicsPanel.setLayout(new GridLayout(2, 2, 5, 5));
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup()
                        .addComponent(separator1, GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                        .addComponent(graphicsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addComponent(label2)
                                .addComponent(label4))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(outputFileTextBox, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                .addComponent(ratingDataFileTextField))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(label3)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(algorithmTypeComboBox, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
                                .addComponent(predictButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(trainingButton)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(trainingProgressBar, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(trainingButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(trainingProgressBar, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                    .addGap(18, 18, 18)
                    .addComponent(separator1, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label2)
                        .addComponent(ratingDataFileTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label3)
                        .addComponent(algorithmTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label4)
                        .addComponent(outputFileTextBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(predictButton))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(graphicsPanel, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                    .addContainerGap())
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JButton trainingButton;
    private JTextField ratingDataFileTextField;
    private JComboBox algorithmTypeComboBox;
    private JTextField outputFileTextBox;
    private JButton predictButton;
    private JPanel graphicsPanel;
    private JProgressBar trainingProgressBar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    @SuppressWarnings("unchecked")
    private void generateResultGraphics(List<EvaluationResult> resultList) {
        DataTable dataRMSE = new DataTable(String.class, Double.class, Double.class);
        DataTable dataTopMAP = new DataTable(String.class, Double.class, Double.class);
        DataTable dataTopNDCG = new DataTable(String.class, Double.class, Double.class);
        DataTable dataTime = new DataTable(String.class, Double.class, Double.class);
        Set<String> dataSetsNames = new TreeSet<>();

        for (EvaluationResult result : resultList) {
            dataRMSE.add(result.getTestName(), result.getX(), result.getRMSE());
            dataTopMAP.add(result.getTestName(), result.getX(), result.getTopMap());
            dataTopNDCG.add(result.getTestName(), result.getX(), result.getTopNDCG());
            dataTime.add(result.getTestName(), result.getX(), result.getTime());
            dataSetsNames.add(result.getTestName());
        }

        graphicsPanel.removeAll();
        graphicsPanel.add(new InteractivePanel(generatePlot(dataSetsNames, dataRMSE, "RMSE", "RMSE")));
        graphicsPanel.add(new InteractivePanel(generatePlot(dataSetsNames, dataTopMAP, "Top MAP", "TopMAP")));
        graphicsPanel.add(new InteractivePanel(generatePlot(dataSetsNames, dataTopNDCG, "Top nDCG", "TopNDCG")));
        graphicsPanel.add(new InteractivePanel(generatePlot(dataSetsNames, dataTime, "Total time", "Elapsed time")));
        graphicsPanel.revalidate();
        graphicsPanel.repaint();
    }

    private XYPlot generatePlot(Set<String> dataSetNames, DataTable table, String title, String yLabel) {
        XYPlot plot = new XYPlot();
        Random random = new Random();

        double insetsTop = 20.0,
                insetsLeft = 60.0,
                insetsBottom = 60.0,
                insetsRight = 140.0;
        plot.setInsets(new Insets2D.Double(
                insetsTop, insetsLeft, insetsBottom, insetsRight));
        plot.getTitle().setText(title);
        for (final String dataSetName : dataSetNames) {
            DataSeries dataSeries = new DataSeries(dataSetName, new RowSubset(table) {
                @Override
                public boolean accept(Row row) {
                    return row.get(0).equals(dataSetName);
                }
            }, 1, 2);

            Color seriesColor = Color.getHSBColor(random.nextFloat(), 0.70f, 0.70f);
            plot.add(dataSeries);
            PointRenderer pointRenderer = new DefaultPointRenderer2D();
            pointRenderer.setColor(seriesColor);
            plot.setPointRenderers(dataSeries, pointRenderer);

            LineRenderer lineRenderer = new DefaultLineRenderer2D();
            lineRenderer.setColor(seriesColor);
            plot.setLineRenderers(dataSeries, lineRenderer);
        }

        plot.setLegendVisible(true);
        plot.getLegend().setFont(new Font("SansSerif", Font.PLAIN, 8));
        plot.setLegendLocation(Location.EAST);
        plot.getAxis(XYPlot.AXIS_X).setMin(0);
        plot.getAxis(XYPlot.AXIS_X).setMax(1.0);
        plot.getAxis(XYPlot.AXIS_Y).setMin(0.0);
        plot.getAxisRenderer(XYPlot.AXIS_X).getLabel().setText("Holdout fraction (%user used for testing)");
        plot.getAxisRenderer(XYPlot.AXIS_Y).getLabel().setText(yLabel);

        return plot;
    }

    private Map<String, LenskitConfiguration> generateAlgorithms() {
        Map<String, LenskitConfiguration> result = new TreeMap<>();

        if (isRanking) {
            for (int i = 10; i <= 50; i+=5) {
                result.put("FunkSVD " + i, AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.SVD, i));
            }
            result.put("UserKNN", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User));
            result.put("ItemKNN", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item));
            result.put("Slope One", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.SlopeOne));
            result.put("Slope One-Weighted Slope", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.SlopeOne, SimilarityType.WeightedSlope));
        } else {
            result.put("UserKNN", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User));
            result.put("UserKNN-Pearson", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User, SimilarityType.Pearson));
            result.put("UserKNN-Spearman", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.User, SimilarityType.Spearman));

            result.put("ItemKNN", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item));
            result.put("ItemKNN-Pearson", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item, SimilarityType.Pearson));
            result.put("ItemKNN-Spearman", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.Item, SimilarityType.Spearman));

            result.put("Slope One", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.SlopeOne));
            result.put("Slope One-Weighted Slope", AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.SlopeOne, SimilarityType.WeightedSlope));

            for (int i = 10; i <= 50; i+=5) {
                result.put("FunkSVD " + i, AlgorithmConfiguratorFactory.getConfiguration(ConfigurationType.SVD, i));
            }
        }

        return result;
    }

    private class TrainerWorker extends SwingWorker<List<EvaluationResult>, Void> {
        @Override
        protected List<EvaluationResult> doInBackground() throws Exception {
            List<EvaluationResult> resultList = new ArrayList<>();
            logger.info("Beginning Training");
            int index = 1;

            for (String name : algorithms.keySet()) {
                logger.info("Evaluating " + name);
                Evaluator evaluator = new Evaluator(ratingFile, name, algorithms.get(name), isRanking);

                resultList.addAll(evaluator.runEvaluation());
                logger.info("Finished evaluating " + name);
                setProgress(index * 100 / algorithms.keySet().size());
                ++index;
            }

            return resultList;
        }

        @Override
        protected void done() {
            try {
                logger.info("Finished evaluation");
                parentFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                trainingProgressBar.setValue(100);
                trainingButton.setText("Start Training");
                trainingButton.setEnabled(true);
                ratingDataFileTextField.setEnabled(true);
                algorithmTypeComboBox.setEnabled(true);
                outputFileTextBox.setEnabled(true);
                predictButton.setEnabled(true);

                generateResultGraphics(get());
            } catch (InterruptedException | ExecutionException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private class RaterWorker extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() {
            try {
                LenskitConfiguration configuration = new LenskitConfiguration(algorithms.get(algorithmTypeComboBox.getSelectedItem().toString()));

                DelimitedColumnEventFormat format = Formats.csvRatings();

                format.setHeaderLines(1);
                if (isRanking)
                    format.setFields(Fields.user(), Fields.item(), Fields.rating());
                else
                    format.setFields(Fields.user(), Fields.item(), Fields.ignored(), Fields.rating());
                configuration.bind(EventDAO.class).to(new TextEventDAO(ratingFile, format));

                RecommendationPredictionsGenerator generator = new RecommendationPredictionsGenerator(configuration, inputFile, outputFile);
                generator.predict(isRanking);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void done() {
            trainingButton.setEnabled(true);
            ratingDataFileTextField.setEnabled(true);
            algorithmTypeComboBox.setEnabled(true);
            outputFileTextBox.setEnabled(true);
            predictButton.setEnabled(true);
            if (isRanking)
                predictButton.setText("Generate TopN");
            else
                predictButton.setText("Predict Ratings");
            JOptionPane.showMessageDialog(RatingStatistics.this, "Prediction finished", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
