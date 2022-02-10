import { Component, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';

@Component({
  selector: 'app-my-sprint-listing',
  templateUrl: './my-sprint-listing.component.html',
  styleUrls: ['./my-sprint-listing.component.css']
})
export class MySprintListingComponent implements OnInit {

  sprintUrl:any = 'http://localhost:8080/sprint/';

  constructor( private authenticatedRequestService:AuthenticatedRequestService) { }

  ngOnInit(): void {
    this.getMySprints();
  }

  sprints:any;

  getMySprints(){
    this.authenticatedRequestService.get(this.sprintUrl).subscribe(
      (sprintList) =>{
        this.sprints = sprintList;
      }
    );
  }

}
