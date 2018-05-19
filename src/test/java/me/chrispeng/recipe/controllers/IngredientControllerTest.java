package me.chrispeng.recipe.controllers;

import me.chrispeng.recipe.commands.IngredientCommand;
import me.chrispeng.recipe.commands.RecipeCommand;
import me.chrispeng.recipe.service.IngredientService;
import me.chrispeng.recipe.service.RecipeService;
import me.chrispeng.recipe.service.UnitOfMeasureServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IngredientControllerTest {

	@Mock
	private RecipeService recipeService;

	@Mock
	private IngredientService ingredientService;

	@Mock
	private UnitOfMeasureServiceImpl unitOfMeasureService;

	private IngredientController ingredientController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
	}

	@Test
	public void getIngredientList() throws Exception {
		// given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

		// when
		mockMvc.perform(get("/recipe/1/ingredients"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/list"))
				.andExpect(model().attributeExists("recipe"));

		// then
		verify(recipeService, times(1)).findCommandById(anyLong());
	}

	@Test
	public void showIngredient() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();

		// when
		when(ingredientService.findCommandByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

		// then
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("ingredient"))
				.andExpect(view().name("recipe/ingredient/show"));
	}

	@Test
	public void saveOrUpdate() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(2L);
		ingredientCommand.setRecipeId(2L);

		// when
		when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);

		// then
		mockMvc.perform(post("/recipe/2/ingredient")
										.contentType(MediaType.APPLICATION_FORM_URLENCODED)
										.param("id", "")
										.param("description", "some string"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/ingredient/2/show"));
	}

	@Test
	public void updateIngredientForm() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();

		// when
		when(ingredientService.findCommandByRecipeIdAndIngredientId(anyLong(), anyLong()))
				.thenReturn(ingredientCommand);
		when(unitOfMeasureService.listAll())
				.thenReturn(new HashSet<>());

		// then
		mockMvc.perform(get("/recipe/2/ingredient/3/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient"))
				.andExpect(model().attributeExists("uomList"));
	}

	@Test
	public void createIngredientForm() throws Exception {
		// given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		// when
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		when(unitOfMeasureService.listAll())
				.thenReturn(new HashSet<>());

		// then
		mockMvc.perform(get("/recipe/1/ingredient/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient"))
				.andExpect(model().attributeExists("uomList"));

		verify(recipeService, times(1)).findCommandById(anyLong());
	}
}
