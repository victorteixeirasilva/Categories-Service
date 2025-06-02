package tech.inovasoft.inevolving.ms.categories.domain.exception;

public class NotFoundObjectiveInDatabaseException extends Exception {
    public NotFoundObjectiveInDatabaseException() {
        super("Objective not found in database");
    }
}
