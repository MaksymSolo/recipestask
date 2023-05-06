package task.recipesbook.recipesbookservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import task.recipesbook.recipesbookservice.model.Recipe;

/**
 * Service layer interface for {@link Recipe} entity.
 */
public interface RecipeService {

    /**
     * Finds all Recipes containing the given name, sorted and paginated.
     *
     * @param recipeName The name to search for in Recipe names.
     * @param page The page number of the results to return.
     * @param size The number of results per page.
     * @param sortBy The field to sort the results by.
     * @param direction The direction to sort the results by.
     *
     * @return A Page object containing the requested Recipes.
     */
    Page<Recipe> findAllByName(String recipeName, int page, int size, String sortBy, Sort.Direction direction);

    /**
     * Finds a Recipe with the given ID.
     *
     * @param recipeId The ID of the Recipe to find.
     * @return The Recipe with the given ID.
     */
    Recipe findRecipeById(Long recipeId);

    /**
     * Saves a Recipe.
     *
     * @param recipe The Recipe to save.
     * @return The saved Recipe.
     */
    Recipe saveRecipe(Recipe recipe);

    /**
     * Updates a Recipe.
     *
     * @param recipe The Recipe to update.
     * @return The updated Recipe.
     */
    Recipe updateRecipe(Recipe recipe);

    /**
     * Deletes a Recipe with the given ID.
     *
     * @param recipeId The ID of the Recipe to delete.
     */
    void deleteRecipeById(Long recipeId);
}
