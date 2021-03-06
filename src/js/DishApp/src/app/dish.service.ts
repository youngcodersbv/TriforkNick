import { Dish } from './dish';
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DishService{

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getDishes(): Observable<Dish[]> {
    return this.http.get<Dish[]>(`${this.apiServerUrl}/api/dish/all`);
  }

  public getDishesFiltered(cats: string[], diets: string[]) {
    let catParams: string ="";
    let dietParams: string ="";

    for(let cat of cats) {
      catParams += "category="+cat+"&";
    }
    for(let d of diets) {
      dietParams += "diet="+d+"&";
    }
    let url = `${this.apiServerUrl}/api/dish/all?${catParams}${dietParams}`;
    url = url.slice(0,url.length-1);
    console.log(url);
    return this.http.get<Dish[]>(url);
  }

  public addDish(dish: Dish): Observable<Dish> {
    return this.http.post<Dish>(`${this.apiServerUrl}/api/dish/add`,dish);
  }

  public addDishDto(dish: Dish): Observable<Dish> {
    return this.http.post<Dish>(`${this.apiServerUrl}/api/dish/adddto`,dish);
  }

  public updateDishDto(dish: Dish): Observable<Dish> {
    return this.http.put<Dish>(`${this.apiServerUrl}/api/dish/updatedto`,dish);
  }

  public updateDish(dish: Dish): Observable<Dish> {
    return this.http.put<Dish>(`${this.apiServerUrl}/api/dish/update`,dish);
  }

  public deleteDish(dishId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/api/dish/delete/${dishId}`)
  }
}
