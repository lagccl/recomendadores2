package cl.uc.psanabria.sysrec.work1.recommender;

public class InvalidCorrelationException extends RuntimeException {
    public InvalidCorrelationException(SimilarityType similarityType) {
        super("Invalid algorithm exception: " + similarityType.toString());
    }
}
