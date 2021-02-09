import { Component, OnInit } from '@angular/core';
import {LoginServiceService} from './login-service.service';
import { RouterModule,Router } from '@angular/router';
// import {NotificationsService} from 'angular4-notify';
declare var jQuery;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [LoginServiceService],
})

export class LoginComponent implements OnInit {
  public array :any;
  public loginErrMessage:boolean = true;
  public alertMessage:any;

  constructor(private loginService: LoginServiceService, private router:Router) {
        //   if(localStorage.getItem('partnerId')){
        //     this.router.navigate(['myorganisation']);
        // }
  }

  ngOnInit() {
    
  }
  showSuccessMessage() {
    if(this.loginErrMessage == true){
      jQuery('#divSmallBoxes').css('display', 'block');
      jQuery('#divSmallBoxes').fadeOut(5000);
      this.router.navigate(['myorganisation']);
    }
    else{
      jQuery('#divSmallBoxes').css('display', 'block');
      jQuery('#divSmallBoxes').fadeOut(1000);
    }      
 }

  login(email,password){
    this.array = {
      "email":email,
      "password":password
    }
    
    this.loginService.loginSubmit(this.array)
    .subscribe(
      data => {      
        
        if(data.response_body.role.roleType == 'END_USER'){
          let token = "Basic " + btoa(email+ ":" + password);
          localStorage.setItem('email', data.response_body.email);
          localStorage.setItem('roleType',data.response_body.role.roleType);        
          localStorage.setItem('partnerId', data.response_body.partnerId);
          localStorage.setItem('token',token); 
          this.router.navigate(['event/list']);
        }
        else{
          let token = "Basic " + btoa(email+ ":" + password);
          localStorage.setItem('email', data.response_body.email);
          localStorage.setItem('roleType',data.response_body.role.roleType);        
          localStorage.setItem('partnerId', data.response_body.partnerId);
          localStorage.setItem('token',token); 
          
          this.router.navigate(['myorganisation']);
          
        }
        // if(data.response_code == 200){          
        //   let token = "Basic " + btoa(email+ ":" + password);
        //   localStorage.setItem('email', data.response_body.email);
        //   localStorage.setItem('roleType',data.response_body.role.roleType);        
        //   localStorage.setItem('partnerId', data.response_body.partnerId);
        //   localStorage.setItem('token',token);
        //   this.showSuccessMessage();
        //   this.loginErrMessage   = true;
        //   this.alertMessage = data.response_message; 
        // }else{
          
        //   // this.notificationsService.addError(data.response_message);
        //   this.showSuccessMessage();
        //   this.loginErrMessage   = false;
        //   this.alertMessage = data.response_message;       
        // }      
      });    
  }
}
