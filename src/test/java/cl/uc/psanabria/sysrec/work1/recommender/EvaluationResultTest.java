package cl.uc.psanabria.sysrec.work1.recommender;

import org.junit.Test;

import static org.junit.Assert.*;

public class EvaluationResultTest {
    @Test
    public void testEmptyEvaluationResult() {
        EvaluationResult evaluationResult = new EvaluationResult("Test1", 0.1);

        assertEquals(0, evaluationResult.size());
        assertEquals(0, evaluationResult.getTime(), 0.001);
        assertEquals(0.0, evaluationResult.getRMSE(), 0.001);
        assertEquals(0.0, evaluationResult.getNDCG(), 0.001);
        assertEquals(0.0, evaluationResult.getTopNDCG(), 0.001);
    }

    @Test
    public void testResultsWithOneEvaluationResult() {
        EvaluationResult evaluationResult = new EvaluationResult("Test1", 0.1);

        evaluationResult.addResult(15, 0.5, 1.0, 0.8);

        assertEquals(1, evaluationResult.size());
        assertEquals(15, evaluationResult.getTime(), 0.001);
        assertEquals(0.5, evaluationResult.getRMSE(), 0.001);
        assertEquals(1.0, evaluationResult.getNDCG(), 0.001);
        assertEquals(0.8, evaluationResult.getTopNDCG(), 0.001);
    }

}