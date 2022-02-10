import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RequestService } from 'src/app/services/request.service';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';

@Component({
  selector: 'app-backlog-detail',
  templateUrl: './backlog-detail.component.html',
  styleUrls: ['./backlog-detail.component.css']
})
export class BacklogDetailComponent implements OnInit {



  // projectId: any;
  projectUrl:any = "http://localhost:8080/project/";
  backlogUrl:any = 'http://localhost:8080/backlog/info/';
  patchUrl:string= 'http://localhost:8080/backlog/'; // concat backlogId when passing as argument
  patchCurrentTrueObject:any = {"current":true}

  backlogId: any;
  selected: any = "overview";

  backlog:any;
  project:any;

  constructor(private requestService:RequestService,private route: ActivatedRoute, private authenticatedRequestService:AuthenticatedRequestService) {
    // this.projectId = this.route.snapshot.params.pid;
    this.backlogId = this.route.snapshot.params.bid;
   }

  ngOnInit(): void {
    
    this.getBacklogInfo();
  }


  setCurrent(){
    //request with wrapper

    
    this.authenticatedRequestService.patch(this.patchCurrentTrueObject,this.patchUrl +this.backlogId).subscribe(
        () =>{
          this.getBacklogInfo();
        },
        (error: any) => {
          console.log(error);
        }
    );
    
  }


  getBacklogInfo(){
    this.authenticatedRequestService.get(this.backlogUrl+this.backlogId).subscribe(
      (backlogInfo: any) =>{
        this.backlog = backlogInfo;
        this. getProject();
      },
      (error: any) => {
        console.log(error);
      }
    );

  }
  getProject(){
    this.authenticatedRequestService.get(this.projectUrl + this.backlog.projectId).subscribe(
      (proInfo) => {
        this.project = proInfo;
      }
    );
  }



}
