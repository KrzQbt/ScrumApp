import { Component, OnInit } from '@angular/core';
import { ChartType, Row } from "angular-google-charts";

import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute } from '@angular/router';

import {ElementRef, HostListener, ViewChild} from '@angular/core';


@Component({
  selector: 'app-time-submit',
  templateUrl: './time-submit.component.html',
  styleUrls: ['./time-submit.component.css']
})
export class TimeSubmitComponent implements OnInit {

  sprintUrl = 'http://localhost:8080/sprint/';
  deleteUrl = 'http://localhost:8080/daily/';


  form:any = {
    day:null,
    hours:null
  }

  delForm:any ={
    dailyId:null
  }

  sprintInfo:any;

  preference:any;

  sprintId:any;

  dateInBounds:boolean =true;

  negativeHoursValue:boolean = false;
  tooMuchHours:boolean =false;


  isReady:boolean =false;

  constructor(private route: ActivatedRoute,private authenticatedRequestService:AuthenticatedRequestService) { 
    this.sprintId = this.route.snapshot.params.sid;

  }

  ngOnInit(): void {
    this.getPreference();
    this.getSprintInfo();
  }

  getSprintInfo(){
    this.authenticatedRequestService.get(this.sprintUrl + this.sprintId).subscribe(
      (sprint) => {
        this.sprintInfo = sprint;
      }
    )
  }

  onSubmit(){
    const { day, hours } = this.form;
    this.form.hours =null;
    if(
      new Date(day) > new Date(this.sprintInfo.endTime) ||
      new Date(day) < new Date(this.sprintInfo.startTime)
    ){
      this.dateInBounds =false;
      return;
    }
    if(hours <0){
      this.negativeHoursValue = true;
      return;
    }
    if(hours > 24){
      this.tooMuchHours =true;
      return;
    }
    
    this.dateInBounds =true;
    this.negativeHoursValue =false;
    this.tooMuchHours =false;


    this.authenticatedRequestService.post(this.buildDailyPreference(day,hours),
      this.sprintUrl + this.sprintId + '/time'
    ).subscribe(
      () => {
        this.reloadCurrentPage();
        
      }
    )




  }

  onDelSubmit(){
      // const of daily id not dat, change values
      const {id} = this.delForm.dailyId;
      this.authenticatedRequestService.delete(this.deleteUrl + this.delForm.dailyId).subscribe(
        () =>{
          this.reloadCurrentPage();
        }
      );
  }



  reloadCurrentPage() {
    window.location.reload();
   }

  buildDailyPreference(day:any, hours:any){
    return {
      "date":day,
      "availableForHours":hours
    };
  }

  getPreference(){
    this.authenticatedRequestService.get(this.sprintUrl + this.sprintId + '/time').subscribe(
      (pref)=>{
        this.preference = pref;
        // at reponse copy to chart data
        this.setNewChartData(pref);

      }

    )
  }


  setNewChartData(prefList:any){
    this.chartData.data.pop();
    for (let i = 0; i < this.preference.dailyTimePreferences.length; i++) {
      
      this.chartData.data.push([
        this.preference.dailyTimePreferences[i].date.substring(5), 
        this.preference.dailyTimePreferences[i].availableForHours
      ])
    }

    this.isReady =true;

  }
 
  
  
  @HostListener('window:resize', ['$event'])
  onResize(event:any) {
    this.chartData.width = parseInt(window.document.getElementById("chart").offsetWidth.toString().replace("px","")); //set an appropriate  factor
    this.chartData.height = parseInt(
      window.document.getElementById("chart").offsetHeight.toString().replace("px","")
    );
    
  }



  chartData = {

    type: ChartType.ColumnChart,

    data: [

    
      
    

    ["REFRESH",  0]

 ],

 columnNames: ["day", "hours"],

 options: {

 hAxis: {

       title: 'day'

    },

    vAxis:{

       title: 'hours'

    },

 },
 

 width: 1000,

 height: 400

};
  

}
