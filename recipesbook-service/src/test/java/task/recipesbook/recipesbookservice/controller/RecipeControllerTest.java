package task.recipesbook.recipesbookservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import task.recipesbook.recipesbookservice.model.Recipe;
import task.recipesbook.recipesbookservice.service.RecipeService;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static util.RandomIngredientGenerator.generateRandomIngredientList;
import static util.RandomRecipeGenerator.generateRandomRecipe;

@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {

    private static final String BASE_URL = "/recipe";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Test
    public void shouldGetAllRecipesByName() throws Exception {
        String name = "test"; int page = 1; int size = 5;
        String sortBy = "id_recipe"; Sort.Direction direction = Sort.Direction.DESC;
        Recipe recipe = generateRandomRecipe();
        recipe.setIngredients(generateRandomIngredientList(3));
        Page<Recipe> recipesPage = new PageImpl<>(Collections.singletonList(recipe));
        when(recipeService.findAllByName(name, page, size, sortBy, direction)).thenReturn(recipesPage);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/all")
                        .param("name", name)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sortBy", sortBy)
                        .param("direction", direction.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].recipeId").exists())
                .andExpect(jsonPath("$.content[0].name").exists())
                .andExpect(jsonPath("$.content[0].shortDescription").exists())
                .andExpect(jsonPath("$.content[0].ingredients").isArray());
    }

    @Test
    public void shouldGetRecipeById() throws Exception {
        Recipe recipe = generateRandomRecipe();
        when(recipeService.findRecipeById(recipe.getRecipeId())).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", recipe.getRecipeId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.shortDescription").exists());
    }

    @Test
    public void shouldAddRecipe() throws Exception {
        Recipe recipe = generateRandomRecipe();

        when(recipeService.saveRecipe(recipe)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(recipe.getRecipeId()))
                .andExpect(jsonPath("$.name").value(recipe.getName()))
                .andExpect(jsonPath("$.shortDescription").value(recipe.getShortDescription()))
                .andExpect(jsonPath("$.guide").value(recipe.getGuide()));
    }

    @Test
    public void shouldUpdateRecipe() throws Exception {
        Recipe recipe = generateRandomRecipe();

        when(recipeService.updateRecipe(recipe)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(recipe.getRecipeId()))
                .andExpect(jsonPath("$.name").value(recipe.getName()))
                .andExpect(jsonPath("$.shortDescription").value(recipe.getShortDescription()))
                .andExpect(jsonPath("$.guide").value(recipe.getGuide()));
    }

    @Test
    public void shouldDeleteRecipeById() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/delete/{id}", id))
                .andExpect(status().isNoContent());
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonContent = objectMapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
