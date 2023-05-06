package util;

import task.recipesbook.recipesbookservice.model.Recipe;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomRecipeGenerator {

    private static final String[] RECIPE_NAMES = {
            "Pasta Carbonara", "Beef Stroganoff", "Chicken Tikka Masala",
            "Fish Tacos", "Greek Salad", "Spinach Lasagna"
    };

    private static final String[] RECIPE_DESCRIPTIONS = {
            "A classic Italian pasta dish with eggs, cheese, and bacon",
            "A Russian dish made with beef, onions, and sour cream",
            "A popular Indian curry with chicken and aromatic spices",
            "A Mexican dish made with grilled fish, salsa, and avocado",
            "A healthy salad with feta cheese, olives, and tomatoes",
            "A vegetarian lasagna with spinach, ricotta, and tomato sauce"
    };

    private static final String[] RECIPE_INSTRUCTIONS = {
            "Boil pasta in salted water, fry bacon until crisp, mix eggs and cheese, toss all ingredients together",
            "Cut beef into strips, sauté onions, add mushrooms and beef broth, stir in sour cream, serve over noodles",
            "Marinate chicken in yogurt and spices, grill or bake until done, simmer in tomato sauce with cream and butter, serve with rice",
            "Grill or fry fish until cooked, mix salsa and avocado, assemble tacos with fish, salsa, and other toppings",
            "Chop lettuce and vegetables, crumble feta cheese, mix with olive oil and vinegar, add salt and pepper to taste",
            "Boil lasagna noodles, mix spinach and ricotta cheese, layer noodles, sauce, and spinach mixture, bake until golden and bubbly"
    };

    private static final Random random = new Random();

    private static String getRandomRecipeName() {
        int index = random.nextInt(RECIPE_NAMES.length);
        return RECIPE_NAMES[index];
    }

    private static String getRandomRecipeDescription() {
        int index = random.nextInt(RECIPE_DESCRIPTIONS.length);
        return RECIPE_DESCRIPTIONS[index];
    }

    private static String getRandomRecipeGuide() {
        int index = random.nextInt(RECIPE_INSTRUCTIONS.length);
        return RECIPE_INSTRUCTIONS[index];
    }

    public static Recipe generateRandomRecipe() {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(random.nextLong());
        recipe.setName(getRandomRecipeName());
        recipe.setShortDescription(getRandomRecipeDescription());
        recipe.setImageUrl(UUID.randomUUID().toString());
        recipe.setGuide(getRandomRecipeGuide());
        return recipe;
    }

    public static List<Recipe> generateRandomResipeList(int amount) {
        return IntStream.range(0, amount)
                .mapToObj(i -> generateRandomRecipe())
                .collect(Collectors.toList());
    }
}
