import { Component, Input, OnInit } from '@angular/core';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import { RequestService } from 'src/app/services/request.service';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';

import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from "@angular/material/dialog";

import {BacklogEditDialogComponent} from "src/app/backlog/backlog-edit-dialog/backlog-edit-dialog.component";


@Component({
  selector: 'app-backlog-groom',
  templateUrl: './backlog-groom.component.html',
  styleUrls: ['./backlog-groom.component.css']
})
export class BacklogGroomComponent implements OnInit {

  
 @Input() backlogId:any;

  apiUrl:any = 'http://localhost:8080/backlog/';
  itemUrl:any = 'http://localhost:8080/item/';
  

  updated:boolean =false;

  constructor(private requestService:RequestService, private dialog: MatDialog,private authenticatedRequestService:AuthenticatedRequestService) { }
  

  // openDialog(): void {
  //   const dialogRef = this.dialog.open(BacklogEditDialogComponent, {
  //     width: '250px',
  //     data: {this.},
  //   });
  // }

  ngOnInit(): void {
    this.getBacklog();
  }

  getBacklog(){
    this.authenticatedRequestService.get(this.apiUrl+this.backlogId).subscribe(
      (backlog: any) =>{
        
        
        this.backlog10items = backlog;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }



  updateBacklog(){
    // set priorities to position on list
    this.indexToPriotity();
    this.authenticatedRequestService.put(this.backlog10items,this.apiUrl+this.backlogId).subscribe(
      response =>{ 
        this.showUpdate();
        this.getBacklog();
      }
    );
  }

  deleteBacklogItem(backlogItemId:any){
    this.authenticatedRequestService.delete(this.itemUrl+backlogItemId)
    .subscribe(
      response =>{ 
        this.showUpdate();
        this.getBacklog();
      }
    )
  }

  

  showUpdate(){
    this.updated=true;
    
  }
  
  hideUpdate(){
    this.updated=false;
  }

  // changes priority in all by index, to resend
  // indexToPriotity(data:any){
  //   for(let i =0; i<data.length; i++){
  //     data[i].priority = i+1;
  //   }
    
  // }

  indexToPriotity(){
    for(let i =0; i<this.backlog10items.length; i++){
      this.backlog10items[i].priority = i+1;
    }
    
  }
 



  
  selected:any ;
  projectId:any = null ;
  // backlogId = null ;
  swap:any;
  drop(event: CdkDragDrop<any[]>) {
    moveItemInArray(this.backlog10items, event.previousIndex, event.currentIndex);
    this.hideUpdate();
  }


  // fromModal:any;

  openDialog(backlogId:number): void {
    const dialogRef = this.dialog.open(BacklogEditDialogComponent, {
      width: '500px',
      data: {backlog: this.backlog10items[backlogId]}
      // data: {backlog: this.backlog10items[backlogId]}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // this.fromModal = result;
      this.updateBacklog();
    });
  }





  backlog10items:any;











/** old version with no auth */
  // getBacklog(){
  //   this.requestService.backlog(this.apiUrl+this.backlogId).subscribe(
  //     (backlog: any) =>{
        
        
  //       this.backlog10items = backlog;
  //     },
  //     (error: any) => {
  //       console.log(error);
  //     }
  //   );
  // }

// updateBacklog(){
  //   // set priorities to position on list
  //   this.indexToPriotity();
  //   this.requestService.update(1,this.backlog10items,this.apiUrl+this.backlogId).subscribe(
  //     response =>{ 
  //       this.showUpdate();
  //       this.getBacklog();
  //     }
  //   );
  // }

  // deleteBacklogItem(backlogItemId:any){
  //   this.requestService.delete(this.itemUrl+backlogItemId)
  //   .subscribe(
  //     response =>{ 
  //       this.showUpdate();
  //       this.getBacklog();
  //     }
  //   )
  // }

/** old data */

  //priority for grooming
  //feature/change/defect.techimprovemen/knowledgeaqusition types
  // backlog10items:any = [
  //   {
  //     "id":"1",
  //     "name":"Create dog searching algorithm ",
  //     "type":"feature",
  //     "description":"Match dogs to distance and prefered breed",
  //     "story":"As user, I want sth, so sth",
  //     "details":"",
  //     "release":"2.0",
  //     "priority":"1" 
  //   },
  //   {
  //     "id":"2",
  //     "name":"Change selection queries only to corgi",
  //     "type":"change",
  //     "description":"",
  //     "details":"",
  //     "story":"As user, I want sth, so sth",
  //     "release":"3.0",
  //     "priority":"2" 
  //   },
  //   {
  //     "id":"3",
  //     "name":"Design UI",
  //     "type":"feature",
  //     "description":"Match dogs to distance and prefered breed",
  //     "story":"As user, I want sth, so sth",
  //     "details":"",
  //     "release":"2.0",
  //     "priority":"3" 
  //   },
  //   {
  //     "id":"4",
  //     "name":"Create dog searching algorithm ",
  //     "type":"feature",
  //     "description":"Match dogs to distance and prefered breed",
  //     "details":"",
  //     "story":"As user, I want sth, so sth",
  //     "release":"2.0",
  //     "priority":"4" 
  //   },
  //   {
  //     "id":"5",
  //     "name":"Create dog searching algorithm ",
  //     "type":"feature",
  //     "description":"Match dogs to distance and prefered breed",
  //     "story":"As user, I want sth, so sth",
  //     "details":"",
  //     "release":"2.0",
  //     "priority":"5" 
  //   },
  //   {
  //     "id":"6",
  //     "name":"Create dog searching algorithm ",
  //     "type":"feature",
  //     "description":"Match dogs to distance and prefered breed",
  //     "details":"",
  //     "release":"2.0",
  //     "priority":"6" 
  //   },
  //   {
  //     "id":"7",
  //     "name":"Create dog searching algorithm ",
  //     "type":"feature",
  //     "description":"Match dogs to distance and prefered breed",
  //     "story":"As user, I want sth, so sth",
  //     "details":"",
  //     "release":"2.0",
  //     "priority":"7" 
  //   },
  //   {
  //     "id":"8",
  //     "name":"Create dog searching algorithm ",
  //     "type":"feature",
  //     "description":"Match dogs to distance and prefered breed",
  //     "story":"As user, I want sth, so sth",
  //     "details":"",
  //     "release":"2.0",
  //     "priority":"8" 
  //   }
  
  // ];

  
  
}



