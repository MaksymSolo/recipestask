package task.recipesbook.recipesbookservice.aop;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import task.recipesbook.recipesbookservice.dto.ErrorResponseDTO;
import task.recipesbook.recipesbookservice.exception.RecipeNotFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;

import static task.recipesbook.recipesbookservice.dto.ErrorResponseDTO.toDto;

/**
 *  This class is used to handle exceptions that may be thrown during
 *  the execution of a Spring application.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * Handles the {@link RecipeNotFoundException} exception and returns a response entity with a
     * status of {@code BAD_REQUEST} and a body containing an error message.
     *
     * @param ex {@link RecipeNotFoundException} exception that was thrown
     * @return {@link ErrorResponseDTO} with code error message
     */
    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Object> recipeNotFoundException(RecipeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(toDto("Recipe not found", ex.getMessage()));
    }

    /**
     * Handles the {@link EmptyResultDataAccessException} exception and returns a response entity with a
     * status of {@code BAD_REQUEST} and a body containing an error message.
     *
     * @param ex {@link EmptyResultDataAccessException} exception
     *              that can be thrown by {@link org.springframework.data.jpa.repository.support.SimpleJpaRepository}
     * @return {@link ErrorResponseDTO} with code error message
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> recipeNotFoundException(EmptyResultDataAccessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(toDto("Recipe not found", ex.getMessage()));
    }
    
    /**
     * Handles any {@link  Exception} that is not caught by more specific exception handlers and
     * returns a response entity with a status of {@code INTERNAL_SERVER_ERROR} and a body containing
     * an error message.
     *
     * @param ex the {@link Exception} that was thrown
     * @return {@link ErrorResponseDTO} with code error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> internalServerErrorResponse(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(toDto("Something went wrong", ex.getMessage()));
    }



    /**
     * Handles the {@link ConstraintViolationException} exception, which is thrown when there is a
     * violation of a constraint defined in a Java Persistence API (JPA) entity.
     * This method extracts the validation errors from the exception
     * and returns a response entity with a status of {@code BAD_REQUEST}
     * and a body containing the validation errors.
     *
     * @param ex the {@code ConstraintViolationException} exception that was thrown
     * @return {@link ErrorResponseDTO} with code error message
     *          and a body containing the validation errors
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO<Map<String, String>>> handleValidationException(ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations()
                                        .stream()
                                        .collect(Collectors.toMap(
                                                constraint  -> constraint.getPropertyPath().toString(),
                                                ConstraintViolation::getMessage,
                                                (msg1, msg2) -> msg1 + ". " + msg2));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(toDto("Validation error", errors));
    }
}
