package task.recipesbook.recipesbookservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static task.recipesbook.recipesbookservice.util.RandomRecipeGenerator.generateRandomRecipe;
import static task.recipesbook.recipesbookservice.util.RandomRecipeGenerator.generateRandomResipeList;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import task.recipesbook.recipesbookservice.exception.RecipeNotFoundException;
import task.recipesbook.recipesbookservice.model.Recipe;
import task.recipesbook.recipesbookservice.repository.RecipeRepository;
import task.recipesbook.recipesbookservice.service.impl.RecipeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void shouldFindAllByName() {
        List<Recipe> recipes = generateRandomResipeList(3);
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, "name");
        Page<Recipe> page = new PageImpl<>(recipes, pageRequest, 2);
        when(recipeRepository.findAllByNameContainingIgnoreCase(eq("a"), eq(pageRequest))).thenReturn(page);

        Page<Recipe> result = recipeService.findAllByName("a", 1, 5, "name", Sort.Direction.ASC);

        verify(recipeRepository, times(1)).findAllByNameContainingIgnoreCase(eq("a"), eq(pageRequest));
        assertEquals(page, result);
    }

    @Test
    public void shouldFindRecipeById() {
        Long idForSearch = 1l;
        Recipe recipe = generateRandomRecipe();
        recipe.setRecipeId(idForSearch);
        when(recipeRepository.findById(recipe.getRecipeId())).thenReturn(Optional.of(recipe));

        Recipe result = recipeService.findRecipeById(idForSearch);

        verify(recipeRepository, times(1)).findById(idForSearch);
        assertEquals(recipe, result);
    }

    @Test
    public void shouldSaveRecipe() {
        Recipe recipe = generateRandomRecipe();
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        RecipeServiceImpl recipeService = new RecipeServiceImpl(recipeRepository);
        Recipe result = recipeService.saveRecipe(recipe);

        verify(recipeRepository, times(1)).save(recipe);
        assertEquals(recipe, result);
    }

    @Test
    public void shouldUpdateRecipe() {
        Recipe recipe = generateRandomRecipe();
        when(recipeRepository.save(eq(recipe))).thenReturn(recipe);

        Recipe result = recipeService.updateRecipe(recipe);

        verify(recipeRepository, times(1)).save(recipe);
        assertEquals(recipe, result);
    }

    @Test
    public void shouldDeleteRecipeById() {
        Long idForDelete = 1L;
        doNothing().when(recipeRepository).deleteById(idForDelete);

        recipeService.deleteRecipeById(idForDelete);

        verify(recipeRepository, times(1)).deleteById(idForDelete);
    }

    @Test
    public void shouldThrowExceptionWhenFindRecipeByIdNotFound() {
        Long idForSearch = 1L;
        when(recipeRepository.findById(idForSearch)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.findRecipeById(idForSearch);
        });

        verify(recipeRepository, times(1)).findById(idForSearch);
    }
}
