import { Component, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';

@Component({
  selector: 'app-sprint-list',
  templateUrl: './sprint-list.component.html',
  styleUrls: ['./sprint-list.component.css']
})
export class SprintListComponent implements OnInit {


  sprintUrl:any = 'http://localhost:8080/sprint/';

  constructor(private authenticatedRequestService:AuthenticatedRequestService) { }

  ngOnInit(): void {
    this.getMySprints();
  }
  sprints:any;
  displayedSprints:any;


  getMySprints(){
    this.authenticatedRequestService.get(this.sprintUrl).subscribe(
      (sprintList) =>{
        this.sprints = sprintList;
      }
    );
  }

}
