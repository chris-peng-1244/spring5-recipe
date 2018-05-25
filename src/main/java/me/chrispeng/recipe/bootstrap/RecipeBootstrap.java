package me.chrispeng.recipe.bootstrap;

import lombok.extern.slf4j.Slf4j;
import me.chrispeng.recipe.domain.*;
import me.chrispeng.recipe.repositories.CategoryRepository;
import me.chrispeng.recipe.repositories.RecipeRepository;
import me.chrispeng.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
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
@Profile("default")
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
    perfectGuacamole.setDescription("The BEST guacamole!");
    perfectGuacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
        "\n" +
        "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
        "\n" +
        "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
        "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
        "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
        "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
        "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
        "\n" +
        "\n" +
        "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");
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

    Recipe spicyGrilledChikenTacos = new Recipe();
    spicyGrilledChikenTacos.setDescription("Spicy grilled chicken tacos!");
    spicyGrilledChikenTacos.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
        "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
        "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
        "\n" +
        "\n" +
        "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
        "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
        "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
        "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
        "\n" +
        "\n" +
        "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");
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
