import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';


@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  userUrl = 'http://localhost:8080/user/';

  form: any = {
    
    role: null
    
  };

  possibleRoles = [
    "ROLE_USER",
    "ROLE_MODERATOR",
    "ROLE_ADMIN",
    "ROLE_PROGRAMMER",
    "ROLE_TEAM_LEADER",
    
    "ROLE_PRODUCT_OWNER",
    "ROLE_SCRUM_MASTER",
    "ROLE_PROJECT_MANAGER"

  ];

  userId:any;
  userData:any;

  edit:boolean =false;

  constructor(private authenticatedRequestService:AuthenticatedRequestService,private route: ActivatedRoute) {
    this.userId = this.route.snapshot.params.uid;
   }

  ngOnInit(): void {
    this.getUserDetails();
  }



  getUserDetails(){
    this.authenticatedRequestService.get(this.userUrl + this.userId).subscribe(
      data => {
        this.userData = data;
      }
    );
  }//det end


  editChange(){
    this.edit = !this.edit;    
  }


}
