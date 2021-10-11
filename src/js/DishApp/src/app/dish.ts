import {Diet} from "./diet";
import {Category} from "./category";
import {Ingredient} from "./ingredient";

export interface Dish {
  id: number;
  name: string;
  avgTimeToMake: number;
  calories: number;
  rating: number;
  warm: boolean;
  description: string;
  longDescription: string;
  image: string;
  categories: Category[];
  diets: Diet[];
  ingredients: Ingredient[];
  amount: number[];
}
