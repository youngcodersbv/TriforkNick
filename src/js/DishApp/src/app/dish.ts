import {Diet} from "./diet";
import {Category} from "./category";

export interface Dish {
  id: number;
  name: string;
  avgTimeToMake: number;
  calories: number;
  rating: number;
  warm: boolean;
  description: string;
  longDescription: string;
  imgPath: string;
  categories: Category[];
  diets: Diet[];
}
