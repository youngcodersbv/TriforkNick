import { Diet } from './diet';
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DietService{

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getDiets(): Observable<Diet[]> {
    return this.http.get<Diet[]>(`${this.apiServerUrl}/diet/all`);
  }

  public addDiet(diet: Diet): Observable<Diet> {
    return this.http.post<Diet>(`${this.apiServerUrl}/diet/add`,diet);
  }

  public updateDiet(diet: Diet): Observable<Diet> {
    return this.http.put<Diet>(`${this.apiServerUrl}/diet/update`,diet);
  }

  public deleteDiet(dietId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/dish/delete/${dietId}`)
  }
}
