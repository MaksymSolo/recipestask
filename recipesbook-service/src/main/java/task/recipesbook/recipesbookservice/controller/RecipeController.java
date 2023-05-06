package task.recipesbook.recipesbookservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import task.recipesbook.recipesbookservice.model.Recipe;
import task.recipesbook.recipesbookservice.service.RecipeService;

/**
 * RestController for handling "/recipe" related HTTP requests
 */
@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private final RecipeService recipeService;

    /**
     * Constructor to initialize the RecipeController with a RecipeService
     * @param recipeService RecipeService to be injected
     */
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * GET endpoint for retrieving all recipes by name
     *
     * @param name String value of the recipe name to be searched for
     * @param page Integer value of the page to be returned
     * @param size Integer value of the number of results to be returned
     * @param sortBy String value of the field to be sorted by
     * @param direction Sort.Direction value of the direction of sorting (ASC or DESC)
     * @return ResponseEntity containing a Page of Recipe objects
     */
    @GetMapping("/all")
    public ResponseEntity<Page<Recipe>> getAllRecipesByName(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "recipeId") String sortBy,
            @RequestParam(value = "direction", required = false, defaultValue = "DESC") Sort.Direction direction
    ) {
        Page<Recipe> recipes = recipeService.findAllByName(name, page, size, sortBy, direction);
        return ResponseEntity.ok(recipes);
    }

    /**
     * GET endpoint for retrieving a {@link Recipe} by ID
     *
     * @param id Long value of the Recipe ID
     * @return Recipe object
     */
    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable("id") Long id) {
        return recipeService.findRecipeById(id);
    }

    /**
     * POST endpoint for adding a new {@link Recipe}
     *
     * @param recipe Recipe object to be added
     * @return ResponseEntity containing the added Recipe object
     */
    @PostMapping("/add")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.saveRecipe(recipe);
        return ResponseEntity.ok(newRecipe);
    }

    /**
     * PUT endpoint for updating an existing {@link Recipe}
     *
     * @param recipe Recipe object to be updated
     * @return ResponseEntity containing the updated Recipe object
     */
    @PutMapping("/update")
    public ResponseEntity<Recipe> updateRecipe(@RequestBody Recipe recipe) {
        Recipe updatedRecipe = recipeService.updateRecipe(recipe);
        return ResponseEntity.ok(updatedRecipe);
    }

    /**
     * DELETE endpoint for deleting a {@link Recipe} by ID
     *
     * @param id Long value of the Recipe ID to be deleted
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecipeById(@PathVariable("id") Long id) {
        recipeService.deleteRecipeById(id);
        return ResponseEntity.noContent().build();
    }
}
