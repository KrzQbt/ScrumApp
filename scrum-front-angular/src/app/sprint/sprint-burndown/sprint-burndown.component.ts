import { Component, OnInit } from '@angular/core';
import { ChartType, Row } from "angular-google-charts";

import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute } from '@angular/router';
import {ElementRef, HostListener, ViewChild} from '@angular/core';

@Component({
  selector: 'app-sprint-burndown',
  templateUrl: './sprint-burndown.component.html',
  styleUrls: ['./sprint-burndown.component.css']
})
export class SprintBurndownComponent implements OnInit {

  /**
   * error can be in 198 line if, where future date burndown is not accepted
   * also it updates on next day
   */


  sprintUrl = 'http://localhost:8080/sprint/';

  constructor(private route: ActivatedRoute,private authenticatedRequestService:AuthenticatedRequestService) { 
    this.sprintId = this.route.snapshot.params.sid; }

  ngOnInit(): void {
    this.getSprint();
    
  }

  sprint:any;

  isSprintBeforeStart:boolean = false;
  isSprintOver:boolean = false;


  burndownData:any;

  sprintId:any;

  isReady:boolean =false;

  sprintStartDate:any;
  sprintEndDate:any;

  totalTeamHours:any;

  sumMap:any;
  testSum = [
    ["01-02", null, null]
  ];


  chartData = {

    type: ChartType.LineChart,

    data: [
      ["RELOAD SITE",  0, 0]
 ]
   ,
   columnNames : ["Day", "Remaining task hours", "Team remaining hours"],
   options : {   
      hAxis: {
         title: 'Month'
      },
      vAxis:{
         title: 'Hours',
         viewWindow: {
          min: 0
        }
      },
	//   pointSize:5,
     series: {
      //   color: '#fd49a0'
      0: { lineWidth: 2, pointSize:5 },
      1: { lineWidth: 3, lineDashStyle: [7, 3], color: '#ff4004' }          
   }
   },
   width: 900,

 height: 300

}
@HostListener('window:resize', ['$event'])
  onResize(event:any) {
    this.chartData.width = parseInt(window.document.getElementById("chart").offsetWidth.toString().replace("px","")); //set an appropriate  factor
    this.chartData.height = parseInt(
      window.document.getElementById("chart").offsetHeight.toString().replace("px","")
    );
    
  }

getSprint(){
  this.authenticatedRequestService.get(this.sprintUrl+this.sprintId).subscribe(
    (sprintInfo) =>{
      this.sprint = sprintInfo;
      this.getBurndownData();
    }
  );
}

getBurndownData(){
  this.authenticatedRequestService.get(this.sprintUrl + this.sprintId + '/burndown')
  .subscribe(
    (data) =>{
      this.burndownData = data;
      
      this.prepareSumData();
      this.prepareChartData();
      this.prepareTeamHoursData();
      // this.insertBacklogDataInChart();
      this.insertTaskData();
      
      this.isReady =true;
      


    }
  );
}

dataDownloadCheck(){
  if(this.chartData.data[0][0] =="RELOAD SITE")
    window.location.reload();
}

prepareChartData(){
  // this.chartData.data = [];

}

// this function changes team hours sum report data to remaining hours at day


prepareSumData(){
  const sumMap = new Map();
  for (let index = 0; index < this.burndownData.preferenceWrapperList.length; index++){

    for (let j = 0; j < this.burndownData.preferenceWrapperList[index].dailyTimePreferences.length; j++){
      
      if(sumMap.has(this.burndownData.preferenceWrapperList[index].dailyTimePreferences[j].date)){
        sumMap.set(
          this.burndownData.preferenceWrapperList[index].dailyTimePreferences[j].date,

          sumMap.get(this.burndownData.preferenceWrapperList[index].dailyTimePreferences[j].date) +
           this.burndownData.preferenceWrapperList[index].dailyTimePreferences[j].availableForHours
        
        );


      }else{
        sumMap.set(
          this.burndownData.preferenceWrapperList[index].dailyTimePreferences[j].date,
          this.burndownData.preferenceWrapperList[index].dailyTimePreferences[j].availableForHours
          );
      }



    }//for j


  }//for index

  

  this.testSum = [];
  this.totalTeamHours = 0;
  sumMap.forEach(
    (value, key) => {

      this.testSum.push([
        key.substring(5), 
        value
      ])
      
      this.totalTeamHours += value;
    }
  )
  
  this.sumMap = sumMap;
}
dateDummy:any;

prepareTeamHoursData(){
  let remainingTotal = this.totalTeamHours;

  let currentDate =new Date(this.burndownData.sprintStartDate);
  let endDate = new Date(this.burndownData.sprintEndDate);

  this.dateDummy =  currentDate;


  this.chartData.data[0][0] = currentDate.toISOString().substring(0,10);
  this.chartData.data[0][2] = remainingTotal;
  remainingTotal -= this.sumMap.get(currentDate.toISOString().substring(0,10));

  while(endDate > currentDate){
            
    
  currentDate.setDate(currentDate.getDate() + 1);
  
  this.chartData.data.push([ currentDate.toISOString().substring(0,10), null , remainingTotal])
  remainingTotal -= this.sumMap.get(currentDate.toISOString().substring(0,10));

  }
  // repeated for last day
  currentDate.setDate(currentDate.getDate() + 1);
  this.chartData.data.push([ currentDate.toISOString().substring(0,10), null , remainingTotal])
  remainingTotal -= this.sumMap.get(currentDate.toISOString().substring(0,10));
  // this.chartData.data.splice(0,1);
  this.dateDummy = currentDate.toISOString().substring(0,10); // ten substring to klucz do mapy
  

}



insertTaskData(){
  const taskFinishMap = new Map();
  let remainingItemTime = 0;

  // case when today date is past sprint end date
  if(
    // true
    new Date(this.sprint.startTime) > new Date(Date.now())
    // ||
    // new Date(this.sprint.endTime) < new Date(Date.now())
    

  ){
    for (let i = 0; i < this.burndownData.taskList.length; i++){
      remainingItemTime += this.burndownData.taskList[i].expectedHoursToFinish;
  
    }

    this.chartData.data[0][1] =remainingItemTime;

    

  }
  
  else{// case when today date is in sprint boundaries or its prior to sprint
  for (let i = 0; i < this.burndownData.taskList.length; i++){
    remainingItemTime += this.burndownData.taskList[i].expectedHoursToFinish;

    if(!taskFinishMap.has(this.burndownData.taskList[i].finishedAt)){
      taskFinishMap.set(this.burndownData.taskList[i].finishedAt,[]);
    }
    
    let arrFinished = taskFinishMap.get(this.burndownData.taskList[i].finishedAt);
    arrFinished.push(this.burndownData.taskList[i].expectedHoursToFinish);
    taskFinishMap.set(this.burndownData.taskList[i].finishedAt,arrFinished);
  }

  for(let j = 0; j < this.chartData.data.length; j++){
    
    if(taskFinishMap.has(this.chartData.data[j][0])){

      taskFinishMap.get(this.chartData.data[j][0]).forEach(
        (num:number) => {
          remainingItemTime -= num;
        }
      );

      // remainingItemTime -= taskFinishMap.get(this.chartData.data[j][0]);
    
    
    
    }

    if(new Date(this.chartData.data[j][0] )  < new Date(Date.now())){
      this.chartData.data[j][1] =remainingItemTime;
    }

  }
}



}

//to be called after team hours insert data
insertBacklogDataInChart(){
  const taskFinishMap = new Map();
  // total hrs of
  let remainingItemTime = 0;
  for (let i = 0; i < this.burndownData.taskList.length; i++) {
    remainingItemTime += this.burndownData.taskList[i].expectedHoursToFinish;
    taskFinishMap.set(this.burndownData.taskList[i].finishedAt,this.burndownData.taskList[i].expectedHoursToFinish);
  }

  
  
  
  for (let j = 0; j < this.chartData.data.length; j++) {
    
    if(taskFinishMap.has(this.chartData.data[j][0]))
      remainingItemTime -= taskFinishMap.get(this.chartData.data[j][0]);
    // remaining task times are null, if date is not future, then remaining will be uploaded
    if(new Date(this.chartData.data[j][0] )  < new Date(Date.now())){
      this.chartData.data[j][1] =remainingItemTime;
    }

    //if task was finished at date, we can subtract time
    
  
  
  }






}
  

}
