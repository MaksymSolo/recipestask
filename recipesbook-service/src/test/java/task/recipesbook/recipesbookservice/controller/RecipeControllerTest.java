package task.recipesbook.recipesbookservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import task.recipesbook.recipesbookservice.exception.RecipeNotFoundException;
import task.recipesbook.recipesbookservice.model.Recipe;
import task.recipesbook.recipesbookservice.service.RecipeService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static task.recipesbook.recipesbookservice.util.RandomIngredientGenerator.generateRandomIngredientList;
import static task.recipesbook.recipesbookservice.util.RandomRecipeGenerator.generateRandomRecipe;

@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {

    private static final String BASE_URL = "/recipe";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonContent = objectMapper.writeValueAsString(obj);
        return jsonContent;
    }

    @Test
    public void shouldGetAllRecipesByName() throws Exception {
        String name = "test"; int page = 1; int size = 5;
        String sortBy = "id_recipe"; Sort.Direction direction = Sort.Direction.DESC;
        Recipe recipe = generateRandomRecipe();
        recipe.setRecipeId(1L);
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
        Long recipeId = 1L;
        Recipe recipe = generateRandomRecipe();
        recipe.setRecipeId(recipeId);
        when(recipeService.findRecipeById(recipe.getRecipeId())).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", recipeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.shortDescription").exists());
    }

    @Test
    public void shouldReturnBadRequestWhenGettingNonExistentRecipe() throws Exception {
        Long recipeId = 1L;
        when(recipeService.findRecipeById(recipeId))
                .thenThrow(new RecipeNotFoundException("Recipe with id '" + recipeId + "'was not found"));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", recipeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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

    @Test
    public void shouldReturnBadRequestWhenDeletingNonExistentRecipe() throws Exception {
        Long notExistedIdForDelete = 1L;
        doThrow(new EmptyResultDataAccessException(1))
                .when(recipeService).deleteRecipeById(notExistedIdForDelete);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(BASE_URL + "/delete/{id}", notExistedIdForDelete))
                .andExpect(status().isBadRequest());
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

    /* validation tests start */
    @Test
    public void shouldReturnBadRequestWhenAddingRecipeWithMissingName() throws Exception {
        Recipe recipe = generateRandomRecipe();
        recipe.setName(null);
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(mockConstraintViolation("Recipe name is required"));
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        when(recipeService.saveRecipe(recipe)).thenThrow(exception);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipe)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingRecipeWithInvalidName() throws Exception {
        Recipe recipe = generateRandomRecipe();
        recipe.setName("a");
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(mockConstraintViolation("Recipe name must be between 2 and 30 characters"));
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        when(recipeService.saveRecipe(recipe)).thenThrow(exception);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipe)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingRecipeWithMissingShortDescription() throws Exception {
        Recipe recipe = generateRandomRecipe();
        recipe.setShortDescription(null);
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(mockConstraintViolation("Short description is required"));
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        when(recipeService.saveRecipe(recipe)).thenThrow(exception);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipe)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingRecipeWithInvalidShortDescription() throws Exception {
        Recipe recipe = generateRandomRecipe();
        recipe.setShortDescription("a");
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(mockConstraintViolation("Short description must be between 2 and 254 characters"));
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        when(recipeService.saveRecipe(recipe)).thenThrow(exception);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipe)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingRecipeWithInvalidGuide() throws Exception {
        Recipe recipe = generateRandomRecipe();
        recipe.setGuide("a");
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(mockConstraintViolation("Guide must be between 2 and 800 characters"));
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        when(recipeService.saveRecipe(recipe)).thenThrow(exception);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipe)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingRecipeWithMissingGuide() throws Exception {
        Recipe recipe = generateRandomRecipe();
        recipe.setGuide(null);
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(mockConstraintViolation("Guide is required"));
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        when(recipeService.saveRecipe(recipe)).thenThrow(exception);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipe)))
                .andExpect(status().isBadRequest());
    }

    private ConstraintViolation<?> mockConstraintViolation(String messageTemplate) {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn(messageTemplate);
        //represents the path to the object that is being validated.
        Path path = mock(Path.class);
        when(path.toString()).thenReturn("path to object");
        when(violation.getPropertyPath()).thenReturn(path);
        return violation;
    }
    /* validation tests end */
}
