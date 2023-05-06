import { HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError, of } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { Page } from 'src/types/page';
import { Recipe, Ingredient } from 'src/types/recipe';
import { RecipeService } from 'src/services/recipe.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  // Current page content
  public page: Page;
  // Current selected recipe (for edit or delete)
  public selectedRecipe!: Recipe;
  // Current ingredients on add
  public ingredientsOnAdd: Ingredient[] = [];

  constructor(private recipeService: RecipeService) { }

  ngOnInit() {
    this.loadRecipes();
  }

  // Navigates to a specific page of recipe 
  // search results based on the given name and page number.
  goToPage(name?: string, pageNumber?: number) {
    this.recipeService.findAllRecipesByName(name, pageNumber + 1).subscribe(response => {
      console.log(response);
      
      this.page = response;
    });
  }

  // Loads all the recipes and stores them in the 'page' property.
  public loadRecipes() {
    this.recipeService.findAllRecipes().
      subscribe((response: Page) => {
        console.log(response);

        this.page = response;
      });
  }

  // Opens detaile guide of selected recipe
  public onGuideRecipe(recipeId: number) {
    for (let recipe of this.page.content) {
      if (recipe.id === recipeId) {
        this.selectedRecipe = recipe;
        break;
      }
    }
  }

  // When the user submits the add recipe form
  // takes in the NgForm object which represents the form data
  // sets the ingredients to the ingredientsOnAdd array, 
  // and then adds the recipe using the recipeService
  public onAddRecipe(addForm: NgForm) {
    addForm.value.ingredients = this.ingredientsOnAdd;

    this.recipeService.addRecipe(addForm.value).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error(error);

        return of(null);
      })
    ).subscribe(response => {
      if (response) {
        addForm.reset();
        this.ingredientsOnAdd = [];
        this.loadRecipes();
      }
    });
  }

  // When the user submits the update recipe form
  // takes in the NgForm object which represents the form data
  // and then update the recipe using the recipeService
  public onUpdateRecipe(editForm: NgForm) {
    this.recipeService.addRecipe(editForm.value).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error(error);

        return of(null);
      })
    ).subscribe(response => {
      if (response) {

        this.loadRecipes();
      }
    });
  }

  // Delete recipe with spesified ID
  public onDeleteRecipe(recipeId: number) {
    this.recipeService.deleteRecipe(recipeId)
      .subscribe(response => {
        this.loadRecipes();
      }
      );
  }

  // Search for recipes based on a keyword.(recipe name)
  public searchRecipe(key: string) {
    this.recipeService.findAllRecipesByName(key).
      subscribe((response: Page) => {
        console.log(response);

        this.page = response;
      });
  }

  //Opens a modal based on the given mode (guide, add, edit, delete) for the selected recipe.
  public onOpenModal(recipe: Recipe, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');

    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if (mode === 'guide') {
      this.selectedRecipe = recipe;
      button.setAttribute('data-target', '#guideRecipeModal');
    }

    if (mode === 'add') {
      button.setAttribute('data-target', '#addRecipeModal');
    }

    if (mode === 'edit') {
      this.selectedRecipe = recipe;
      button.setAttribute('data-target', '#updateRecipeModal');
    }

    if (mode === 'delete') {
      this.selectedRecipe = recipe;
      button.setAttribute('data-target', '#deleteRecipeModal');
    }

    if (container != null) {
      container.appendChild(button);
    }

    button.click();
  }

  // Adds ingredient to ingredientsOnAdd for add recipe form
  public addIngredient(nameInput: NgModel, amountInput: NgModel) {
    if(nameInput.valid && amountInput.valid) {
      this.ingredientsOnAdd.push(this.mapIngredientAndReset(nameInput, amountInput));
    }
  }

  // Adds ingredient to selected recipe for update recipe form
  public addIngredientToSelected(nameInput: NgModel, amountInput: NgModel) {
    if(nameInput.valid && amountInput.valid) {
      this.selectedRecipe.ingredients.push(this.mapIngredientAndReset(nameInput, amountInput));
    }
  }

  // Maps ingredient  with spesified in ngModel name and amount 
  // end resets the input fields
  private mapIngredientAndReset(nameInput: NgModel, amountInput: NgModel): Ingredient {
    const ingredient: Ingredient = { name: nameInput.value, amount: amountInput.value }
    
    nameInput.reset;
    amountInput.reset;
    return ingredient;
  }

  // Removes ingredient to ingredientsOnAdd for add recipe form
  public removeIngredient(name: string) {
    this.ingredientsOnAdd = this.ingredientsOnAdd.filter(item => item.name !== name)
  }

  // Removes ingredient to selected recipe for update recipe form
  public removeIngredintOnSelected(name: string) {
    this.selectedRecipe.ingredients = this.selectedRecipe.ingredients.filter(item => item.name !== name)
  }
}