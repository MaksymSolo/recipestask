package task.recipesbook.recipesbookservice.dto;

import lombok.Getter;

/**
 * That represents an error response with a message and a body.
 *
 * @param <T> the type of the body of the error response
 * @see task.recipesbook.recipesbookservice.aop.ExceptionHandlerAdvice
 */
@Getter
public class ErrorResponseDTO<T> {

    /**
     * The error message to include in the response
     */
    private final String errorMassage;

    /**
     * The body of the error response
     */
    private final T body;

    /**
     * Constructs a new {@link ErrorResponseDTO} object with the given error message and body.
     * @param errorMassage the error message to include in the response
     * @param body the body of the error response
     */
    public ErrorResponseDTO(String errorMassage, T body) {
        this.errorMassage = errorMassage;
        this.body = body;
    }

    /**
     * Creates a new {@link ErrorResponseDTO} object with the given error message and body.
     *
     * @param errorMassage the error message to include in the response
     * @param body the body of the error response
     * @param <T> the type of the body of the error response
     * @return the newly created ErrorResponseDTO object
     */
    public static <T> ErrorResponseDTO<T> toDto(String errorMassage, T body) {
        return new ErrorResponseDTO<>(errorMassage, body);
    }
}
