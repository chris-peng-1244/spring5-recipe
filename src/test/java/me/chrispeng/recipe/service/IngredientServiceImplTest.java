package me.chrispeng.recipe.service;

import me.chrispeng.recipe.commands.IngredientCommand;
import me.chrispeng.recipe.converters.IngredientCommandToIngredient;
import me.chrispeng.recipe.converters.IngredientToIngredientCommand;
import me.chrispeng.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import me.chrispeng.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import me.chrispeng.recipe.domain.Ingredient;
import me.chrispeng.recipe.domain.Recipe;
import me.chrispeng.recipe.repositories.RecipeRepository;
import me.chrispeng.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sun.rmi.runtime.Log;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

	@Mock
	private RecipeRepository recipeRepository;

	@Mock
	private UnitOfMeasureRepository uomRepository;

	private IngredientService ingredientService;

	private IngredientToIngredientCommand ingredientToIngredientCommand;

	private IngredientCommandToIngredient ingredientCommandToIngredient;

	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(recipeRepository, uomRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);
	}

	@Test
	public void findByRecipeIdAndIngredientIdHappyPath() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		Ingredient ingredient2 = new Ingredient();
		ingredient1.setId(2L);
		Ingredient ingredient3 = new Ingredient();
		ingredient1.setId(3L);

		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);

		Optional<Recipe> recipeOptional = Optional.of(recipe);
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		IngredientCommand ingredientCommand = ingredientService.findCommandByRecipeIdAndIngredientId(1L, 3L);
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());
	}

	@Test
	public void saveIngredientCommand() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(3L);
		ingredientCommand.setRecipeId(2L);

		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(3L);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);

		// when
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

		// then
		assertEquals(Long.valueOf(3L), savedCommand.getId());
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
	}

	@Test
	public void deleteIngredientCommand() {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(1L);
		ingredientCommand.setRecipeId(2L);

		Recipe recipe = new Recipe();
		recipe.setId(2L);
		recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));

		when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

		// when
		ingredientService.deleteById(2L, 1L);

		// then
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
	}
}