import {Component, OnInit} from '@angular/core';
import {Dish} from "./dish";
import {DishService} from "./dish.service";
import {HttpErrorResponse} from "@angular/common/http";
import {FormArray, NgForm, Validators} from "@angular/forms";
import {Diet} from "./diet";
import {DietService} from "./diet.service";
import {FormControl} from "@angular/forms";
import {setupTestingRouter} from "@angular/router/testing";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  public dishesList!: Dish[];
  public dietsList!: Diet[];
  public selectedDiets:object[] =[];
  public deleteDish!: Dish;
  public updateDish!: Dish;

  constructor(private dishService: DishService, private dietService: DietService) {
  }


  ngOnInit() {
    this.getDishes();
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

  public onUpdateSelectedDiet(value: Diet) {
    if(this.selectedDiets.includes(value)) {
      this.selectedDiets.splice(this.selectedDiets.indexOf(value));
    } else {
      this.selectedDiets.push(value);
    }
    for (var diet of this.selectedDiets) {
      console.log(diet);
    }
  }

  public onAddDish(addForm: NgForm): void {
    addForm.controls['diets'].setValue(this.selectedDiets);
    const dishForm = document.getElementById('add-dish-form');
    if(dishForm != null) {
      dishForm.click();
    }
    this.dishService.addDish(addForm.value).subscribe(
      (response: Dish) => {
        console.log(response);
        addForm.reset();
        this.getDishes();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onDeleteDish(dishId:number): void {
    const dishForm = document.getElementById('delete-dish-form');
    if(dishForm != null) {
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

    public onUpdateDish(updateForm:NgForm):void {
      const dishForm=document.getElementById('update-dish-form');
      updateForm.controls['diets'].setValue(this.selectedDiets);
      updateForm.controls['id'].setValue(this.updateDish.id);

      if(dishForm!=null) {
        dishForm.click();
      }
      this.dishService.updateDish(updateForm.value).subscribe(
        (response:Dish) => {
          console.log(response);
          updateForm.reset();
          this.getDishes();
        },
        (error:HttpErrorResponse) => {
          alert(error.message);
        });
    }

  public onOpenModal(dish: Dish, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if(mode === 'add') {
      this.getDiets();
      this.selectedDiets=[];
      button.setAttribute('data-target', '#addDishModal');

    }
    if(mode === 'edit') {
      this.getDiets();
      this.updateDish = dish;
      console.log(dish);
      button.setAttribute('data-target','#editDishModal');
    }
    if(mode === 'delete') {
      this.deleteDish = dish;
      button.setAttribute('data-target','#deleteDishModal');
    }
    if(container != null) {
      container.appendChild(button);
    }
    button.click();
  }

  public resetForm(form: NgForm) {
    form.reset();
  }
}
