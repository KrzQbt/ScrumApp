import { Component, OnInit } from '@angular/core';
import { RequestService } from 'src/app/services/request.service';
import { AuthenticatedRequestService } from 'src/app/services/authenticated-request/authenticated-request.service';
import { TokenStorageService } from '../../services/token/token-storage.service';


@Component({
  selector: 'app-project-listing',
  templateUrl: './project-listing.component.html',
  styleUrls: ['./project-listing.component.css']
})
export class ProjectListingComponent implements OnInit {

  projectUrl:any = 'http://localhost:8080/project/';


  constructor(
    private requestService:RequestService,
    private authenticatedRequestService:AuthenticatedRequestService,
    private tokenStorage: TokenStorageService
    ) { }

  ngOnInit(): void {
    this.getAllProjects();
    // this.getUser();
  }

  projects:any;
  displayedProjects:any;

  page:any = 1;

  totalPages:any;




  getAllProjects(){


    this.authenticatedRequestService.get(this.projectUrl).subscribe(
      (projectsListing: any) =>{
        
        
        this.projects = projectsListing;
        this.calculatePagination();
        this.setProjectsInPage();
      },
      (error: any) => {
        

        console.log(error);
      }
    );
  }

 calculatePagination(){
   

   
   if(this.projects.length %5 == 0)
      this.totalPages = Math.floor(this.projects.length/5);
   else
      this.totalPages = Math.floor(this.projects.length/5) +1;

      return
   


 }

 setProjectsInPage(){
   this.displayedProjects = this.projects.slice(
    ((this.page-1)*5), this.page*5
     
     )
 }


 prevPage(){
  if( this.page > 1){
    this.page--;
    this.setProjectsInPage();
  }
 }

 nextPage(){
   
   if(this.totalPages+1 > this.page){
    this.page++;
    this.setProjectsInPage();
  }
 }

    
}
