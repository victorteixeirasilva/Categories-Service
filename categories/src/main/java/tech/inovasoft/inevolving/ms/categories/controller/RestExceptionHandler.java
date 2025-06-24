package tech.inovasoft.inevolving.ms.categories.controller;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ExceptionResponse;
import tech.inovasoft.inevolving.ms.categories.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.ErrorInExternalServiceException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.NotFoundCategoryInDatabaseException;
import tech.inovasoft.inevolving.ms.categories.domain.exception.NotFoundObjectiveInDatabaseException;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ExceptionResponse> handleDataBaseException(DataBaseException exception) {
        log.error("ERROR: {} - {}", exception.getClass().getSimpleName(), exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(
                        exception.getClass().getSimpleName(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(ErrorInExternalServiceException.class)
    public ResponseEntity<ExceptionResponse> handleErrorInExternalServiceException(ErrorInExternalServiceException exception) {
        log.error("ERROR: {} - {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(
                        exception.getClass().getSimpleName(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(NotFoundCategoryInDatabaseException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundCategoryInDatabaseException(NotFoundCategoryInDatabaseException exception) {
        log.error("ERROR: {} - {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(
                        exception.getClass().getSimpleName(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(NotFoundObjectiveInDatabaseException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundObjectiveInDatabaseException(NotFoundObjectiveInDatabaseException exception) {
        log.error("ERROR: {} - {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(
                        exception.getClass().getSimpleName(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ExceptionResponse> handleFeignException(FeignException ex) {
        ExceptionResponse response = new ExceptionResponse(
                ex.getClass().getName(),
                ex.getMessage()
        );

        log.error("ERROR: {} - {} - {} - {}",  ex.getMessage(), ex.getClass().getSimpleName(), ex.getLocalizedMessage(), ex.request());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

}
