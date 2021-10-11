import { Component, OnInit } from '@angular/core';
import {Ingredient} from "../ingredient";
import {IngredientService} from "../ingredient.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {CategoryService} from "../category.service";
import {Category} from "../category";

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {


  categoriesList!: Category[];
  currentCategory!: Category;

  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.getCategories();
  }

  public getCategories(): void {
    this.categoryService.getCategories().subscribe(
      (response: Category[]) => {
        this.categoriesList = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onAddCategory(addForm: NgForm): void {
    console.log(addForm.value);
    const categoryForm = document.getElementById("add-cat-form");
    if(categoryForm != null) {
      categoryForm.click();
    }
    this.categoryService.addCategory(addForm.value).subscribe(
      (response: Category) => {
        addForm.reset();
        this.getCategories();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onUpdateCategory(updateForm: NgForm): void {
    const categoryForm = document.getElementById('update-cat-form');
    console.log(updateForm.value);

    if (categoryForm != null) {
      categoryForm.click();
    }
    this.categoryService.updateCategory(updateForm.value).subscribe(
      (response: Category) => {
        console.log(response);
        this.getCategories();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onDeleteCategory(id: number): void {
    console.log(id);
    const categoryForm = document.getElementById("delete-cat-form");
    if(categoryForm != null) {
      categoryForm.click();
    }
    this.categoryService.deleteCategory(id).subscribe(
      (response: void) => {
        console.log("Deleted.")
        this.getCategories();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
  }

  public onOpenModal(category: Category, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      this.getCategories();
      button.setAttribute('data-target', '#addCatModal');
    }
    if (mode === 'edit') {
      this.currentCategory = category;

      console.log(this.categoriesList);

      button.setAttribute('data-target', '#editCatModal');
    }
    if (mode === 'delete') {
      this.currentCategory = category;
      button.setAttribute('data-target', '#deleteCatModal');
    }
    if (mode === 'view') {
      this.currentCategory = category;
      button.setAttribute('data-target', '#viewIngModal');
    }

    if (container != null) {
      container.appendChild(button);
    }
    button.click();
  }

}
