
import { Component, OnInit,Input,Output,EventEmitter} from '@angular/core';
import { EndUserServiceService } from './end-user-service.service'
import { ActivatedRoute, Router, provideRoutes,Params } from "@angular/router";
import {Location} from '@angular/common';
import {CrossStorageClient,CrossStorageHub} from 'cross-storage';


@Component({
  selector: 'app-end-user-events',
  templateUrl: './end-user-events.component.html',
  styleUrls: ['./end-user-events.component.css'],
  providers:[EndUserServiceService]  
})

export class EndUserEventsComponent implements OnInit {

  public eventName;
  public eName;
  public eDesc;
  public eData;
  public eventId;
  public email;
  public domainName: string;
  public hostName: string;


  public partnerId;
  public token;
  public roleType;


  @Output() public changeEmitter = new EventEmitter<any>();
  // ngOnInit() { 
    
        // window.addEventListener('message', this.postMessageEventHandler, false);
       /*  window.addEventListener('message', (event) => {
          this.email = event.data.email;
          this.partnerId = event.data.partnerId;
          this.token = event.data.token;
          this.roleType = event.data.roleType;
          console.log("event",event);
          console.log("email",this.email);
          localStorage.setItem('email', this.email);
          localStorage.setItem('partnerId', this.partnerId);
          localStorage.setItem('token', this.token);
          localStorage.setItem('roleType', this.roleType);
          console.log(this.changeEmitter);
          this.changeEmitter.emit(this.email);
       }, false); */
    
      // }
  constructor(private endUserEventService:EndUserServiceService,private activatedRoute:ActivatedRoute
    ,private router:Router,private _location: Location) { }

  ngOnInit() { 
    var storage = new CrossStorageClient('http://trackwayz.com');
    storage.onConnect()
    .then(function() {
      return storage.get('email', 'partnerId','token','roleType');
    }).then(function(res) {
      console.log("res==="+res); // ['foo', 'bar']
      localStorage.setItem("email",res[0]);
      localStorage.setItem("partnerId",res[1]);
      localStorage.setItem("token",res[2]);
      localStorage.setItem("roleType",res[3]);
      return storage.set('email','snehal@agasft.com');
    })['catch'](function(err) {
      console.log(err);
    });

    this.domainName = location.hostname.substr(0, location.hostname.indexOf('.'));
    console.log("domainName",this.domainName);
    console.log(location.href);
    let id;

    this.activatedRoute.params.subscribe ((params:Params)=>{ 
     this.eventName=location.hostname.substr(0, location.hostname.indexOf('.'));
     this.hostName =location.host.split('.')[1];
     console.log("hostname",this.hostName);
      }); 
      this.eventUser();      
  }

  backToLogin(){

    
    this.router.navigate(['myorganisation']);    
    
  }

  buyTicket(eventname,id){
    this.eventId = id;
    this.endUserEventService.buyTicket(id)
    .subscribe(
      data => {});
    this.router.navigate(['payment',eventname,id]);    
  }

  eventUser(){
    this.endUserEventService.eventUser(this.eventName)
    .subscribe(
      data => {
          this.eData = data.response_body;
          if(data.response_code != 200 ){
           this.router.navigate(['login']);
          }      
      });
  }


  homePage(){
    console.log('fdf');
    this._location.back();
        // this.router.navigate(['myorganisation']);
  }


/*   postMessageEventHandler (event) {   
    this.email = event.data.email;
    this.partnerId = event.data.partnerId;
    this.token = event.data.token;
    this.roleType = event.data.roleType;
    localStorage.setItem('email', this.email);
    // console.log(this.changeEmitter)
     this.changeEmitter.emit(this.email);

  }
=======
    localStorage.setItem('partnerId', this.partnerId);
    localStorage.setItem('token', this.token);
    localStorage.setItem('roleType', this.roleType);
    console.log(this.changeEmitter)
     this.changeEmitter.emit(this.email);
  } */

}
