package cl.uc.psanabria.sysrec.work1.recommender;

public class InvalidAlgorithmException extends RuntimeException {
    public InvalidAlgorithmException(ConfigurationType type) {
        super("Invalid algorithm exception: " + type.toString());
    }
}
