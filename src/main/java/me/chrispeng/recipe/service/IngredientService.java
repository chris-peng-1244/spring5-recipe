package me.chrispeng.recipe.service;

import me.chrispeng.recipe.commands.IngredientCommand;
import org.springframework.stereotype.Service;

@Service
public interface IngredientService {

	IngredientCommand findCommandByRecipeIdAndIngredientId(long recipeId, long ingredientId);
}
