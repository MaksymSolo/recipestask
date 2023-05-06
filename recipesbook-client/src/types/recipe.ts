export interface Recipe {
    id: number;
    name: string;
    shortDescription: string;
    guide: string
    imageUrl: string;
    ingredients: Ingredient[];
}

export interface Ingredient {
    name: string;
    amount: string;
}