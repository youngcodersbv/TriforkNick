import {Diet} from "./diet";

export interface Dish {
  id: number;
  name: string;
  avgTimeToMake: number;
  calories: number;
  rating: number;
  warm: boolean;
  type: any;
  description: string;
  longDescription: string;
  imgPath: string;
  diets: object[];
}
