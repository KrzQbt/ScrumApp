import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';



@Component({
  selector: 'app-sprint-time-preference-form',
  templateUrl: './sprint-time-preference-form.component.html',
  styleUrls: ['./sprint-time-preference-form.component.css']
})
export class SprintTimePreferenceFormComponent implements OnInit {

 

  ngOnInit(): void {
  }


  constructor(private datePipe : DatePipe)
  {


  }
  


}





