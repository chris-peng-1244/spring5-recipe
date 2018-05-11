package me.chrispeng.recipe.service;

import me.chrispeng.recipe.commands.RecipeCommand;
import me.chrispeng.recipe.converters.RecipeToRecipeCommand;
import me.chrispeng.recipe.domain.Recipe;
import me.chrispeng.recipe.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {
	public static final String NEW_DESCRIPTION = "New Description";

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private RecipeRepository recipeRepository;
//
//	@Autowired
//	private RecipeCommandToRecipe recipeCommandToRecipe;

	@Autowired
	private RecipeToRecipeCommand recipeToRecipeCommand;

	@Test
	@Transactional
	public void saveOfDescription() throws Exception {
		Iterable<Recipe> recipes = recipeRepository.findAll();
		Recipe testRecipe = recipes.iterator().next();
		RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

		// when
		testRecipeCommand.setDescription(NEW_DESCRIPTION);
		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

		// then
		assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
		assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
		assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
		assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
	}
}
