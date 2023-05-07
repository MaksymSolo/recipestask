export interface Recipe {
    recipeId: number;
    name: string;
    shortDescription: string;
    guide: string
    imageUrl: string;
    ingredients: Ingredient[];
}

export interface Ingredient {
    ingredientId: number;
    name: string;
    amount: string;
}