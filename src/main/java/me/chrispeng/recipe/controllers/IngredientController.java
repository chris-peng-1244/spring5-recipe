package me.chrispeng.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import me.chrispeng.recipe.commands.IngredientCommand;
import me.chrispeng.recipe.commands.RecipeCommand;
import me.chrispeng.recipe.commands.UnitOfMeasureCommand;
import me.chrispeng.recipe.domain.Ingredient;
import me.chrispeng.recipe.service.IngredientService;
import me.chrispeng.recipe.service.RecipeService;
import me.chrispeng.recipe.service.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Controller
public class IngredientController {

	private RecipeService recipeService;

	private IngredientService ingredientService;

	private UnitOfMeasureService unitOfMeasureService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
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

	@PostMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand command) {
		IngredientCommand ingredientCommand = ingredientService.saveIngredientCommand(command);
		log.debug("saved recipe id：" + ingredientCommand.getRecipeId());
		log.debug("saved ingredient id：" + ingredientCommand.getId());
		return "redirect:/recipe/" + ingredientCommand.getRecipeId() + "/ingredient/"
				+ ingredientCommand.getId() + "/show";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
	public String updateIngredientForm(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		model.addAttribute("uomList", unitOfMeasureService.listAll());
		return "recipe/ingredient/ingredientform";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/new")
	public String createIngredientForm(@PathVariable String recipeId, Model model) {
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		// TODO raise exception when null

		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeCommand.getId());
		ingredientCommand.setUom(new UnitOfMeasureCommand());

		Set<UnitOfMeasureCommand> uomList = unitOfMeasureService.listAll();
		model.addAttribute("ingredient", ingredientCommand);
		model.addAttribute("uomList", uomList);
		return "recipe/ingredient/ingredientform";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
		ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
}
