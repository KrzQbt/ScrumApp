import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RequestService } from 'src/app/services/request.service';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html',
  styleUrls: ['./project-detail.component.css']
})
export class ProjectDetailComponent implements OnInit {
  projectId: any;
  projectUrl:any = 'http://localhost:8080/project/';
  projectBasics:any;

  constructor(private route: ActivatedRoute, private requestService:RequestService, private authenticatedRequestService:AuthenticatedRequestService) {
    this.projectId = this.route.snapshot.params.pid;


  }
  ngOnInit(): void {
    this.getProjectInfo();
  }

  getProjectInfo(){
    this.authenticatedRequestService.get(this.projectUrl+this.projectId).subscribe(
      (project: any) =>{
        this.projectBasics = project;
        
        
      },
      (error: any) => {
        console.log(error);
      }
    );
  }




  }


