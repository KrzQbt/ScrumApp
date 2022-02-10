import { Component, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-sprint-creation',
  templateUrl: './sprint-creation.component.html',
  styleUrls: ['./sprint-creation.component.css']
})
export class SprintCreationComponent implements OnInit {

  projectUrl:any = "http://localhost:8080/project/";

  projectId:any;
  

  constructor(private route: ActivatedRoute,private authenticatedRequestService:AuthenticatedRequestService) {
    this.projectId =this.route.snapshot.params.pid;
   }

  ngOnInit(): void {
    this. getProject();
    this.getBacklogList();
    this.getSprintList();
  }
  backlogs:any;
  sprints:any;
  project:any;


  form:any = {
    description:null,
    startTime:null,
    endTime:null,
    projectId:null,
    backlogId:null,
    sprintRole:null,
    copyFromSprintWithId:null

  }
  roles:any = [
    "ROLE_TEAM_LEADER",
    "ROLE_EDITOR_PROGRAMMER",
    "ROLE_PRODUCT_OWNER",
    "ROLE_PROJECT_MANAGER"
  ]

  onSubmit(){
    this.authenticatedRequestService.post(this.buildSprint(
      this.form.description,
      this.form.startTime,
      this.form.endTime,
      this.form.projectId,
      this.form.backlogId,
      this.form.sprintRole,
      this.form.copyFromSprintWithId
    ),this.projectUrl+this.projectId+'/sprint').subscribe(
      () =>{

      }
    );
  }
  buildSprint(
    description:any,
    startTime:any,
    endTime:any,
    projectId:any,
    backlogId:any,
    sprintRole:any,
    copyFromSprintWithId:any){
    return {
      "description":description,
      "startTime":startTime,
      "endTime":endTime,
      "projectId":projectId,
      "backlogId":backlogId,
      "copyFromSprintWithId":copyFromSprintWithId,
      "sprintRole":sprintRole
    };
  }

  getBacklogList(){
    this.authenticatedRequestService.get(this.projectUrl+ this.projectId + '/backlog').subscribe(
      (backlogList) => {
        this.backlogs =backlogList;
      }
    );
  }

  getSprintList(){
    this.authenticatedRequestService.get(this.projectUrl+this.projectId+'/sprint').subscribe(
      (sprintList) => {
        this.sprints = sprintList;
      }
    );
  }

  getProject(){
    this.authenticatedRequestService.get(this.projectUrl + this.projectId).subscribe(
      (proInfo) => {
        this.project = proInfo;
      }
    );
  }

}
