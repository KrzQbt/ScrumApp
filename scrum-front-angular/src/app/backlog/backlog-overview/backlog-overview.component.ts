import { Component, Input, OnInit } from '@angular/core';
import { RequestService } from 'src/app/services/request.service';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';


@Component({
  selector: 'app-backlog-overview',
  templateUrl: './backlog-overview.component.html',
  styleUrls: ['./backlog-overview.component.css']
})
export class BacklogOverviewComponent implements OnInit {

  @Input() backlogId:any;
  backlogItems:any;

  constructor(private requestService:RequestService, private authenticatedRequestService:AuthenticatedRequestService) { }

  apiUrl:any ='http://localhost:8080/backlog/';

  ngOnInit(): void {
    this.getBacklog();
  }

  getBacklog(){
    this.authenticatedRequestService.get(this.apiUrl + this.backlogId).subscribe(
      (backlog: any) =>{
        
        
        this.backlogItems = backlog;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }
  

  /**OLD VERSION WITH NO AUTH */
  // getBacklog(){
  //   this.requestService.backlog(this.apiUrl + this.backlogId).subscribe(
  //     (backlog: any) =>{
        
        
  //       this.backlogItems = backlog;
  //     },
  //     (error: any) => {
  //       console.log(error);
  //     }
  //   );
  // }




}
