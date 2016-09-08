package cl.uc.psanabria.sysrec.work1.recommender;

import org.grouplens.lenskit.ItemScorer;
import org.grouplens.lenskit.baseline.BaselineScorer;
import org.grouplens.lenskit.baseline.ItemMeanRatingItemScorer;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.knn.NeighborhoodSize;
import org.grouplens.lenskit.knn.item.ItemItemScorer;
import org.grouplens.lenskit.knn.item.ItemVectorSimilarity;
import org.grouplens.lenskit.knn.item.ModelSize;
import org.grouplens.lenskit.knn.item.model.ItemItemModel;
import org.grouplens.lenskit.knn.item.model.ItemItemModelBuilder;
import org.grouplens.lenskit.knn.user.UserSimilarityThreshold;
import org.grouplens.lenskit.knn.user.UserUserItemScorer;
import org.grouplens.lenskit.knn.user.UserVectorSimilarity;
import org.grouplens.lenskit.transform.normalize.MeanCenteringVectorNormalizer;
import org.grouplens.lenskit.transform.normalize.UserVectorNormalizer;
import org.grouplens.lenskit.transform.normalize.VectorNormalizer;
import org.grouplens.lenskit.transform.threshold.AbsoluteThreshold;
import org.grouplens.lenskit.transform.threshold.RealThreshold;
import org.grouplens.lenskit.transform.threshold.Threshold;
import org.grouplens.lenskit.transform.threshold.ThresholdValue;
import org.grouplens.lenskit.vectors.similarity.*;

@SuppressWarnings("unchecked")
public class AlgorithmConfiguratorFactory {
    public static LenskitConfiguration getConfiguration(ConfigurationType type, int neighbours, double threshold, SimilarityType similarityType) {
        switch (type) {
            case User:
                return getUserUserConfiguration(neighbours, threshold, similarityType);
            case Item:
                return getUserItemConfiguration(neighbours, threshold, similarityType);
            case SlopeOne:
            default:
                throw new InvalidAlgorithmException(type);
        }
    }

    public static LenskitConfiguration getConfiguration(ConfigurationType type, int neighbours, double threshold) {
        return getConfiguration(type, neighbours, threshold, SimilarityType.Default);
    }

    public static LenskitConfiguration getConfiguration(ConfigurationType type, double threshold) {
        return getConfiguration(type, 0, threshold, SimilarityType.Default);
    }

    public static LenskitConfiguration getConfiguration(ConfigurationType type, SimilarityType similarityType) {
        return getConfiguration(type, 0, 0.0, similarityType);
    }

    public static LenskitConfiguration getConfiguration(ConfigurationType type, int neighbours) {
        return getConfiguration(type, neighbours, 0.0);
    }

    public static LenskitConfiguration getConfiguration(ConfigurationType type) {
        return getConfiguration(type, 0);
    }

    private static LenskitConfiguration getUserUserConfiguration(int neighbours, double threshold, SimilarityType similarityType) {
        LenskitConfiguration configuration = new LenskitConfiguration();

        configuration.bind(ItemScorer.class).to(UserUserItemScorer.class);
        configuration.bind(BaselineScorer.class, ItemScorer.class).to(ItemMeanRatingItemScorer.class);

        configuration.within(UserVectorNormalizer.class)
                .bind(VectorNormalizer.class).to(MeanCenteringVectorNormalizer.class);

        if (neighbours > 0) {
            configuration.set(NeighborhoodSize.class).to(neighbours);
        }

        if (!(Math.abs(threshold - 0.0) < 0.01)) {
            configuration.set(ThresholdValue.class).to(threshold);
        }

        if (similarityType != SimilarityType.Default) {
            switch (similarityType) {
                case Cosine:
                    configuration.within(UserVectorSimilarity.class)
                            .bind(VectorSimilarity.class).to(CosineVectorSimilarity.class);
                    break;
                case Pearson:
                    configuration.within(UserVectorSimilarity.class)
                            .bind(VectorSimilarity.class).to(PearsonCorrelation.class);
                    break;
                case Spearman:
                    configuration.within(UserVectorSimilarity.class)
                            .bind(VectorSimilarity.class).to(SpearmanRankCorrelation.class);
                    break;
                default:
                    throw new InvalidCorrelationException(similarityType);
            }
        }

        return configuration;
    }

    private static LenskitConfiguration getUserItemConfiguration(int neighbours, double threshold, SimilarityType similarityType) {
        LenskitConfiguration configuration = new LenskitConfiguration();

        configuration.bind(ItemScorer.class).to(ItemItemScorer.class);
        configuration.bind(BaselineScorer.class, ItemScorer.class).to(ItemMeanRatingItemScorer.class);
        configuration.within(UserVectorNormalizer.class)
                .bind(VectorNormalizer.class).to(MeanCenteringVectorNormalizer.class);

        if (neighbours > 0) {
            configuration.set(NeighborhoodSize.class).to(neighbours);
        }

        if (!(Math.abs(threshold - 0.0) < 0.01)) {
            configuration.set(ThresholdValue.class).to(threshold);
        }

        if (similarityType != SimilarityType.Default) {
            switch (similarityType) {
                case Cosine:
                    configuration.within(ItemVectorSimilarity.class)
                            .bind(VectorSimilarity.class).to(CosineVectorSimilarity.class);
                    break;
                case Pearson:
                    configuration.within(ItemVectorSimilarity.class)
                            .bind(VectorSimilarity.class).to(PearsonCorrelation.class);
                    break;
                case Spearman:
                    configuration.within(ItemVectorSimilarity.class)
                            .bind(VectorSimilarity.class).to(SpearmanRankCorrelation.class);
                    break;
                default:
                    throw new InvalidCorrelationException(similarityType);
            }
        }

        return configuration;
    }
}