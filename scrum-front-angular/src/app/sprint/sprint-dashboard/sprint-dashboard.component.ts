import { Component, Input, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-sprint-dashboard',
  templateUrl: './sprint-dashboard.component.html',
  styleUrls: ['./sprint-dashboard.component.css']
})
export class SprintDashboardComponent implements OnInit {

  sprintUrl:string ='http://localhost:8080/sprint/';




  sprintId:any;

  sprint:any;

  breadcrumpUrl:any;

  constructor(private route: ActivatedRoute, private authenticatedRequestService:AuthenticatedRequestService, private router: Router) { 
    this.sprintId =this.route.snapshot.params.sid;
    this.breadcrumpUrl = this.router.url;
    

  }

  ngOnInit(): void {
    this.getSprint();
    window.dispatchEvent(new Event('resize'));
    
  }




  getSprint(){
    this.authenticatedRequestService.get(this.sprintUrl + this.sprintId).subscribe(
      (sprintInfo: any) =>{
        
        
        this.sprint = sprintInfo;
        
      },
      (error: any) => {
        console.log(error);
      }
    );
  }


}
