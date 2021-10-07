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
  public dietsList: Diet[] = [];
  public toUpdateDiets:Diet[] =[];
  public deleteDish!: Dish;
  public updateDish!: Dish;

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

  public onUpdateSelectedDiet(value: Diet) {
    if(value.selected) {
      value.selected=false;
    } else {
      value.selected=true;
    }
  }

  public updateInitSelectedDiets() {
    for(let i = 0; i < this.dietsList.length;++i) {
      for(let j = 0; j < this.updateDish.diets.length; ++j) {
        console.log(this.dietsList[i].id + " DietsList " + this.dietsList[i].type);
        console.log(this.updateDish.diets[j].id + " SelectedDiets" + this.updateDish.diets[j].type);
        if(this.dietsList[i].id == this.updateDish.diets[j].id) {
          console.log("EQUAL!");
          this.dietsList[i].selected=true;
          this.updateDish.diets[j].selected=true;
          this.updateDish.diets[j] = this.dietsList[i];
        }
      }
    }
  }

  public onAddDish(addForm: NgForm): void {
    addForm.controls['diets'].setValue(this.getSelectedDiets());
    const dishForm = document.getElementById('add-dish-form');
    if(dishForm != null) {
      dishForm.click();
    }
    this.dishService.addDish(addForm.value).subscribe(
      (response: Dish) => {
        console.log(response);
        this.wipeSelectedDiets();
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
    for(let i = 0; i < this.dietsList.length;++i) {
      if(this.dietsList[i].selected) {
        res.push(this.dietsList[i]);
      }
    }
    return res;
  }

  public wipeSelectedDiets() {
    for(let i = 0; i < this.dietsList.length; ++i) {
      this.dietsList[i].selected=false;
    }
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
      updateForm.controls['diets'].setValue(this.getSelectedDiets());

      if(dishForm!=null) {
        dishForm.click();
      }
      this.dishService.updateDish(updateForm.value).subscribe(
        (response:Dish) => {
          console.log(response);
          this.wipeSelectedDiets();
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
      button.setAttribute('data-target', '#addDishModal');
    }
    if(mode === 'edit') {

      this.updateDish = dish;


      this.updateInitSelectedDiets();

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
