import { Component, OnInit } from '@angular/core';

import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-sprint-participation',
  templateUrl: './sprint-participation.component.html',
  styleUrls: ['./sprint-participation.component.css']
})
export class SprintParticipationComponent implements OnInit {

  allUsersUrl = 'http://localhost:8080/user/';
  participantsUrl = 'http://localhost:8080/sprint/'; //sid/url
  removeUrl = 'http://localhost:8080/sprint/participation/';

  sprintId:any;

  userList:any;
  participantList:any;
  roleList:any;

  form:any = {
    userId:null,
    role:null,
    
  }

  constructor(private route: ActivatedRoute,private authenticatedRequestService:AuthenticatedRequestService) { 
    this.sprintId = this.route.snapshot.params.sid;

  }

  ngOnInit(): void {
    this.getAllUsers();
    this.getAllParticipants();
    this.getRoles();
  }


  getAllUsers(){
    this.authenticatedRequestService.get(this.allUsersUrl)
    .subscribe(
      (users) =>{
        this.userList =users;
      }
    );

  }

  getAllParticipants(){
    this.authenticatedRequestService.get(this.participantsUrl + this.sprintId + '/participation').subscribe(
      (participants: any) =>{
        
        
        this.participantList = participants;
      },
      (error: any) => {
        console.log(error);
      }
    );

  }

  getRoles(){
    this.authenticatedRequestService.get(this.participantsUrl + 'roles').subscribe(
      (roles: any) =>{
        
        
        this.roleList = roles;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  buildParticipant( userId:any, role:any ){
    return {
      "sprintId" : this.sprintId,
      "userId" : userId,
      "role" : role
    };
  }

  onSubmit(){
    const { userId, role } = this.form;
    

    this.form.userId = "NONE";
    this.form.role =null;

    // buildRoleChangePatchObject
    this.authenticatedRequestService.post(this.buildParticipant(userId, role)
      , this.participantsUrl + this.sprintId +'/participation'
      ).subscribe(
      () =>{
        this.getAllParticipants();
        this.getAllUsers();
      },
      (error: any) => {
        console.log(error);
      }
    );


  }

  removePart(partId:any){
    this.authenticatedRequestService.delete(this.removeUrl+ partId).subscribe(
      () =>{
        this.getAllParticipants();
        this.getAllUsers();
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  rolePreetify(role:string){

    return role.substring(5).replace("_"," ");
  }


}
