import { Component, OnInit } from '@angular/core';
import {Diet} from "../diet";
import {DietService} from "../diet.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {Ingredient} from "../ingredient";
import {IngredientService} from "../ingredient.service";

@Component({
  selector: 'app-ingredients',
  templateUrl: './ingredients.component.html',
  styleUrls: ['./ingredients.component.css']
})
export class IngredientsComponent implements OnInit {

  ingredientList!: Ingredient[];
  currentIngrendient!: Ingredient;

  constructor(private ingredientService: IngredientService) { }

  ngOnInit(): void {
    this.getIngredients();
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

  public onAddIngredient(addForm: NgForm): void {
    console.log(addForm.value);
    const ingredientForm = document.getElementById("add-ing-form");
    if(ingredientForm != null) {
      ingredientForm.click();
    }
    this.ingredientService.addIngredient(addForm.value).subscribe(
      (response: Ingredient) => {
        addForm.reset();
        this.getIngredients();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onUpdateIngredient(updateForm: NgForm): void {
    const ingredientForm = document.getElementById('update-ing-form');
    console.log(updateForm.value);

    if (ingredientForm != null) {
      ingredientForm.click();
    }
    this.ingredientService.updateIngredient(updateForm.value).subscribe(
      (response: Ingredient) => {
        console.log(response);
        this.getIngredients();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onDeleteIngredient(id: number): void {
    console.log(id);
    const ingredientForm = document.getElementById("delete-ing-form");
    if(ingredientForm != null) {
      ingredientForm.click();
    }
    this.ingredientService.deleteIngredient(id).subscribe(
      (response: void) => {
        console.log("Deleted.")
        this.getIngredients();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onOpenModal(ingredient: Ingredient, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      this.getIngredients();
      button.setAttribute('data-target', '#addIngModal');
    }
    if (mode === 'edit') {
      this.currentIngrendient = ingredient;

      console.log(this.ingredientList);

      button.setAttribute('data-target', '#editIngModal');
    }
    if (mode === 'delete') {
      this.currentIngrendient = ingredient;
      button.setAttribute('data-target', '#deleteIngModal');
    }
    if (mode === 'view') {
      this.currentIngrendient = ingredient;
      button.setAttribute('data-target', '#viewIngModal');
    }

    if (container != null) {
      container.appendChild(button);
    }
    button.click();
  }

}
