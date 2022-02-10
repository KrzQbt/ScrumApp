import { Component, OnInit } from '@angular/core';
import { ChartType, Row } from "angular-google-charts";

import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute } from '@angular/router';
import {ElementRef, HostListener, ViewChild} from '@angular/core';

@Component({
  selector: 'app-time-report',
  templateUrl: './time-report.component.html',
  styleUrls: ['./time-report.component.css']
})
export class TimeReportComponent implements OnInit {

  sprintUrl = 'http://localhost:8080/sprint/';

  sprintId:any;

  participantPreferences:any;

  chartPick:any = {
    id:null
  }
  picked:any;

  constructor(private route: ActivatedRoute,private authenticatedRequestService:AuthenticatedRequestService) { 
    this.sprintId = this.route.snapshot.params.sid; }

  ngOnInit(): void {
    this.getParticipantPreferences();
    
  }

  getParticipantPreferences(){
    this.authenticatedRequestService.get(this.sprintUrl +this.sprintId +'/report').subscribe(
      (pref) =>{
        this.participantPreferences = pref;
      }
    );
  }


  prepareUserData(){
    // prepares data based array  in participantPreferences array under picked index
    let pickIndex = this.chartPick.id;

    this.chartData.data =[];

    for (let i = 0; i < this.participantPreferences[pickIndex].dailyTimePreferences.length; i++){
      this.chartData.data.push([
        this.participantPreferences[pickIndex].dailyTimePreferences[i].date.substring(5), 
        this.participantPreferences[pickIndex].dailyTimePreferences[i].availableForHours
      ])
    }


  }

  prepareSumData(){
    let pickIndex = this.chartPick.id;

    this.chartData.data =[];
    // summary report
    

        const sumMap = new Map();

      for (let index = 0; index < this.participantPreferences.length; index++) {
        



        for (let j = 0; j < this.participantPreferences[index].dailyTimePreferences.length; j++) {
         
          // if date already exists - sum
          if(sumMap.has(this.participantPreferences[index].dailyTimePreferences[j].date)){
            sumMap.set(
              this.participantPreferences[index].dailyTimePreferences[j].date,
              
              sumMap.get(this.participantPreferences[index].dailyTimePreferences[j].date) +
              this.participantPreferences[index].dailyTimePreferences[j].availableForHours
              );

          }else{
            sumMap.set(
              this.participantPreferences[index].dailyTimePreferences[j].date,
              this.participantPreferences[index].dailyTimePreferences[j].availableForHours);

          }

      } //for j

      } //for index

      sumMap.forEach(
        (value, key) => {
          this.chartData.data.push([
            key.substring(5), 
            value
          ])
        }
      )


      
    

  }

  onSubmit(){

    if(this.chartPick.id == "sum"){
      this.prepareSumData()
      this.picked =this.chartPick.id;
    }
      
    else{
      this.prepareUserData();
    this.picked =this.chartPick.id; // na koncu po zmianie danych zmiana pickid do genereacji
    }

    // this.picked =this.chartPick.id;
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



}//end
