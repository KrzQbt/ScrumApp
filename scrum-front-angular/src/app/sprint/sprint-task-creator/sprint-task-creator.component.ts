import { Component, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-sprint-task-creator',
  templateUrl: './sprint-task-creator.component.html',
  styleUrls: ['./sprint-task-creator.component.css']
})
export class SprintTaskCreatorComponent implements OnInit {
  sprintUrl:any = "http://localhost:8080/sprint/";
  taskUrl:any = "http://localhost:8080/task/";


  sprintId:any;
  pickedItems:any;
  tasks:any;


  form:any = {
    itemId:null,
    name:null,
    description:null,
    hours:null

  }

  showItemById(){
    return this.retPicked().find(
      (item:any) => item.id == this.form.itemId
    );
  }

  retPicked(){
    return this.pickedItems;
  }
  getSting() {
    return "d";
  }

  statusPreetify(status:string){

    return status.replace("_"," ");
  }


  displayName:any;


  // Long id;
  // String name;
  // String description;
  // @Enumerated(EnumType.STRING)
  // ETaskStatus status;

  // Long sprintId;
  // Long pickedItemId;
  // Long sprintParticipantId; // owner

  constructor(private route: ActivatedRoute,private authenticatedRequestService:AuthenticatedRequestService) { 
    this.sprintId =this.route.snapshot.params.sid;
  }

  ngOnInit(): void {
    this.getPicked();
    this.getTaskList();
  }

  onSubmit() {
    const { name, description,  itemId, hours } = this.form;

    
    // claearing form
    this.form.name =null;
    this.form.description =null;
    this.form.itemId = null;
    this.form.hours = null;

    this.authenticatedRequestService.post(this.buildTask(
      name,description,itemId,this.sprintId, hours
    ), this.sprintUrl+ this.sprintId + '/task').subscribe(
      () =>{
        this.getTaskList();
      },
      (error: any) => {
        console.log(error);
      }
    );
    
  }

  getTaskList(){
    this.authenticatedRequestService.get(this.sprintUrl+ this.sprintId+'/task').subscribe(taskList =>{ 
      this.tasks = taskList;
    },
      (error: any) => {
        console.log(error);
      });
  }
  

  buildTask(name:string, description:string, itemId:any ,sprintId:any, hours:any){
    return {
      "name": name,
      "description": description,
      "pickedItemId":itemId,
      "sprintId" : sprintId,
      "expectedHoursToFinish": hours
    };
  }

  getPicked(){
    this.authenticatedRequestService.get(this.sprintUrl +this.sprintId +'/picked').subscribe(
      (picked) => {
        this.pickedItems = picked;
      }
    )
  }

  deleteTask(taskId:any){
    this.authenticatedRequestService.delete(this.taskUrl+ taskId).subscribe(
      () =>{
        this.getTaskList();
      },
      (error: any) => {
        console.log(error);
      }
    );
  }



}
