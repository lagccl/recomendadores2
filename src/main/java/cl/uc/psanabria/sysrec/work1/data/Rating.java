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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Rating))
            return false;

        Rating other = (Rating)obj;

        return item == other.item && user == other.user && Math.abs(score - other.score) < 0.001;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "user=" + user +
                ", item=" + item +
                ", score=" + score +
                '}';
    }
}
