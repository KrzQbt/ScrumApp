import { Component, Input, OnInit } from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { RequestService } from 'src/app/services/request.service';

@Component({
  selector: 'app-board-row',
  templateUrl: './board-row.component.html',
  styleUrls: ['./board-row.component.css']
})
export class BoardRowComponent implements OnInit {

  singleTaskUrl:any = 'http://localhost:8080/board/';

  @Input() taskInput:any;

  constructor(private requestService:RequestService) { }

  ngOnInit(): void {
    if(this.taskInput.status == 'free')
      this.free.push(this.taskInput);

    if(this.taskInput.status == 'taken')
      this.taken.push(this.taskInput);
    
    if(this.taskInput.status == 'todo')
      this.todo.push(this.taskInput);


    if(this.taskInput.status == 'done')
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

  // to be used after each move on board
  // figure out which list to fetch from
  updateTaskStatus(){
    this.requestService.update(1,this.findArrayWithTask(),this.singleTaskUrl+'1').subscribe(
      response =>{ 
        
      }
    );
  }

  // method to be used at each drop in row
  // drag and drop api does not allow to get name and item easily
  // this method is allowing to change status field in backlog element
  
  dropStatusChange(){
    if( this.free.length > 0 )
      this.free[0].status = 'free';
    
    if( this.taken.length > 0 )
      this.taken[0].status = 'taken';
    
    if( this.todo.length > 0 )
      this.todo[0].status = 'todo';
    
    if( this.done.length > 0 )
      this.done[0].status = 'done';

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
