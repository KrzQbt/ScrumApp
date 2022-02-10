import { Component, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-project-manager',
  templateUrl: './project-manager.component.html',
  styleUrls: ['./project-manager.component.css']
})
export class ProjectManagerComponent implements OnInit {

  projectUrl:any = "http://localhost:8080/project/";

  constructor(private route: ActivatedRoute,private authenticatedRequestService:AuthenticatedRequestService) { }

  ngOnInit(): void {
    this.projectId = this.route.snapshot.params.pid;
    this.getProject();
  }
  projectId:any;
  project:any;

  getProject(){
    this.authenticatedRequestService.get(this.projectUrl+this.projectId).subscribe(
      (pro) => {
        this.project = pro;
      }
    );
  }

  onSubmit(){
    this.authenticatedRequestService.put(
      this.buildProject(this.project.name),
      this.projectUrl+ this.projectId
    ).subscribe(
      () => {
        this.getProject();
      }
    );
  }
  buildProject(name:string){
    return {
      "name":name
    };
  }


  deleteProject(){
    this.authenticatedRequestService.delete(this.projectUrl+this.projectId).subscribe(
      () => {}
    )
  }




}
