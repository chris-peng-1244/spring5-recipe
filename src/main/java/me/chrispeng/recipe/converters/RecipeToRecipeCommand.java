package me.chrispeng.recipe.converters;

import lombok.Synchronized;
import me.chrispeng.recipe.commands.RecipeCommand;
import me.chrispeng.recipe.domain.Category;
import me.chrispeng.recipe.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

	private final CategoryToCategoryCommand categoryConverter;
	private final IngredientToIngredientCommand ingredientConverter;
	private final NotesToNotesCommand notesConverter;

	public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter, IngredientToIngredientCommand ingredientConverter, NotesToNotesCommand notesConverter) {
		this.categoryConverter = categoryConverter;
		this.ingredientConverter = ingredientConverter;
		this.notesConverter = notesConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe source) {
		if (null == source) {
			return null;
		}

		final RecipeCommand command = new RecipeCommand();
		command.setId(source.getId());
		command.setCookTime(source.getCookTime());
		command.setPrepTime(source.getPrepTime());
		command.setDescription(source.getDescription());
		command.setDifficulty(source.getDifficulty());
		command.setDirections(source.getDirections());
		command.setServings(source.getServings());
		command.setSource(source.getSource());
		command.setUrl(source.getSource());
		command.setImage(source.getImage());
		command.setNotes(notesConverter.convert(source.getNotes()));

		if (source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories()
					.forEach((Category category) -> command.getCategories().add(categoryConverter.convert(category)));
		}

		if (source.getIngredients() != null && source.getIngredients().size() > 0) {
			source.getIngredients()
					.forEach(ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
		}
		return command;
	}
}
