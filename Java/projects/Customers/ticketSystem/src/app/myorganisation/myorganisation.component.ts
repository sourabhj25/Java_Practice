import { Component, ViewChild, OnInit, ElementRef ,HostListener} from '@angular/core';
import { MyoragnisationserviceService } from './myoragnisationservice.service';
import { ModalDirective } from 'ng2-bootstrap/modal';
import { Router } from '@angular/router';


declare var jQuery;

@Component({
  selector: 'app-myorganisation',
  templateUrl: './myorganisation.component.html',
  styleUrls: ['./myorganisation.component.css'],
  providers: [MyoragnisationserviceService],
})

export class MyorganisationComponent implements OnInit {

  @ViewChild('orgModal') public orgModal: ModalDirective;
  @ViewChild('org') public org: ElementRef;
  @ViewChild('webAddr') public webAddr: ElementRef;
  
  public orgdata: any = [];
  public account: any;
  public role;
  public roleFlag: boolean = false;
  public alertError:boolean;
  public alertMessage;
  public orgmodalflag:boolean = false;
  public web = ".amazon.com";

  constructor(private organisationService: MyoragnisationserviceService, private router: Router) {}

  ngOnInit() {    

    this.organisation();
    this.account = localStorage.getItem("email");
    this.role = localStorage.getItem('roleType');
    // this.communicationService.notifyOther({email:localStorage.getItem('item')});        
    
    if (this.role == 'ADMIN') {
      this.roleFlag = false;
    }
    else {
      this.roleFlag = true;
    }  
  }
  

  // showSuccessMessage() {    
  //           jQuery('#divSmallBoxes').css('display', 'block');
  //           jQuery('#divSmallBoxes').fadeOut(2000);
  //           // jQuery('#orgList').fadeOut(1000);
  //       }


  submit(org, webAddr, domain) {
    this.organisationService.addOrganisation(org, webAddr, domain)
      .subscribe(
      data => {
        this.organisation();        
      });
    this.orgModal.hide();   
  }

  viewUser(name, id) {
    this.router.navigate(['./user', name, id]);
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['./login']);
  }

  organisationModal() {
    this.orgModal.show();
    this.org.nativeElement.value = '';
    this.webAddr.nativeElement.value = '';
  }
  

  organisation() {
    this.organisationService.organisation()
      .subscribe(
      data => {
        this.orgdata = data.response_body;
        if(data.response_code == 200){ 
          //this.showSuccessMessage();
          this.alertError = true;
          this.alertMessage = data.response_message;
         //alert(data.response_message);
        }else{      
         // this.showSuccessMessage();
          this.alertError = false;
          this.alertMessage = data.response_message;
         //  alert(data.response_message);  
          
        } 
      }
   );
  }

  viewEvent(name, id) {
console.log('event');
    this.router.navigate(['./events', name, id]);
  }
}
