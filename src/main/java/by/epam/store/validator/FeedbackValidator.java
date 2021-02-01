package by.epam.store.validator;

public class FeedbackValidator {
    public static boolean isEvaluationValid(byte evaluation){
        return evaluation <= 5 && evaluation >= 1;
    }
}
