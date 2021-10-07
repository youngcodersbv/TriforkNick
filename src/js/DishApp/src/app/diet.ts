import {Dish} from "./dish";

export interface Diet{
  id:number;
  type:string;
  selected:boolean;
  dishes:Dish[];
}
