package tech.inovasoft.inevolving.ms.categories.domain.exception;

public class NotFoundCategoryInDatabaseException extends Exception {
    public NotFoundCategoryInDatabaseException() {
        super("Category not found in database");
    }
}
