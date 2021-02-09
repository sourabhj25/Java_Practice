import { Component } from '@angular/core';
import { ActivatedRoute, Router, provideRoutes,Params } from "@angular/router";
import {CrossStorageClient,CrossStorageHub} from 'cross-storage';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
 
})

export class AppComponent {
  constructor(private activatedRoute:ActivatedRoute,private router:Router) {

    CrossStorageHub.init([
      {origin: /\.*trackwayz.com$/,            allow: ['get', 'set', 'del']},
      {origin: /:\/\/(www\.)?trackwayz.com$/, allow: ['get', 'set', 'del']}
    ]);

  }
  
  logout(){

    localStorage.clear();
    this.router.navigate(['login']);
  }
}
