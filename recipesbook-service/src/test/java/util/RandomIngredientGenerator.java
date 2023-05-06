package util;

import task.recipesbook.recipesbookservice.model.Ingredient;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomIngredientGenerator {

    private static final String[] INGREDIENTS_NAME = {
            "flour", "sugar", "butter", "salt", "eggs",
            "vanilla extract", "baking powder", "milk", "cocoa powder"
    };

    public static Ingredient generateRandomIngredient() {
        Ingredient ingredient = new Ingredient();
        Random random = new Random();
        ingredient.setIngredientId(random.nextLong());
        ingredient.setName(INGREDIENTS_NAME[random.nextInt(INGREDIENTS_NAME.length)]);
        ingredient.setAmount(random.nextInt(100) + 1 + " grams");
        return ingredient;
    }

    public static List<Ingredient> generateRandomIngredientList(int amount) {
        return IntStream.range(0, amount)
                        .mapToObj(i -> generateRandomIngredient())
                        .collect(Collectors.toList());
    }
}
