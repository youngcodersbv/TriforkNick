import { Component, OnInit } from '@angular/core';
import {Dish} from "../dish";
import {Diet} from "../diet";
import {Category} from "../category";
import {Ingredient} from "../ingredient";
import {DishService} from "../dish.service";
import {DietService} from "../diet.service";
import {CategoryService} from "../category.service";
import {IngredientService} from "../ingredient.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {FilterList} from "./filterList";
import {filter} from "rxjs/operators";

@Component({
  selector: 'app-dishes',
  templateUrl: './dishes.component.html',
  styleUrls: ['./dishes.component.css']
})
export class DishesComponent implements OnInit {

  public dishesList!: Dish[];
  public dietsList: Diet[] = [];
  public categoryList: Category[] = [];
  public ingredientList: Ingredient[] = [];
  public filterList!: FilterList;

  public deleteDish!: Dish;
  public currentDish!: Dish;

  constructor(private dishService: DishService,
              private dietService: DietService,
              private categoryService: CategoryService,
              private ingredientService: IngredientService) {
  }


  ngOnInit() {
    this.getDishes();
    this.getDiets();
    this.getCategories();
    this.getIngredients();
    this.filterList = new class implements FilterList {
      categories: Category[] = [];
      diets: Diet[] = [];
    }
  }

  public getDiets(): void {
    this.dietService.getDiets().subscribe(
      (response: Diet[]) => {
        this.dietsList = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public getIngredients(): void {
    this.ingredientService.getIngredients().subscribe(
      (response: Ingredient[]) => {
        this.ingredientList = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public getCategories(): void {
    this.categoryService.getCategories().subscribe(
      (response: Category[]) => {
        this.categoryList = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public getDishes(): void {
    this.dishService.getDishes().subscribe(
      (response: Dish[]) => {
        this.dishesList = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onUpdateSelectedIngredient(value: Ingredient) {
    if (value.selected) {
      value.selected = false;
    } else {
      value.selected = true;
    }
  }

  public onUpdateSelectedDiet(value: Diet) {
    if (value.selected) {
      value.selected = false;
    } else {
      value.selected = true;
    }
  }

  public onUpdateSelectedCategory(value: Category) {
    if (value.selected) {
      value.selected = false;
    } else {
      value.selected = true;
    }
  }

  public updateInitSelectedDiets() {
    for (let i = 0; i < this.dietsList.length; ++i) {
      for (let j = 0; j < this.currentDish.diets.length; ++j) {
        console.log(this.dietsList[i].id + " DietsList " + this.dietsList[i].type);
        console.log(this.currentDish.diets[j].id + " SelectedDiets" + this.currentDish.diets[j].type);
        if (this.dietsList[i].id == this.currentDish.diets[j].id) {
          console.log("EQUAL!");
          this.dietsList[i].selected = true;
          this.currentDish.diets[j].selected = true;
          this.currentDish.diets[j] = this.dietsList[i];
        }
      }
    }
  }

  public updateInitSelectedCategories() {
    for (let i = 0; i < this.categoryList.length; ++i) {
      for (let j = 0; j < this.currentDish.categories.length; ++j) {
        console.log(this.categoryList[i].id + " CategoriesList " + this.categoryList[i].type);
        console.log(this.currentDish.categories[j].id + " CategoriesSel." + this.currentDish.categories[j].type);
        if (this.categoryList[i].id == this.currentDish.categories[j].id) {
          console.log("EQUAL!");
          this.categoryList[i].selected = true;
          this.currentDish.categories[j].selected = true;
          this.currentDish.categories[j] = this.categoryList[i];
        }
      }
    }
  }

  public updateInitSelectedIngredients() {
    for (let i = 0; i < this.ingredientList.length; ++i) {
      for (let j = 0; j < this.currentDish.ingredients.length; j++) {
        if (this.ingredientList[i].id == this.currentDish.ingredients[j].id) {
          this.ingredientList[i].selected = true;
          this.currentDish.ingredients[j].selected = true;
          this.currentDish.ingredients[j] = this.ingredientList[i];
        }
      }
    }
  }

  public onFilterSelectedCategory(category:Category): void {
    if (category.selectedFilter) {
      category.selectedFilter = false;
    } else {
      category.selectedFilter = true;
    }
  }

  public getFilterCategories(): Category[] {
    var res: Category[] = [];
    for(let i = 0; i<this.categoryList.length; ++i) {
      if(this.categoryList[i].selectedFilter) {
        res.push(this.categoryList[i]);
      }
    }
    return res;
  }

  public getFilterDiets(): Diet[] {
    var res: Diet[] = [];
    for(let i = 0; i<this.dietsList.length; ++i) {
      if(this.dietsList[i].selectedFilter) {
        res.push(this.dietsList[i]);
      }
    }
    return res;
  }

  public onFilterSelectedDiet(diet: Diet): void {
    if(diet.selectedFilter) {
      diet.selectedFilter = false;
    } else {
      diet.selectedFilter = true;
    }
  }

  public onFilterDishes(filterForm: NgForm): void {
    filterForm.controls['diets'].setValue(this.getFilterDiets());
    filterForm.controls['categories'].setValue(this.getFilterCategories());

    this.filterList.diets = filterForm.controls['diets'].value;
    this.filterList.categories = filterForm.controls['categories'].value;

    console.log(this.filterList);
    var diets: string[] = [];
    var categories: string[] = [];

    this.filterList.categories.forEach(category => categories.push(category.type));
    this.filterList.diets.forEach(diet => diets.push(diet.type));


    this.dishService.getDishesFiltered(categories, diets).subscribe(
      (response: Dish[]) => {
        console.log(response);
        this.dishesList=response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
    });
  }

  public onAddDish(addForm: NgForm): void {
    addForm.controls['diets'].setValue(this.getSelectedDiets());
    addForm.controls['categories'].setValue(this.getSelectedCategories());
    addForm.controls['ingredients'].setValue(this.getSelectedIngredients());

    const dishForm = document.getElementById('add-dish-form');
    if (dishForm != null) {
      dishForm.click();
    }
    console.log(addForm.value);

    this.dishService.addDishDto(addForm.value).subscribe(
      (response: Dish) => {
        console.log(response);
        this.wipeSelectedDiets();
        this.wipeSelectedCategories();
        this.wipeSelectedIngredients();
        addForm.reset();
        this.getDishes();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onClose() {

  }

  public getSelectedDiets(): Diet[] {
    var res: Diet[] = [];
    for (let i = 0; i < this.dietsList.length; ++i) {
      if (this.dietsList[i].selected) {
        res.push(this.dietsList[i]);
      }
    }
    return res;
  }

  public getSelectedCategories(): Category[] {
    var res: Category[] = [];
    for (let i = 0; i < this.categoryList.length; ++i) {
      if (this.categoryList[i].selected) {
        res.push(this.categoryList[i]);
      }
    }
    return res;
  }

  public getSelectedIngredients(): Ingredient[] {
    var res: Ingredient[] = [];
    for (let i = 0; i < this.ingredientList.length; ++i) {
      if (this.ingredientList[i].selected) {
        res.push(this.ingredientList[i]);
      }
    }
    return res;
  }

  public wipeSelectedDiets() {
    for (let i = 0; i < this.dietsList.length; ++i) {
      this.dietsList[i].selected = false;
    }
  }

  public wipeSelectedCategories() {
    for (let i = 0; i < this.categoryList.length; ++i) {
      this.categoryList[i].selected = false;
    }
  }

  public wipeSelectedIngredients() {
    for (let i = 0; i < this.ingredientList.length; ++i) {
      this.ingredientList[i].selected = false;
    }
  }

  public onDeleteDish(dishId: number): void {
    const dishForm = document.getElementById('delete-dish-form');
    if (dishForm != null) {
      dishForm.click();
    }
    this.dishService.deleteDish(dishId).subscribe(
      (response: void) => {
        console.log("Deleted");
        this.getDishes();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onUpdateDish(updateForm: NgForm): void {
    const dishForm = document.getElementById('update-dish-form');
    updateForm.controls['diets'].setValue(this.getSelectedDiets());
    updateForm.controls['categories'].setValue(this.getSelectedCategories());
    updateForm.controls['ingredients'].setValue(this.getSelectedIngredients());

    console.log(updateForm.value);

    if (dishForm != null) {
      dishForm.click();
    }
    this.dishService.updateDishDto(updateForm.value).subscribe(
      (response: Dish) => {
        console.log(response);
        this.wipeSelectedDiets();
        this.wipeSelectedCategories();
        this.wipeSelectedIngredients();
        this.getDishes();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onOpenModal(dish: Dish, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      this.getDiets();
      this.getIngredients();
      this.getCategories();
      button.setAttribute('data-target', '#addDishModal');
    }
    if (mode === 'edit') {

      this.currentDish = dish;

      this.updateInitSelectedCategories();
      this.updateInitSelectedDiets();
      this.updateInitSelectedIngredients();

      button.setAttribute('data-target', '#editDishModal');
    }
    if (mode === 'delete') {
      this.deleteDish = dish;
      button.setAttribute('data-target', '#deleteDishModal');
    }
    if (mode === 'view') {
      this.currentDish = dish;
      button.setAttribute('data-target', '#viewDishModal');
    }

    if (container != null) {
      container.appendChild(button);
    }
    button.click();
  }

  public resetForm(form: NgForm) {
    form.reset();
  }
}
