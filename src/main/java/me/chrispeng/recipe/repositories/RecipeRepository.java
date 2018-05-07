package me.chrispeng.recipe.repositories;

import me.chrispeng.recipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository  extends CrudRepository<Recipe, Long> {
}
