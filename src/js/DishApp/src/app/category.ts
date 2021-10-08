import {Dish} from "./dish";

export interface Category{
  id:number;
  type:string;
  selected:boolean;
  dishes:Dish[];
}
