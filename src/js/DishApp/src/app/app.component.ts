import {Component, OnInit} from '@angular/core';
import {Dish} from "./dish";
import {DishService} from "./dish.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {Diet} from "./diet";
import {DietService} from "./diet.service";
import {readSpanComment} from "@angular/compiler-cli/src/ngtsc/typecheck/src/comments";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  public dishesList!: Dish[];
  public dietsList!: Diet[];
  public diets!: Diet[];
  public nullDiets = [];
  public selectedDiet:any;

  constructor(private dishService: DishService, private dietService: DietService) {
  }


  ngOnInit() {
    this.getDishes();
    this.getDiets();
  }



  public getDiets(): void {
    this.dietService.getDiets().subscribe(
      (response: Diet[]) => {
        this.dietsList=response;
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

  public onAddDish(addForm: NgForm): void {
    this.diets = [];
    this.diets.push(this.selectedDiet);
    addForm.controls['diets'].setValue(this.diets);

    const dishForm = document.getElementById('add-dish-form');
    if(dishForm != null) {

      dishForm.click();
    }

    this.dishService.addDish(addForm.value).subscribe(
      (response: Dish) => {
        console.log(response);
        this.getDishes();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public dishToJSON(diets:Diet[]): string {
    let out:string = "[";
    for(let i = 0; i < diets.length; ++i) {
      console.log(diets[i]);
      out += "{";
      out += "\n \"id\": \""+diets[i].id+"\",";
      out += "\n\"type\": \""+diets[i].type+"\"";
      out += "}";
      if(i < diets.length-1) {
        out += ",";
      }
    }
    out += "\n]";
    return out;
  }

  public onOpenModal(dish: Dish, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if(mode === 'add') {
      button.setAttribute('data-target','#addDishModal');
    }
    if(mode === 'edit') {
      button.setAttribute('data-target','#editDishModal');
    }
    if(mode === 'delete') {
      button.setAttribute('data-target','#deleteDishModal');
    }
    if(container != null) {
      container.appendChild(button);
    }
    button.click();

  }
}
