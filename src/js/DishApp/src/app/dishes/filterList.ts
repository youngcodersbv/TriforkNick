import {Category} from "../category";
import {Diet} from "../diet";

export interface FilterList {
  diets: Diet[];
  categories: Category[];
}
