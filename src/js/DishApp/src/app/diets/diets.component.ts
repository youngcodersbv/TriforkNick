import { Component, OnInit } from '@angular/core';
import {DietService} from "../diet.service";
import {Diet} from "../diet";
import {HttpErrorResponse} from "@angular/common/http";
import {Dish} from "../dish";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-diets',
  templateUrl: './diets.component.html',
  styleUrls: ['./diets.component.css']
})
export class DietsComponent implements OnInit {

  dietsList!: Diet[];
  currentDiet!: Diet;

  constructor(private dietService: DietService) { }

  ngOnInit(): void {
    this.getDiets();
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

  public onAddDiet(addForm: NgForm): void {
    console.log(addForm.value);
    const dietForm = document.getElementById("add-diet-form");
    if(dietForm != null) {
      dietForm.click();
    }
    this.dietService.addDiet(addForm.value).subscribe(
      (response: Diet) => {
        addForm.reset();
        this.getDiets();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
    });
  }

  public onUpdateDiet(updateForm: NgForm): void {
    const dishForm = document.getElementById('update-diet-form');
    console.log(updateForm.value);

    if (dishForm != null) {
      dishForm.click();
    }
    this.dietService.updateDiet(updateForm.value).subscribe(
      (response: Diet) => {
        console.log(response);
        this.getDiets();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onDeleteDiet(id: number): void {
    console.log(id);
    const dietForm = document.getElementById("delete-diet-form");
    if(dietForm != null) {
      dietForm.click();
    }
    this.dietService.deleteDiet(id).subscribe(
      (response: void) => {
        console.log("Deleted.")
        this.getDiets();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onOpenModal(diet: Diet, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      this.getDiets();
      button.setAttribute('data-target', '#addDietModal');
    }
    if (mode === 'edit') {
      this.currentDiet = diet;

      console.log(this.dietsList);

      button.setAttribute('data-target', '#editDietModal');
    }
    if (mode === 'delete') {
      this.currentDiet = diet;
      button.setAttribute('data-target', '#deleteDietModal');
    }
    if (mode === 'view') {
      this.currentDiet = diet;
      button.setAttribute('data-target', '#viewDietModal');
    }

    if (container != null) {
      container.appendChild(button);
    }
    button.click();
  }

}
