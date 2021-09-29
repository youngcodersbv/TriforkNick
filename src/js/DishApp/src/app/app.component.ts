import {Component, OnInit} from '@angular/core';
import {Dish} from "./dish";
import {DishService} from "./dish.service";
import {HttpErrorResponse} from "@angular/common/http";

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
}
