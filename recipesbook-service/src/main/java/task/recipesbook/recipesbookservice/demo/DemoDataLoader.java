package task.recipesbook.recipesbookservice.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import task.recipesbook.recipesbookservice.model.Ingredient;
import task.recipesbook.recipesbookservice.model.Recipe;
import task.recipesbook.recipesbookservice.service.RecipeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static task.recipesbook.recipesbookservice.util.RandomIngredientGenerator.generateRandomIngredientList;
import static task.recipesbook.recipesbookservice.util.RandomRecipeGenerator.generateRandomRecipe;

/**
 * A component that loads demo data into the application database when the application starts up.
 */
@Component
public class DemoDataLoader implements ApplicationRunner {

    /**
     * The Recipe service to use for loading demo data.
     */
    @Autowired
    private final RecipeService recipeService;

    public DemoDataLoader(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Generates random recipes and saves them using the recipeService.
     */
    @Override
    public void run(ApplicationArguments args)  {
        recipeService.saveAllRecipes(randomRecipes(10));
    }

    /**
     * Generates a list of random recipes with random ingredients.
     *
     * @param amount The number of recipes to generate.
     * @return A list of randomly generated recipes.
     */
    private List<Recipe> randomRecipes(int amount) {
        Random random = new Random();

        List<Recipe> recipes = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            Recipe recipe = generateRandomRecipe();
            List<Ingredient> ingredients = generateRandomIngredientList(random.nextInt(3) + 2);
            recipe.setIngredients(ingredients);
            recipes.add(recipe);
        }
        return recipes;
    }
}
