import { Component, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-sprint-manager',
  templateUrl: './sprint-manager.component.html',
  styleUrls: ['./sprint-manager.component.css']
})
export class SprintManagerComponent implements OnInit {

  sprintUrl:any = "http://localhost:8080/sprint/";
  projectUrl:any = "http://localhost:8080/project/"; // do backlogow

  constructor(private route: ActivatedRoute,private authenticatedRequestService:AuthenticatedRequestService) {
    
  }

  ngOnInit(): void {
    this.sprintId =this.route.snapshot.params.sid;
    this.getSprint();
  }

  sprintId:any;

  sprint:any;
  backlogs:any;
  projectId:any;

  form:any = {
    description:null,
    startTime:null,
    endTime:null,
    backlogId:null,

  }

  getSprint(){
    this.authenticatedRequestService.get(this.sprintUrl+this.sprintId).subscribe(
      (sprintInfo) =>{
        this.sprint = sprintInfo;
        this.projectId = this.sprint.projectId;
        this.getBacklogs();
      }
    );
  }

  getBacklogs(){
    this.authenticatedRequestService.get(this.projectUrl+  this.projectId + '/backlog').subscribe(
      (backlogList)=>{
        this.backlogs = backlogList;
      }
    );
  }//sprint has project id

  onSubmit(){
    this.authenticatedRequestService.put(
      this.buildSprint(
        this.sprint.description,
        this.sprint.startTime,
        this.sprint.endTime,
        this.sprint.backlogId,
        ),
      this.sprintUrl+this.sprintId).subscribe(()=>{
        window.location.reload();
      });
  }

  // form:any = {
  //   description:null,
  //   startTime:null,
  //   endTime:null,
  //   backlogId:null,

  // }

  buildSprint( 
    description:any,
    startTime:any,
    endTime:any,
    backlogId:any){
    return {
      "description":description,
      "startTime":startTime,
      "endTime":endTime,
      "backlogId":backlogId
    };
  }

  setStatus(status:string){
      this.authenticatedRequestService.patch(this.buildStatusPatch(status),this.sprintUrl+ this.sprintId).subscribe(
        () =>{
          this.getSprint();
        }
      );
  }
  str:any;

  buildStatusPatch(status:string){
    
    if(status == "current")
      return { 
                "current":true,
                "finished":false,
                "inPlaning":false
              };

    if(status == "finished")
      return { 
        "current":false,
        "finished":true,
        "inPlaning":false
      };
    
    if(status == "inPlaning")
      return { 
        "current":false,
        "finished":false,
        "inPlaning":true
      };

    return {};

  }
  



  deleteSprint(){
    this.authenticatedRequestService.delete(this.sprintUrl+this.sprintId).subscribe(
      () =>{}
    )
  }

}
