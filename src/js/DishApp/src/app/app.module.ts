import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { DishesComponent } from './dishes/dishes.component';
import { DietsComponent } from './diets/diets.component';
import { RouterModule} from "@angular/router";
import { IngredientsComponent } from './ingredients/ingredients.component';
import { CategoriesComponent } from './categories/categories.component';

@NgModule({
  declarations: [
    AppComponent,
    DishesComponent,
    DietsComponent,
    IngredientsComponent,
    CategoriesComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot([{path: 'dishes', component: DishesComponent},
                                {path: 'diets', component: DietsComponent},
                                {path: 'ingredients', component: IngredientsComponent},
                                {path: 'categories', component: CategoriesComponent}
    ]),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
