import { Component, Input, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { NgIf } from '@angular/common';


@Component({
  selector: 'app-sprint-board-row',
  templateUrl: './sprint-board-row.component.html',
  styleUrls: ['./sprint-board-row.component.css']
})
export class SprintBoardRowComponent implements OnInit {

  taskUrl = 'http://localhost:8080/task/';
  backlogItemUrl = 'http://localhost:8080/picked/';

  @Input() taskInput:any;

  backlogItem:any;

  color:any;
  
  
  randCol(){
    let max =6;
    let rand = Math.floor(Math.random() * max) + 1;
    
    this.color ="rand"+rand;

    // if(rand == 1)
    //   this.color = "rand1";
    // if(rand==2)
    // this.color = "rand2";
    // if(rand==3)
    // this.color = "rand3";
    //  if(rand==4)
    //  this.color = "rand4";


    
      //if no number was matched ret bg-secondary
      // return "secondary"
  
    
  
  
  }

  constructor(private authenticatedRequestService:AuthenticatedRequestService) { }

  ngOnInit(): void {
    this.randCol();
    this.getBacklogItem();

    if(this.taskInput.status == 'FREE')
      this.free.push(this.taskInput);

    if(this.taskInput.status == 'ACCEPTED')
      this.taken.push(this.taskInput);
    
    if(this.taskInput.status == 'IN_PROGRESS')
      this.todo.push(this.taskInput);


    if(this.taskInput.status == 'DONE')
        this.done.push(this.taskInput);
  }

  free:any = [
    
  ];// change array to object and display fields

  taken:any = [

  ];

  todo:any = [
   
  ];

  done:any = [
    
  ];

  drop(event: CdkDragDrop<any>) {
    if (event.previousContainer === event.container) {

      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
      
    } else {
      
      transferArrayItem(event.previousContainer.data,
                        event.container.data,
                        event.previousIndex,
                        event.currentIndex);
             
      
    }
    this.dropStatusChange();
    this.updateTaskStatus();
    
  }

  getBacklogItem(){
    this.authenticatedRequestService.get(this.backlogItemUrl + this.taskInput.pickedItemId).subscribe(
      (item) =>{
        this.backlogItem = item;
      }
    );
    
  }
  // to be used after each move on board
  // figure out which list to fetch from
  updateTaskStatus(){

    let updated =this.findArrayWithTask();

    let patchObject ={
      "id": updated.id,
      "status": updated.status
    };

    // api consumes patch object with task id and status
    this.authenticatedRequestService.patch(patchObject,this.taskUrl + updated.id).subscribe(
      () =>{ 
        
      }
    );


  }

  // method to be used at each drop in row
  // drag and drop api does not allow to get name and item easily
  // this method is allowing to change status field in backlog element
  
  dropStatusChange(){
    if( this.free.length > 0 )
      this.free[0].status = 'FREE';
    
    if( this.taken.length > 0 )
      this.taken[0].status = 'ACCEPTED';
    
    if( this.todo.length > 0 )
      this.todo[0].status = 'IN_PROGRESS';
    
    if( this.done.length > 0 )
      this.done[0].status = 'DONE';

  }

  findArrayWithTask(){
    if( this.free.length > 0 )
      return this.free[0];
    
    if( this.taken.length > 0 )
    return this.taken[0];
    
    if( this.todo.length > 0 )
      return this.todo[0];
    
    if( this.done.length > 0 )
        return this.done[0];

  }

}
