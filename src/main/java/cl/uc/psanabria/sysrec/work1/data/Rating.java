package cl.uc.psanabria.sysrec.work1.data;

public class Rating {

    private int user;
    private int item;
    private float score;

    public Rating() {
        this(0, 0, 0.0f);
    }

    public Rating(int item, int user, float score) {
        setUser(user);
        setItem(item);
        setScore(score);
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
