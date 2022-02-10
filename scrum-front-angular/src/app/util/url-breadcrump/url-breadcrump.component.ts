import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-url-breadcrump',
  templateUrl: './url-breadcrump.component.html',
  styleUrls: ['./url-breadcrump.component.css']
})
export class UrlBreadcrumpComponent implements OnInit {

  @Input() url:any;
  
  constructor() { }

  ngOnInit(): void {
  }

}
