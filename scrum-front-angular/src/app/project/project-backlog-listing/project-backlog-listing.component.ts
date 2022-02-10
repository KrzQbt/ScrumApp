import { Component, Input, OnInit } from '@angular/core';
import { RequestService } from 'src/app/services/request.service';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';


@Component({
  selector: 'app-project-backlog-listing',
  templateUrl: './project-backlog-listing.component.html',
  styleUrls: ['./project-backlog-listing.component.css']
})
export class ProjectBacklogListingComponent implements OnInit {

  backlogListUrlTemplate:any = 'http://localhost:8080/project/';
  backlogListUrl:any;
  finRoute:string = '/backlog';

  @Input() projectId:any;
  constructor(private requestService:RequestService, private authenticatedRequestService:AuthenticatedRequestService) { }


  // on init merge api url with project id and get backlog data
  ngOnInit(): void {
    this.backlogListUrl =this.backlogListUrlTemplate+this.projectId+'/backlog';
    this.getBacklogList();
  }

  backlogList:any;

  getBacklogList(){
    this.authenticatedRequestService.get(this.backlogListUrl).subscribe(
      (backlogs: any) =>{
        // backlogs.reverse();
        this.backlogList = backlogs.reverse();
        
        
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

/**
 * old non auth vearsion
 * 
 * 
 **/
  // getBacklogList(){
  //   this.requestService.get(this.backlogListUrl).subscribe(
  //     (backlogs: any) =>{
  //       this.backlogList = backlogs;
        
        
  //     },
  //     (error: any) => {
  //       console.log(error);
  //     }
  //   );
  // }



}
