package me.chrispeng.recipe.service;

import me.chrispeng.recipe.converters.RecipeCommandToRecipe;
import me.chrispeng.recipe.converters.RecipeToRecipeCommand;
import me.chrispeng.recipe.domain.Recipe;
import me.chrispeng.recipe.exceptions.NotFoundException;
import me.chrispeng.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

	private RecipeServiceImpl recipeService;

	@Mock
	private RecipeToRecipeCommand recipeToRecipeCommand;

	@Mock
	private RecipeCommandToRecipe recipeCommandToRecipe;

	@Mock
	private RecipeRepository recipeRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, recipeToRecipeCommand, recipeCommandToRecipe);
	}

	@Test
	public void getRecipes() {
		Recipe recipe = new Recipe();
		Set<Recipe> recipeData = new HashSet<>();
		recipeData.add(recipe);

		when(recipeService.getRecipes()).thenReturn(recipeData);

		Set<Recipe> recipes = recipeService.getRecipes();
		assertEquals(1, recipes.size());
		verify(recipeRepository, times(1)).findAll();
	}

	@Test
	public void findById() {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		Recipe recipeReturned = recipeService.findById(1L);
		assertNotNull("Null recipe returned", recipeReturned);
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, never()).findAll();
	}

	@Test(expected = NotFoundException.class)
	public void findByIdNotFound() {
		Optional<Recipe> recipeOptional = Optional.empty();
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		recipeService.findById(1L);
		fail("Should not reach here");
	}


	@Test
	public void deleteById() {
		// given
		Long idToDelete = 2L;

		// when
		recipeService.deleteById(idToDelete);

		// then
		verify(recipeRepository, times(1)).deleteById(anyLong());
	}
}