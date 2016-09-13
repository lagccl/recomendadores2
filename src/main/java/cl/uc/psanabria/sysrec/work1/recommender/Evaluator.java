package cl.uc.psanabria.sysrec.work1.recommender;

import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.data.source.CSVDataSourceBuilder;
import org.grouplens.lenskit.data.text.DelimitedColumnEventFormat;
import org.grouplens.lenskit.data.text.Fields;
import org.grouplens.lenskit.data.text.Formats;
import org.grouplens.lenskit.eval.TaskExecutionException;
import org.grouplens.lenskit.eval.algorithm.AlgorithmInstance;
import org.grouplens.lenskit.eval.metrics.predict.NDCGPredictMetric;
import org.grouplens.lenskit.eval.metrics.predict.RMSEPredictMetric;
import org.grouplens.lenskit.eval.metrics.topn.ItemSelectors;
import org.grouplens.lenskit.eval.metrics.topn.MAPTopNMetric;
import org.grouplens.lenskit.eval.metrics.topn.NDCGTopNMetric;
import org.grouplens.lenskit.eval.traintest.SimpleEvaluator;
import org.grouplens.lenskit.util.table.Column;
import org.grouplens.lenskit.util.table.Table;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Evaluator {
    private SimpleEvaluator evaluator;
    private String name;
    private boolean isRanking;
    private static final String DATA_SET_BASE_NAME = "RatingData-0.";
    private static final String DATA_SET_RANKING_BASE_NAME = "RankingData-0.";

    public Evaluator(File file, String name, LenskitConfiguration configuration, boolean isRanking) {
        this.name = name;
        this.isRanking = isRanking;
        evaluator = new SimpleEvaluator();

        DelimitedColumnEventFormat format = Formats.csvRatings();

        format.setHeaderLines(1);
        if (isRanking)
            format.setFields(Fields.user(), Fields.item(), Fields.rating());
        else
            format.setFields(Fields.user(), Fields.item(), Fields.ignored(), Fields.rating());
        CSVDataSourceBuilder sourceBuilder = new CSVDataSourceBuilder(file);
        sourceBuilder.setFormat(format);

        for(int i = 1; i < 10; i++) {
            evaluator.addDataset(getBaseName() + i, sourceBuilder.build(), 5, i / 10.0);
        }

        evaluator.addAlgorithm(name, configuration);

        evaluator.addMetric(RMSEPredictMetric.class);

        MAPTopNMetric.Builder mapBuilder = new MAPTopNMetric.Builder();
        mapBuilder.setCandidates(ItemSelectors.allItems());
        mapBuilder.setExclude(ItemSelectors.trainingItems());
        mapBuilder.setListSize(10);
        evaluator.addMetric(mapBuilder.build());

        NDCGTopNMetric.Builder builder = new NDCGTopNMetric.Builder();

        builder.setCandidates(ItemSelectors.allItems());
        builder.setExclude(ItemSelectors.trainingItems());
        builder.setListSize(10);

        evaluator.addMetric(builder.build());
    }

    public List<EvaluationResult> runEvaluation() throws TaskExecutionException {
        List<EvaluationResult> evaluationResultList = new ArrayList<>();
        Table table = evaluator.call();
        // Table structure [Algorithm, DataSet, Partition, BuildTime, TestTime, RMSE.ByUser, RMSE.ByRating, nDCG, TopN.nDCG]

        for(int dataSet = 1; dataSet < 10; dataSet++) {
            Table ratingTable = table.filter("DataSet", getBaseName() + dataSet);

            Column rmseColumn = ratingTable.column("RMSE.ByUser");
            Column testTimeColumn = ratingTable.column("TestTime");
            Column buildTimeColumn = ratingTable.column("BuildTime");
            Column topMAPolumn = ratingTable.column("MAP");
            Column topNDCGColumn = ratingTable.column("TopN.nDCG");
            EvaluationResult evaluationResult = new EvaluationResult(name, dataSet / 10.0);

            for(int i = 0; i < rmseColumn.size(); i++) {
                double rmse = (double)rmseColumn.get(i);
                long testTime = (long)testTimeColumn.get(i) + (long)buildTimeColumn.get(i);
                double nDCG = (double)topMAPolumn.get(i);
                double topNDCG = (double)topNDCGColumn.get(i);

                evaluationResult.addResult(testTime, rmse, nDCG, topNDCG);
            }

            evaluationResultList.add(evaluationResult);
        }

        return evaluationResultList;
    }

    private String getBaseName() {
        if (isRanking)
            return DATA_SET_RANKING_BASE_NAME;
        else
            return DATA_SET_BASE_NAME;
    }
}
