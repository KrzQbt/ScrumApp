import { Component, Input, OnInit } from '@angular/core';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';

@Component({
  selector: 'app-backlog-item-creation',
  templateUrl: './backlog-item-creation.component.html',
  styleUrls: ['./backlog-item-creation.component.css']
})
export class BacklogItemCreationComponent implements OnInit {

  constructor(private authenticatedRequestService:AuthenticatedRequestService) { }
  
  projectUrl:any ='http://localhost:8080/project/';
  backlogUrl:any = 'http://localhost:8080/backlog/';

  ngOnInit(): void {
    this.getStories();
  }
  @Input() backlogId:any;
  @Input() projectId:any;
  form:any = {
    name:null,
    type:null,
    description:null,
    story:null,
    details:null,
    release:null
  }
  stories:any;


  pbiTypes = ["Feature", "Change","Fix", "Technical improvement","Knowledge aquisition"];

  onSubmit(){
    //change to overview after submiting
    const {name,type,description,story,details,release} = this.form;
    this.form.name= null; 
    this.form.type= null; this.form.description= null;
    this.form.story= null;this.form.details= null;
    this.form.release = null;

    this.authenticatedRequestService.post(this.buildItem(name,type,description,story,details,release),this.backlogUrl+this.backlogId+ '/item').subscribe(
      () => {
        this.getStories();
      }
    );

  }

  buildItem(name:any,type:any,description:any,story:any,details:any,release:any){
    return {
      "name":name,
      "type":type,
      "description":description,
      "story":story,
      "details":details,
      "release":release,
      "backlogId":this.backlogId
    };
  }


  getStories(){
    this.authenticatedRequestService.get(this.projectUrl +this.projectId+'/story').subscribe(
      (storyList) =>{
        this.stories = storyList;
      }
    );
  }

}
