import {AfterViewInit, Component, ElementRef, OnInit} from "@angular/core";
import {refresh} from '../../assets/snake/snake';

@Component({
  selector: 'app-ingredients',
  templateUrl: './lab.html',
  styleUrls: ['./snake.css'],
})
export class SnakeComponent implements OnInit, AfterViewInit {

  constructor(private elementRef:ElementRef) {
  };

  ngOnInit() {
    this.loadScript("../assets/snake/snake.js");
  }

  ngAfterViewInit() {
    refresh();
  }


 loadScript(url: string) {
  const body = <HTMLDivElement> document.body;
  const script = document.createElement('script');
  script.innerHTML = '';
  script.type='module';
  script.src = url;
  script.async = false;
  script.defer = true;
  body.appendChild(script);
}
}
