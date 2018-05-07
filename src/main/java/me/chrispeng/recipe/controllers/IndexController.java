package me.chrispeng.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import me.chrispeng.recipe.domain.Category;
import me.chrispeng.recipe.domain.Recipe;
import me.chrispeng.recipe.domain.UnitOfMeasure;
import me.chrispeng.recipe.repositories.CategoryRepository;
import me.chrispeng.recipe.repositories.UnitOfMeasureRepository;
import me.chrispeng.recipe.service.RecipeService;
import me.chrispeng.recipe.service.RecipeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

	private RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping({"", "/", "/index"})
	public String index(Model model) {
		Iterable<Recipe> recipes = recipeService.getRecipes();
		model.addAttribute("recipes", recipes);
		return "index";
	}
}
