package cl.uc.psanabria.sysrec.work1.recommender;

import java.util.LinkedList;
import java.util.List;

public class EvaluationResult {
    private String testName;
    private double x;
    private List<Long> timeList;
    private List<Double> rmseList;
    private List<Double> topMapList;
    private List<Double> topNDCGList;

    public EvaluationResult(String testName, double x) {
        this.testName = testName;
        this.x = x;
        timeList = new LinkedList<>();
        rmseList = new LinkedList<>();
        topMapList = new LinkedList<>();
        topNDCGList = new LinkedList<>();
    }

    public int size() {
        return timeList.size();
    }

    public double getTime() {
        if (timeList.size() == 0)
            return 0;
        double total = 0;

        for (double time: timeList) {
            total += time;
        }

        return total / rmseList.size();
    }

    public double getRMSE() {
        if (rmseList.size() == 0)
            return 0;
        double total = 0;

        for (double rmse: rmseList) {
            total += rmse;
        }

        return total / rmseList.size();
    }

    public double getTopMap() {
        if (topMapList.size() == 0)
            return 0;
        double total = 0;

        for (double map: topMapList) {
            total += map;
        }

        return total / topMapList.size();
    }

    public double getTopNDCG() {
        if (topNDCGList.size() == 0)
            return 0;
        double total = 0;

        for (double topNDCG: topNDCGList) {
            total += topNDCG;
        }

        return total / topNDCGList.size();
    }

    public void addResult(long elapsedTime, double rmse, double topMAP, double topNDCG) {
        timeList.add(elapsedTime);
        rmseList.add(rmse);
        topMapList.add(topMAP);
        topNDCGList.add(topNDCG);
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public double getX() {
        return x;
    }
}
