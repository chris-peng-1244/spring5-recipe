package me.chrispeng.recipe.bootstrap;

import lombok.extern.slf4j.Slf4j;
import me.chrispeng.recipe.domain.*;
import me.chrispeng.recipe.repositories.CategoryRepository;
import me.chrispeng.recipe.repositories.RecipeRepository;
import me.chrispeng.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private RecipeRepository recipeRepository;
	private CategoryRepository categoryRepository;
	private UnitOfMeasureRepository unitOfMeasureRepository;

	public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.recipeRepository = recipeRepository;
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		recipeRepository.saveAll(getRecipes());
	}

	private List<Recipe> getRecipes()
	{
		log.debug("Creating recipe");
		List<Recipe> recipes = new ArrayList<>();
		Recipe perfectGuacamole = new Recipe();
		perfectGuacamole.getCategories().add(categoryRepository.findByDescription("American").get());
		perfectGuacamole.setPrepTime(10);
		perfectGuacamole.setCookTime(30);
		perfectGuacamole.setDifficulty(Difficulty.MODERATE);
		perfectGuacamole.setDescription("The BEST guacamole! So easy to make with ripe avocados, salt, serrano chiles, cilantro and lime. Garnish with red radishes or jicama. Serve with tortilla chips.");
		Note notes = new Note() ;
		notes.setRecipeNotes("Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.");
		notes.setRecipe(perfectGuacamole);
		perfectGuacamole.setNotes(notes);
		perfectGuacamole.setServings(4);
		perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

		Set<Ingredient> ingredients =  new HashSet<>();
		Ingredient ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(2));
		ingredient.setDescription("ripe avocados");
		ingredient.setRecipe(perfectGuacamole);
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Whole").get());
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(0.5));
		ingredient.setDescription("Kosher salt");
		ingredient.setRecipe(perfectGuacamole);
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(1));
		ingredient.setDescription("fresh lime juice or lemon juice");
		ingredient.setRecipe(perfectGuacamole);
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(2));
		ingredient.setDescription("minced red onion or thinly sliced green onion");
		ingredient.setRecipe(perfectGuacamole);
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(2));
		ingredient.setDescription("serrano chiles, stems and seeds removed, minced");
		ingredient.setRecipe(perfectGuacamole);
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Whole").get());
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(2));
		ingredient.setDescription("cilantro, finely chopped");
		ingredient.setRecipe(perfectGuacamole);
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(1));
		ingredient.setDescription("freshly grated black pepper");
		ingredient.setRecipe(perfectGuacamole);
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Dash").get());
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(0.5));
		ingredient.setDescription("ripe tomato, seeds and pulp removed, chopped");
		ingredient.setRecipe(perfectGuacamole);
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Whole").get());
		ingredients.add(ingredient);

		perfectGuacamole.setIngredients(ingredients);
		perfectGuacamole.setDirections("");

		Recipe spicyGrilledChikenTacos = new Recipe();
		spicyGrilledChikenTacos.setDescription("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");
		spicyGrilledChikenTacos.setDirections("");
		spicyGrilledChikenTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
		spicyGrilledChikenTacos.getCategories().add(categoryRepository.findByDescription("Italian").get());
		spicyGrilledChikenTacos.getCategories().add(categoryRepository.findByDescription("Fast Food").get());
		spicyGrilledChikenTacos.setServings(6);
		spicyGrilledChikenTacos.setPrepTime(20);
		spicyGrilledChikenTacos.setCookTime(15);
		notes = new Note();
		notes.setRecipe(spicyGrilledChikenTacos);
		notes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
		spicyGrilledChikenTacos.setNotes(notes);
		spicyGrilledChikenTacos.setDifficulty(Difficulty.HARD);
		ingredients = new HashSet<>();
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(2));
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
		ingredient.setDescription("ancho chili powder");
		ingredient.setRecipe(spicyGrilledChikenTacos);
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(1));
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
		ingredient.setDescription("dried oregano");
		ingredient.setRecipe(spicyGrilledChikenTacos);
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(1));
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
		ingredient.setDescription("dried cumin");
		ingredient.setRecipe(spicyGrilledChikenTacos);
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(1));
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
		ingredient.setDescription("sugar");
		ingredient.setRecipe(spicyGrilledChikenTacos);
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(0.5));
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
		ingredient.setDescription("salt");
		ingredient.setRecipe(spicyGrilledChikenTacos);
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(1));
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Whole").get());
		ingredient.setDescription("love garlic, finely chopped");
		ingredient.setRecipe(spicyGrilledChikenTacos);
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(1));
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
		ingredient.setDescription("finely grated orange zest");
		ingredient.setRecipe(spicyGrilledChikenTacos);
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(3));
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
		ingredient.setDescription("fresh-squeezed orange juice");
		ingredient.setRecipe(spicyGrilledChikenTacos);
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(2));
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
		ingredient.setDescription("olive oil");
		ingredient.setRecipe(spicyGrilledChikenTacos);
		ingredients.add(ingredient);
		ingredient = new Ingredient();
		ingredient.setAmount(new BigDecimal(1.25));
		ingredient.setUom(unitOfMeasureRepository.findByDescription("Pound").get());
		ingredient.setDescription("skinless, boneless chicken thighs");
		ingredient.setRecipe(spicyGrilledChikenTacos);
		ingredients.add(ingredient);
		spicyGrilledChikenTacos.setIngredients(ingredients);
		recipes.add(perfectGuacamole);
		recipes.add(spicyGrilledChikenTacos);
		return recipes;
	}
}
