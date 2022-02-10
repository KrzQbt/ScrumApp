import { Component, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-sprint-board',
  templateUrl: './sprint-board.component.html',
  styleUrls: ['./sprint-board.component.css']
})
export class SprintBoardComponent implements OnInit {

  sprintUrl:string ='http://localhost:8080/sprint/';

  sprintId:any;
  tasks:any;
  
  constructor(private route: ActivatedRoute, private authenticatedRequestService:AuthenticatedRequestService) { 
    this.sprintId =this.route.snapshot.params.sid;}

  ngOnInit(): void {
    this.getTasks();
  }

  getTasks(){
    this.authenticatedRequestService.get(this.sprintUrl+ this.sprintId+'/task').subscribe(
      (taskList: any) =>{
        
        this.tasks = taskList;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }



}
