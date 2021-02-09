import { Component, OnInit } from '@angular/core';
import { EnduserregistrService } from './enduserregistr.service'
import { ActivatedRoute, Router, provideRoutes, Params } from "@angular/router";
import { EndUserEventsComponent } from '../end-user-events/end-user-events.component';

@Component({
  selector: 'app-enduserregistr',
  templateUrl: './enduserregistr.component.html',
  styleUrls: ['./enduserregistr.component.css'],
  providers: [EnduserregistrService]
})

export class EnduserregistrComponent implements OnInit {

  public eventId;
  public loggedInEmail = localStorage.getItem('email');
  public login:boolean = false;
  public register:boolean = false;

  constructor(private enduserregisterService: EnduserregistrService,
    private activatedRoute: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {   console.log("loggedInEmail",this.loggedInEmail) ; 
  if(this.loggedInEmail == ''){
    this.register = true;
  }
  else{
    this.login = false;
  }
}

  endUserRegistration(name, email, password) {
    this.enduserregisterService.endUserRegistration(name, email, password)
      .subscribe(
      data => {
        if (data.response_code == 200) {
          let token = "Basic " + btoa(email + ":" + password);
          localStorage.setItem('email', data.response_body.email);
          localStorage.setItem('partnerId', data.response_body.partnerId);
          localStorage.setItem('roleType', data.response_body.role.roleType);
          localStorage.setItem('userId', data.response_body.userId);
          localStorage.setItem('token', token);
          console.log("data", data);
          this.router.navigate(['ticketbook']);
          alert(data.response_message);
        }
        else {
          alert(data.response_message)
        }
      });
  }

}
