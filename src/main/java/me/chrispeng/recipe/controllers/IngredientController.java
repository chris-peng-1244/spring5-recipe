package me.chrispeng.recipe.controllers;

import me.chrispeng.recipe.service.IngredientService;
import me.chrispeng.recipe.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {

	private RecipeService recipeService;

	private IngredientService ingredientService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}

	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		return "recipe/ingredient/list";
	}

	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
	public String showIngredient(@PathVariable String recipeId,
	                             @PathVariable String ingredientId,
	                             Model model) {
		model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdAndIngredientId(
				Long.valueOf(recipeId), Long.valueOf(ingredientId)
		));
		return "recipe/ingredient/show";
	}
}
