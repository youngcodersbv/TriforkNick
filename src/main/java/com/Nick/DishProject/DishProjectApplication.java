package com.Nick.DishProject;

import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.model.DishIngredient;
import com.Nick.DishProject.model.DishIngredientId;
import com.Nick.DishProject.model.Ingredient;
import com.Nick.DishProject.repository.DishIngredientRepository;
import com.Nick.DishProject.repository.DishRepository;
import com.Nick.DishProject.repository.IngredientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
public class DishProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DishProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner cmd(DishRepository dr, IngredientRepository ir,
								 DishIngredientRepository dIr) {
		return(args -> {
			if(!dr.findAll().iterator().hasNext()) {
				Dish dish = new Dish();
				dish.setName("Hello Test");
				dish.setCalories(100);
				dish.setRating(100);
				dish.setDescription("Hello");
				dish.setWarm(true);
				dish.setAvgTimeToMake(10);

				Ingredient ingredient = new Ingredient();
				ingredient.setName("Hello Yum");
				ingredient.setType("VEGETABLE");

				DishIngredient dI = new DishIngredient();
				dI.setDish(dish);
				dI.setIngredient(ingredient);

				dish.getIngredients().add(dI);
				ingredient.getDishes().add(dI);

				ir.save(ingredient);
				dr.save(dish);

				dIr.save(dI);
			}
		});
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
