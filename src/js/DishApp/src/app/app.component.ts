import {Component, OnInit} from '@angular/core';
import {Dish} from "./dish";
import {DishService} from "./dish.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  public dishes!: Dish[];

  constructor(private dishService: DishService) {
  }

  ngOnInit() {
    this.getDishes();
  }

  public getDishes(): void {
    this.dishService.getDishes().subscribe(
      (response: Dish[]) => {
        this.dishes=response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onAddDish(addForm: NgForm): void {
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
