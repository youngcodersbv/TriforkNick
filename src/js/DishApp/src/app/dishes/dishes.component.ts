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
import {resolve} from "@angular/compiler-cli/src/ngtsc/file_system";

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

  public currentModal!: string;

  public amount: {
    ing: number[];
    am: number[];
  } = {
    ing: [],
    am: [],
  };

  public deleteDish!: Dish;
  public currentDish!: Dish;

  public selectedFile!: File;

  public imageBase64: any;

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


  //flips values true/false of variable
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

  //inits the state of checkboxes by currentDish
  public updateInitSelectedDiets() {
    for(let diet of this.dietsList) {
      this.currentDish.diets.forEach(value => {
        if(value.id == diet.id) {
          diet.selected=true;
        }
      });
    }
  }

  public updateInitSelectedCategories() {
    for(let category of this.categoryList) {
      this.currentDish.categories.forEach(value => {
        if(value.id == category.id) {
          category.selected=true;
        }
      });
    }
  }

  public updateInitSelectedIngredients() {
    for(let ingredient of this.ingredientList) {
      this.currentDish.ingredients.forEach(value => {
        if(value.id == ingredient.id) {
          ingredient.selected=true;
        }
      });
    }
  }

  //gets to be filtered values
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

  //flips selected filter true/falseS
  public onFilterSelectedCategory(category:Category): void {
    if (category.selectedFilter) {
      category.selectedFilter = false;
    } else {
      category.selectedFilter = true;
    }
  }

  public onFilterSelectedDiet(diet: Diet): void {
    if(diet.selectedFilter) {
      diet.selectedFilter = false;
    } else {
      diet.selectedFilter = true;
    }
  }

  //Returns filtered list
  public onFilterDishes(): void {

    var diets: string[] = [];
    var categories: string[] = [];

    this.getFilterCategories().forEach(category => categories.push(category.type));
    this.getFilterDiets().forEach(diet => diets.push(diet.type));

    this.dishService.getDishesFiltered(categories, diets).subscribe(
      (response: Dish[]) => {
        console.log(response);
        this.dishesList=response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  //Image handling: immediately converts to Base64 on attachment
  public onFileChanged(event: Event) {
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if(fileList) {
      this.selectedFile = fileList[0];
    }
    this.encodeImageFileAsBase64(this.selectedFile);
  }

  public encodeImageFileAsBase64(element: any) {
    let base64 = "";
    var file = element;
    const promise = new Promise((resolve) => {
      var reader = new FileReader();
      reader.onload = function () {
        resolve(base64 = reader.result as string)};
      reader.readAsDataURL(file);
    });
    promise.then(() => {
      this.imageBase64 = base64.replace(/^data:image\/[a-z]+;base64,/, "");
    });
  }

  public onClose() {

  }

  //Gets selected by checkbox things (used during adding/updating to pass
  //                                  selected data into form).
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

  //Resets the 'selected' variable back to false for fresh startup of form.
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


  public onAddDish(addForm: NgForm): void {
    //overrides the controls values of the form to pass in selected things
    addForm.controls['diets'].setValue(this.getSelectedDiets());
    addForm.controls['categories'].setValue(this.getSelectedCategories());
    addForm.controls['ingredients'].setValue(this.getSelectedIngredients());

    addForm.controls['image'].setValue(this.imageBase64);

    let ingredients: Ingredient[] = addForm.controls['ingredients'].value;

    this.amount = {ing:[],am:[]};

    console.log('entering');
    for(var ing of ingredients) {
      console.log('iia'+ing.id);
      console.log(addForm.controls['iia'+ing.id].value);
      this.amount.ing.push(ing.id);
      this.amount.am.push(addForm.controls['iia'+ing.id].value);
    }
    console.log(this.amount);

    addForm.controls['amount'].setValue(this.amount);

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
        this.amount = {
          ing: [],
          am: []
        };
        addForm.reset();
        addForm.resetForm();
        this.currentModal='';
        this.getDishes();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
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

    updateForm.controls['image'].setValue(this.imageBase64);

    let ingredients: Ingredient[] = updateForm.controls['ingredients'].value;

    this.amount = {ing:[],am:[]};

    console.log('entering');
    for(var ing of ingredients) {
      console.log('iie'+ing.id);
      console.log(updateForm.controls['iie'+ing.id].value);
      this.amount.ing.push(ing.id);
      this.amount.am.push(updateForm.controls['iie'+ing.id].value);
    }
    console.log(this.amount);

    updateForm.controls['amount'].setValue(this.amount);

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
        this.amount = {
          ing: [],
          am: []
        };
        this.currentModal='';
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
      this.imageBase64=null;
      this.currentModal='add';
      this.wipeSelectedIngredients();
      this.wipeSelectedDiets();
      this.wipeSelectedCategories();
      button.setAttribute('data-target', '#addDishModal');
    }
    if (mode === 'edit') {
      this.wipeSelectedCategories();
      this.wipeSelectedDiets();
      this.wipeSelectedIngredients();
      this.currentDish = dish;

      this.currentModal='edit';
      this.imageBase64=this.currentDish.image;
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


  public prepareDishFormWithSelectedData(form: NgForm) {

  }

  public resetForm(form: NgForm) {
    form.reset();
  }
}
