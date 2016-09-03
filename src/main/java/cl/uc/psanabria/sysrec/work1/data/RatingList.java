package cl.uc.psanabria.sysrec.work1.data;


import java.util.*;

public class RatingList {
    private Map<Integer, Map<Integer, Float>> itemRatings;
    private int ratingsCount;


    RatingList() {
        itemRatings = new TreeMap<>();
        ratingsCount = 0;
    }

    public int size() {
        return ratingsCount;
    }

    public int usersCount() {
        Set<Integer> users = new TreeSet<>();

        for (int item : itemRatings.keySet()) {
            users.addAll(itemRatings.get(item).keySet());
        }
        return users.size();
    }

    public int itemsCount() {
        return itemRatings.size();
    }

    public void addRating(Rating rating) {
        if (itemRatings.containsKey(rating.getItem())) {
            Map<Integer, Float> userRatings = itemRatings.get(rating.getItem());
            if (!userRatings.containsKey(rating.getUser())) {
                ++ratingsCount;
            }
            userRatings.put(rating.getUser(), rating.getScore());
        } else {
            Map<Integer, Float> newUserRatings = new TreeMap<>();

            newUserRatings.put(rating.getUser(), rating.getScore());
            itemRatings.put(rating.getItem(), newUserRatings);
            ++ratingsCount;
        }
    }

    public void removeRating(int item, int user) {
        Map<Integer, Float> userRatings = itemRatings.get(item);

        if (userRatings == null || !userRatings.containsKey(user))
            throw new NonExistentRatingException(item, user);

        userRatings.remove(user);

        if (userRatings.size() == 0)
            itemRatings.remove(item);

        --ratingsCount;
    }

    public Rating getRating(int item, int user) {
        Map<Integer, Float> userRatings = itemRatings.get(item);

        if (userRatings == null || !userRatings.containsKey(user))
            return null;

        float score = itemRatings.get(item).get(user);

        return new Rating(item, user, score);
    }

    public float getItemScoreAverage(int item) {
        Map<Integer, Float> userRatings = itemRatings.get(item);

        if (userRatings == null)
            return 0.0f;

        float total = 0.0f;

        for(int user : userRatings.keySet()) {
            total += userRatings.get(user);
        }

        return total / userRatings.size();
    }

    public float getUserScoreAverage(int user) {
        int userCount = 0;
        float total = 0;

        for(int item : itemRatings.keySet()) {
            Map<Integer, Float> userRatings = itemRatings.get(item);

            if (userRatings.containsKey(user)) {
                total += userRatings.get(user);
                ++userCount;
            }
        }

        if (userCount == 0)
            return 0.0f;

        return total / userCount;
    }

    public Collection<Integer> getItemList() {
        return itemRatings.keySet();
    }

    public Collection<Integer> getUserList() {
        Set<Integer> userList = new TreeSet<>();

        for(int item : itemRatings.keySet()) {
            Map<Integer, Float> userRatings = itemRatings.get(item);

            userList.addAll(userRatings.keySet());
        }

        return userList;
    }
}
