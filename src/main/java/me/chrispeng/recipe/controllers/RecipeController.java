package me.chrispeng.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import me.chrispeng.recipe.commands.RecipeCommand;
import me.chrispeng.recipe.exceptions.NotFoundException;
import me.chrispeng.recipe.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {
	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(new Long(id)));
		return "recipe/show";
	}

	@RequestMapping("recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return RECIPE_RECIPEFORM_URL;
	}

	@RequestMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return RECIPE_RECIPEFORM_URL;
	}

	@PostMapping
	@RequestMapping("recipe")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command,
	                           BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
			return RECIPE_RECIPEFORM_URL;
		}
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}


	@RequestMapping("/recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id)
	{
		log.debug("Delete recipe: " + id);
		Long idValue = Long.valueOf(id);
		recipeService.deleteById(idValue);
		return "redirect:/";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		log.error("Handling not found exception");
		log.error(exception.getMessage());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404");
		modelAndView.addObject("exception", exception);
		return modelAndView;
	}

}
