import { Component, OnInit } from '@angular/core';
import {CdkDragDrop, copyArrayItem, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';

import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-sprint-item-pick',
  templateUrl: './sprint-item-pick.component.html',
  styleUrls: ['./sprint-item-pick.component.css']
})
export class SprintItemPickComponent implements OnInit {

  sprintId:any;
  sprint:any;
  backlogId:any;

  sprintUrl:any = "http://localhost:8080/sprint/";
  backlogUrl:any = "http://localhost:8080/backlog/";
  pickUrl:any;


  backlog:any = [];
  picked:any =[];

  

  constructor(private route: ActivatedRoute, private authenticatedRequestService:AuthenticatedRequestService) {
    this.sprintId =this.route.snapshot.params.sid;



   }

  ngOnInit(): void {
    this.getSprint();
    this.backlogId = this.sprint.backlogId;

    this.setUp();


  }

  getBacklog(){
    // 
    this.authenticatedRequestService.get(this.backlogUrl + this.sprint.backlogId).subscribe(
      (backlogItems: any) =>{
        this.backlog = backlogItems;
        
        
      },
      (error: any) => {
        console.log(error);
      }
    );
  }


  setUp(){
    this.authenticatedRequestService.get(this.sprintUrl + this.sprintId).subscribe(
      (sprintInfo: any) =>{
        this.sprint = sprintInfo;
        this.getBacklog();
        
      },
      (error: any) => {
        console.log(error);
      }
    );
  
  
  }  





getSprint(){
  this.authenticatedRequestService.get(this.sprintUrl + this.sprintId).subscribe(
      (sprintInfo: any) =>{
        this.sprint = sprintInfo;
        this.getBacklog();
        // i resign from already picked
        // this.getAlreadyPicked();
      },
      (error: any) => {
        console.log(error);
      }
    );
}



getAlreadyPicked(){
  this.authenticatedRequestService.get(this.sprintUrl + this.sprintId +'/picked').subscribe(
    (pickedList: any) =>{
      this.picked = pickedList;
      
      
    },
    (error: any) => {
      console.log(error);
    }
  );
}

// 
savePicks(){
  this.authenticatedRequestService.put(this.picked,this.sprintUrl + this.sprintId +'/picked').subscribe(
    () =>{
      
      
      
    },
    (error: any) => {
      console.log(error);
    }
  );
}

mapToPicked(){}


remove( item: any){
  this.picked.indexOf(item);  

  this.picked.splice(this.picked.indexOf(item),1   );
  
  
}


/**
 * cdk for drag and drop,
 * items are copied to picked and picked do not dissapear
 * picked are stopped from being duplicated
 */
drop(event: CdkDragDrop<any>) {
  if (event.previousContainer === event.container) {
    
    moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
  } else {
    
    if(!event.container.data.includes(event.previousContainer.data[event.previousIndex])){
     
          copyArrayItem(event.previousContainer.data,
            event.container.data,
            event.previousIndex,
            event.currentIndex);
    }
     
  }
}



}