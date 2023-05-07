package task.recipesbook.recipesbookservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.recipesbook.recipesbookservice.model.Recipe;
import task.recipesbook.recipesbookservice.exception.RecipeNotFoundException;
import task.recipesbook.recipesbookservice.repository.RecipeRepository;
import task.recipesbook.recipesbookservice.service.RecipeService;

/**
 * {@link RecipeService} implementation
 */
@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Recipe> findAllByName(String recipeName, int page, int size,
                                      String sortBy, Sort.Direction direction) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
        return recipeRepository.findAllByNameContainingIgnoreCase(recipeName, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Recipe findRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe with id '" + recipeId + "'was not found"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Iterable<Recipe> saveAllRecipes(Iterable<Recipe> recipes) {
        return recipeRepository.saveAll(recipes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Recipe updateRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRecipeById(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }
}
