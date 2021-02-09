import { Component, OnInit,ElementRef,ViewChild } from '@angular/core';
import {UserService} from './user.service';
import { ActivatedRoute, Router, provideRoutes,Params } from "@angular/router";
import { ModalDirective } from 'ng2-bootstrap/modal';

declare var jQuery;


@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
  providers: [UserService],  
})
export class UserComponent implements OnInit {

  @ViewChild('userModal') public userModal: ModalDirective;
  @ViewChild('eventName') public eventname:ElementRef;
  @ViewChild('desc') public desc:ElementRef;

  public orgName;
  public orgId;
  public userData;
  public role;
  public alert;
  public message:boolean = false;
  
  constructor(private userservice:UserService, private activatedRoute:ActivatedRoute,private router:Router) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe ((params:Params)=>{ 
      this.orgName = params['name'];
      this.orgId = params['id'];
      }); 
    this.user();
  }

  backToOrg(){
    this.router.navigate(['myorganisation']);
  }
  user(){
    this.userservice.user(this.orgId)
    .subscribe(
      data => {    
        this.userData = data.response_body;
        if(data.response_code == 200){ 
        //  alert(data.response_message);
        }else{      
          // alert(data.response_message);  
        } 
      });
  }
  roleType(value){
    this.role = value;   
  }

  createUser(){
    this.userModal.show();
  }
  showSuccessMessage() {    
            jQuery('#divSmallBoxes').css('display', 'block');
            jQuery('#divSmallBoxes').fadeOut(10000);
        }

  AddUser(name,email,role){
    this.userModal.hide();
    this.userservice.addUSer(email,name,this.orgId,role)
    .subscribe(
      data => { 
        if(data.response_code == 200){ 
          //alert(data.response_message);
        }else{      
          // alert(data.response_message);  
        } 
        this.user();
      });
    }
}
