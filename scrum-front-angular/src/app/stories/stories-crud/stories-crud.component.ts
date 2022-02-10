import { Component, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-stories-crud',
  templateUrl: './stories-crud.component.html',
  styleUrls: ['./stories-crud.component.css']
})
export class StoriesCrudComponent implements OnInit {

  projectUrl:any = "http://localhost:8080/project/";
  storyUrl:any = "http://localhost:8080/story/";

  constructor(private route: ActivatedRoute,private authenticatedRequestService:AuthenticatedRequestService) { 
    this.projectId =this.route.snapshot.params.pid;
  }

  ngOnInit(): void {
    this. getProject();
    this.getStories();
  }
  projectId:any;
  project:any;
  activity:any = "manage";

  storyList:any;

  createForm:any ={
    story:null
    
  }

  delForm:any ={
    storyId:null
  }
  getStories(){
    this.authenticatedRequestService.get(this.projectUrl+ this.projectId + '/story').subscribe(
      (stories) =>{
        this.storyList = stories;
      }
    )
  }
  getProject(){
    this.authenticatedRequestService.get(this.projectUrl + this.projectId).subscribe(
      (proInfo) => {
        this.project = proInfo;
      }
    );
  }

  onSubmit(){
    const { story } = this.createForm;
    this.createForm.story =null;
    // this.createForm.projectId =null;

    this.authenticatedRequestService.post(this.buildStory(story,this.projectId), this.projectUrl + this.projectId + '/story' ).subscribe(
      () => {
          this.getStories();
      }
    );

  }

  onDelSubmit(){
    const { storyId } = this.delForm;
    this.delForm.storyId = null;

    this.authenticatedRequestService.delete(this.storyUrl + storyId).subscribe(
      () => {
        this.getStories();
      }
    );

  }

  buildStory(story:string, projectId:string){
    return {
      "story": story,
      "projectId": this.projectId
    };
  }

}
