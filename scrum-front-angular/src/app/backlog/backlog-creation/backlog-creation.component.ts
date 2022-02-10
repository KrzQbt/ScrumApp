import { Component, OnInit } from '@angular/core';

import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-backlog-creation',
  templateUrl: './backlog-creation.component.html',
  styleUrls: ['./backlog-creation.component.css']
})
export class BacklogCreationComponent implements OnInit {

  projectUrl = 'http://localhost:8080/project/';
  

  constructor(private route: ActivatedRoute,private authenticatedRequestService:AuthenticatedRequestService) { 
    this.projectId = this.route.snapshot.params.pid;
  }

  ngOnInit(): void {
    this. getProject();
    this.getBacklogListing();
  }

  projectId:any;

  form:any = {
    description:null,
    copyItemsFromBacklogId:null
  }
  project:any;
  backlogList:any;

  getBacklogListing(){
      this.authenticatedRequestService.get(this.projectUrl+this.projectId+'/backlog').subscribe(
        (backlogs) => {
          this.backlogList = backlogs;
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

  onSubmit(){
    const {description, copyItemsFromBacklogId} = this.form;
    this.form.description = null;
    this.form.copyItemsFromBacklogId =null;

    this.authenticatedRequestService.post(this.buildBacklog(description,copyItemsFromBacklogId),this.projectUrl+this.projectId+'/backlog').subscribe(()=>{});
  }

  buildBacklog(description:string,backlogId:any){
    return {
      "description": description,
      "copyItemsFromBacklogId":backlogId
    };
  }



}
