package cl.uc.psanabria.sysrec.work1.data;

public class NonExistentRatingException extends RuntimeException {
    public static final String MESSAGE_FORMAT = "The rating for item %d and user %d doesn't exist";

    public NonExistentRatingException(int item, int user) {
        super(String.format(MESSAGE_FORMAT, item, user));
    }
}
