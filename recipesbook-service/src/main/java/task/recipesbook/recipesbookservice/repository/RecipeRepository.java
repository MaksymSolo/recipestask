package task.recipesbook.recipesbookservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import task.recipesbook.recipesbookservice.model.Recipe;

/**
 * The RecipeRepository interface provides methods to access and manipulate {@link Recipe} entities
 * from the database.
 */
@Repository
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {

    /**
     * Finds all recipes that contain the given name ignoring case, and returns them as a pageable list.
     *
     * @param recipeName the name to search for in recipe names
     * @param pageRequest the page request to get a specific page of results
     * @return a page of recipes containing the given name
     */
    Page<Recipe> findAllByNameContainingIgnoreCase(String recipeName, PageRequest pageRequest);
}
