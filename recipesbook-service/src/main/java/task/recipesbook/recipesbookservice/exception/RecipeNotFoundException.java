package task.recipesbook.recipesbookservice.exception;

/**
 * This exception can be thrown when required recipe was not found
 */
public class RecipeNotFoundException extends RuntimeException {
    /**
     * Constructs a new RecipeNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public RecipeNotFoundException(String message) {
        super(message);
    }
}
