import { Component, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-project-participation',
  templateUrl: './project-participation.component.html',
  styleUrls: ['./project-participation.component.css']
})
export class ProjectParticipationComponent implements OnInit {

  allUsersUrl = 'http://localhost:8080/user/';
  participantsUrl = 'http://localhost:8080/project/';
  
  removeUrl = 'http://localhost:8080/participation/';

  projectId:any;
  project:any;

  userList:any;
  participantList:any;
  roleList:any;
  
  form:any = {
    userId:null,
    role:null,
    
  }
  formOut:any;
  constructor(private route: ActivatedRoute,private authenticatedRequestService:AuthenticatedRequestService) {
    this.projectId =this.route.snapshot.params.pid;
   }

  ngOnInit(): void {
    this.getProject();
    this.getAllParticipants();
    this.getAllUsers();
    this.getRoles();
  }

  onSubmit(){
    const { userId, role } = this.form;
    this.formOut = this.form;

    this.form.userId = "NONE";
    this.form.role =null;

    // buildRoleChangePatchObject
    this.authenticatedRequestService.post(this.buildParticipant(userId, role)
      , this.participantsUrl + this.projectId +'/participation'
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

  buildParticipant( userId:any, role:any ){
    return {
      "projectId" : this.projectId,
      "userId" : userId,
      "role" : role
    };
  }

  getProject(){
    this.authenticatedRequestService.get(this.participantsUrl + this.projectId).subscribe(
      (proInfo) => {
        this.project = proInfo;
      }
    );
  }

  getAllUsers(){
    this.authenticatedRequestService.get(this.allUsersUrl).subscribe(
      (users: any) =>{
        
        
        this.userList = users;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  getAllParticipants(){
    this.authenticatedRequestService.get(this.participantsUrl + this.projectId + '/participation').subscribe(
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
