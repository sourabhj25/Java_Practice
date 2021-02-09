import { Component, OnInit } from '@angular/core';
import { ListService } from './list.service';
import { ActivatedRoute, Router, provideRoutes,Params } from "@angular/router";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
  providers:[ListService]    
})

export class ListComponent implements OnInit {

  public myevent ;
  public domainName: string;
  public hostName: string;

  public eventName;
  public eName;
  public eDesc;
  public eData;
  public eventId;
  public orgid;

  constructor(private listService:ListService,private activatedRoute:ActivatedRoute,private router:Router) {
    this.domainName = location.hostname.substr(0, location.hostname.indexOf('.'));
    console.log("domainName",this.domainName);
    console.log(location.href);
    
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe ((params:Params)=>{ 
      this.eventName = params['eventName'];
      this.orgid = params['id'];
      console.log("this.orgid",this.orgid)
      }); 
      this.eventUser();
  }

//  eventRegister(){
//   this.listService.eventRegister( this.orgid)
//   .subscribe(
//     data => {
//        console.log("data",data);
//     });
//  }
  register(abc,id){
    console.log('register');
    this.eventId = id;
    this.listService.getUser( this.orgid)
    .subscribe(
      data => {
         console.log("data",data);
      });
    this.router.navigate(['enduserregistr']);    
  }

  eventUser(){
    
    this.listService.eventUser(this.domainName)
    .subscribe(
      data => {
          this.eData = data.response_body;
          // if(data.response_code != 200 ){
         
          // }      
          if(data.response_code == 200){ 
            // alert(data.response_message);
          }else{      
            //  alert(data.response_message);  
             this.router.navigate(['login']);
          } 
      });
  }

}
