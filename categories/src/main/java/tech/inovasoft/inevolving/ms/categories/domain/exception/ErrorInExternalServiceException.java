package tech.inovasoft.inevolving.ms.categories.domain.exception;

public class ErrorInExternalServiceException extends Throwable {
    public ErrorInExternalServiceException(String s) {
        super("Error in external service: " + s);
    }
}
