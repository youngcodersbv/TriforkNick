package com.Nick.DishProject;

import com.Nick.DishProject.model.*;
import com.Nick.DishProject.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@SpringBootApplication
public class DishProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DishProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner cmd(DishRepository dishR, IngredientRepository ingR,
								 DishIngredientRepository dIr, CategoryRepository catR,
								 DietRepository dietR) {
		return(args -> {
			if(!dishR.findAll().iterator().hasNext()) {
				Set<Ingredient> tempIngredients = new HashSet<>();
				tempIngredients.add(genIngredient("White Bread Flour", "Flour"));
				tempIngredients.add(genIngredient("Salt", "Seasoning"));
				tempIngredients.add(genIngredient("Sugar", "Seasoning"));
				tempIngredients.add(genIngredient("Garlic", "Seasoning"));
				tempIngredients.add(genIngredient("Basil", "Seasoning"));
				tempIngredients.add(genIngredient("Olive Oil", "Oil"));
				tempIngredients.add(genIngredient("Tomato", "Fruit"));
				tempIngredients.add(genIngredient("Mozzarella", "Cheese"));

				Set<Category> categories = new HashSet<>();
				Category category = new Category();
				category.setType("Pizza");
				categories.add(category);

				Set<Diet> diets = new HashSet<>();
				Diet diet = new Diet();
				diet.setType("Regular");
				diets.add(diet);

				Dish dish = new Dish();
				dish.setName("Basic Pizza");
				dish.setAvgTimeToMake(80);
				dish.setRating(10);
				dish.setCalories(500);
				dish.setWarm(true);
				dish.setDescription("Pizzaaaa Mozzarellaaaaa");
				dish.setLongDescription("Ummmm Tasttyyy maybeeeee");
				dish.setImage("32784");
				dish.setDiets(diets);
				dish.setCategories(categories);
				dish.setIngredients(new HashSet<>());

				Iterator<Ingredient> ingredientIter = tempIngredients.iterator();

				Set<Ingredient> ingredients = new HashSet<>();

				Set<DishIngredient> dishIngredients = new HashSet<>();

				while(ingredientIter.hasNext()) {
					DishIngredient dI = new DishIngredient();
					Ingredient current = ingredientIter.next();

					dI.setDish(dish);
					dI.setAmountNeeded(Double.toString(Math.random()));
					dI.setIngredient(current);

					current.setDishes(new HashSet<>());
					current.getDishes().add(dI);

					dish.getIngredients().add(dI);

					dishIngredients.add(dI);
				}

				catR.save(category);
				dietR.save(diet);

				dishR.save(dish);
				ingR.saveAll(ingredients);
				dIr.saveAll(dishIngredients);


			}
		});
	}

	private Ingredient genIngredient(String name, String type) {
		Ingredient ingredient = new Ingredient();

		ingredient.setName(name);
		ingredient.setType(type);
		ingredient.setDishes(new HashSet<>());

		return ingredient;
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList(
				"Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept",
				"X-Requested-With", "Access-Control-Request-Method",
				"Access-Control-Request-Headers"
				));
		corsConfiguration.setExposedHeaders(Arrays.asList(
				"Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept",
				"X-Requested-With", "Access-Control-Request-Method",
				"Access-Control-Request-Headers"
		));
		corsConfiguration.setAllowedMethods(Arrays.asList(
				"GET","POST","PUT","DELETE","OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
				new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}
