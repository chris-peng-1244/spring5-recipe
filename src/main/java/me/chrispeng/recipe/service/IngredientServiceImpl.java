package me.chrispeng.recipe.service;

import lombok.extern.slf4j.Slf4j;
import me.chrispeng.recipe.commands.IngredientCommand;
import me.chrispeng.recipe.converters.IngredientCommandToIngredient;
import me.chrispeng.recipe.converters.IngredientToIngredientCommand;
import me.chrispeng.recipe.domain.Ingredient;
import me.chrispeng.recipe.domain.Recipe;
import me.chrispeng.recipe.repositories.RecipeRepository;
import me.chrispeng.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private RecipeRepository recipeRepository;

	private UnitOfMeasureRepository uomRepository;

	private IngredientToIngredientCommand ingredientToIngredientCommand;

	private IngredientCommandToIngredient ingredientCommandToIngredient;

	public IngredientServiceImpl(RecipeRepository recipeRepository,
	                             UnitOfMeasureRepository uomRepository,
	                             IngredientToIngredientCommand ingredientToIngredientCommand,
	                             IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.recipeRepository = recipeRepository;
		this.uomRepository = uomRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public IngredientCommand findCommandByRecipeIdAndIngredientId(long recipeId, long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			// TODO impl error handling
			log.error("recipe id is not found: " + recipeId);
		}

		Recipe recipe  = recipeOptional.get();
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
				.findFirst();
		if (!ingredientCommandOptional.isPresent()) {
			// TODO impl error handling
			log.error("Ingredient id not found: " + ingredientId);
		}

		return ingredientCommandOptional.get();
	}

	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
		if (!recipeOptional.isPresent()) {
			// TODO impl error
			return new IngredientCommand();
		}
		Recipe recipe = recipeOptional.get();
		Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
				.findFirst();

		if (!ingredientOptional.isPresent()) {
			recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
		} else {
			Ingredient ingredientFound = ingredientOptional.get();
			ingredientFound.setDescription(ingredientCommand.getDescription());
			ingredientFound.setAmount(ingredientCommand.getAmount());
			ingredientFound.setUom(uomRepository.findById(ingredientCommand.getUom().getId())
					.orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
		}

		Recipe savedRecipe = recipeRepository.save(recipe);
		Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
				.findFirst();

		if (!savedIngredientOptional.isPresent()) {
			savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
					.filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
					.filter(ingredient -> ingredient.getUom().getId().equals(ingredientCommand.getUom().getId()))
					.findFirst();
		}
		// TODO check for fail
		return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
	}

	@Override
	public void deleteById(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			log.debug("Recipe id is not found: " + recipeId);
			return;
		}

		Recipe recipe = recipeOptional.get();
		Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.findFirst();

		if (ingredientOptional.isPresent()) {
			Ingredient ingredient = ingredientOptional.get();
			ingredient.setRecipe(null);
			recipe.getIngredients().remove(ingredient);
			recipeRepository.save(recipe);
		}
	}

}
