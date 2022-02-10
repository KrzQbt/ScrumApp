import { Component, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';

@Component({
  selector: 'app-project-creation',
  templateUrl: './project-creation.component.html',
  styleUrls: ['./project-creation.component.css']
})
export class ProjectCreationComponent implements OnInit {
  projectUrl:any = "http://localhost:8080/project";

  constructor(private authenticatedRequestService:AuthenticatedRequestService) { }

  ngOnInit(): void {
  }

  form:any = {
    projectName:null,
    myRole:null
  }

  roles:any = [
    "ROLE_TEAM_LEADER",
    "ROLE_EDITOR_PROGRAMMER",
    "ROLE_PRODUCT_OWNER",
    "ROLE_PROJECT_MANAGER"
  ]
  
  onSubmit(){
    // projectName;
    // String creatorRole

    const { projectName,myRole } = this.form;

    this.authenticatedRequestService.post(this.buildProject(projectName,myRole),this.projectUrl).subscribe(
      ()=>{

      }

    );
  }

  buildProject(name:any,role:any){
    return {
      "projectName":name,
      "creatorRole":role
    };
  }




}
