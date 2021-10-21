import { Ingredient } from './ingredient';
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class IngredientService{

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getIngredients(): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>(`${this.apiServerUrl}/api/ingredient/all`);
  }

  public addIngredient(ingredient: Ingredient): Observable<Ingredient> {
    return this.http.post<Ingredient>(`${this.apiServerUrl}/api/ingredient/add`,ingredient);
  }

  public updateIngredient(ingredient: Ingredient): Observable<Ingredient> {
    return this.http.put<Ingredient>(`${this.apiServerUrl}/api/ingredient/update`,ingredient);
  }

  public deleteIngredient(ingredientId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/api/ingredient/delete/${ingredientId}`)
  }
}
