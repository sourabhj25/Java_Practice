import { Component,ViewChild, OnInit,ElementRef } from '@angular/core';
import { RegistrationService } from './registration.service';
import { ModalDirective } from 'ng2-bootstrap/modal';
import { ActivatedRoute, Router, provideRoutes,Params } from "@angular/router";
import { Pipe, PipeTransform, Injectable } from '@angular/core';

declare var jQuery;

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
  providers: [RegistrationService]  
})

export class RegistrationComponent implements OnInit {

  public regEmail;
  public role;
  public token;
  public alert;
  public message:boolean = false;

  constructor(private registerService:RegistrationService, private activatedRoute:ActivatedRoute,private router:Router) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe ((params:Params)=>{ 
      this.regEmail = params['email'];
      this.token = params['token']
      }); 
  }

  showSuccessMessage() {
    
            jQuery('#divSmallBoxes').css('display', 'block');
            jQuery('#divSmallBoxes').fadeOut(10000);
        }

  registration(password){
    this.registerService.registration(this.token,this.regEmail,password)
    .subscribe(
      data => {    
        if(data.response_code == 200){        
          // this.alert = data.response_message  ;  
          // this.message = true
          // this.showSuccessMessage();
          // alert(data.response_message);
          this.router.navigate(['login']);
        }   
      });
  }


}
