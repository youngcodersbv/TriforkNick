import {AfterViewInit, Component, ElementRef, OnInit} from "@angular/core";
import {refresh} from '../../assets/snake/snake.js';

@Component({
  selector: 'app-ingredients',
  templateUrl: './lab.html',
  styleUrls: ['./snake.css'],
})
export class SnakeComponent implements OnInit, AfterViewInit {

  constructor(private elementRef:ElementRef) {
  };

  ngOnInit() {
  }

  ngAfterViewInit() {

    refresh();
  }


}
