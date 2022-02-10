import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from './services/token/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{


  isLogged= false;

  isLoggedIn(){
    if(this.tokenStorage.getToken()!=null)
      return true;

    return false;
  }

  logOut(){
    this.tokenStorage.signOut();
  }

  logReg ="log";


  title = 'scrum-front';
  url:any;

  constructor(private router: Router, private tokenStorage: TokenStorageService){
    
  }
  ngOnInit(){
    this.url = this.router.url;

  }
  
}
