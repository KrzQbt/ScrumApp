import { Component, Input, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';

@Component({
  selector: 'app-project-sprint-listing',
  templateUrl: './project-sprint-listing.component.html',
  styleUrls: ['./project-sprint-listing.component.css']
})
export class ProjectSprintListingComponent implements OnInit {

  // url in form: "/project/{pid}/sprint"
  urlPrefix:string ="http://localhost:8080/project/";
  urlSuffix:string = "/sprint";

  @Input() projectId:any;

  sprintList:any;

  constructor(private authenticatedRequestService:AuthenticatedRequestService) { }

  ngOnInit(): void {
    this.getSprintList();
  }

  getSprintList(){
    this.authenticatedRequestService.get(this.urlPrefix+
      this.projectId+
      this.urlSuffix).subscribe(
      (sprints: any) =>{
        this.sprintList = sprints.reverse();
        
        
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

}
