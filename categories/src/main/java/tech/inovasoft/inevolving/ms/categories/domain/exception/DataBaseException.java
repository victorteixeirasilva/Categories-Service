package tech.inovasoft.inevolving.ms.categories.domain.exception;

public class DataBaseException extends Exception {
    public DataBaseException(String methodName) {
        super("Error in database: " + methodName);
    }
}
