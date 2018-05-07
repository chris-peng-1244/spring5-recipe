package me.chrispeng.recipe.service;

import me.chrispeng.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
	public Set<Recipe> getRecipes();
}
