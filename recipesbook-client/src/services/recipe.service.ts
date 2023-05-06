import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment, pagination } from 'src/environments/environments';
import { Recipe } from 'src/types/recipe';
import { Page } from 'src/types/page';

@Injectable({
  providedIn: 'root'
})
// Service for retrieving and manipulating recipes via HTTP requests to a REST API.
export class RecipeService {
  
  private apiServerUrl = environment.apiBaseUrl + '/recipe';

  constructor(private http: HttpClient) { }

  // Makes call to the back end API to retrieve page of all recipes
  public findAllRecipes(page: number = pagination.defaultPage,
                        size: number = pagination.defaultSize,
                        sortby: string = pagination.defaultSort,
                        direction: string = pagination.defaultDirection): Observable<Page> {
  
    return this.http.get<Page>(`${this.apiServerUrl}/all?page=${page}&size=${size}&sortBy=${sortby}&direction=${direction}`);
  }

  // Makes call to the API to retrieve page of all recipes by name
  public findAllRecipesByName(name: string = '',
                              page: number = pagination.defaultPage,
                              size: number = pagination.defaultSize,
                              sortby: string = pagination.defaultSort,
                              direction: string = pagination.defaultDirection): Observable<Page> {

    return this.http.get<Page>(`${this.apiServerUrl}/all?name=${name}&page=${page}&size=${size}&sortBy=${sortby}&direction=${direction}`);
  }

  // Makes call to the API to Adds a new recipe 
  public addRecipe(recipe: Recipe): Observable<Recipe> {
    return this.http.post<Recipe>(`${this.apiServerUrl}/add`, recipe);
  }

  // Makes call to the API Updates an existing recipe
  public updateRecipe(recipe: Recipe): Observable<Recipe> {
    return this.http.put<Recipe>(`${this.apiServerUrl}/update`, recipe);
  }

  // Makes call to the API Deletes an existing recipe
  public deleteRecipe(recipeId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/delete/${recipeId}`);
  }
}