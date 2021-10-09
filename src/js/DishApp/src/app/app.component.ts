import {Component, OnInit} from '@angular/core';
import {Dish} from "./dish";
import {DishService} from "./dish.service";
import {HttpErrorResponse} from "@angular/common/http";
import {FormArray, NgForm, Validators} from "@angular/forms";
import {Diet} from "./diet";
import {DietService} from "./diet.service";
import {Category} from "./category";
import {CategoryService} from "./category.service";
import {Ingredient} from "./ingredient";
import {IngredientService} from "./ingredient.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  constructor() {
  }


  ngOnInit() {
  }

}
