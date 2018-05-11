package me.chrispeng.recipe.service;

import me.chrispeng.recipe.commands.RecipeCommand;
import me.chrispeng.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
	Set<Recipe> getRecipes();
	Recipe findById(Long l);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
}
