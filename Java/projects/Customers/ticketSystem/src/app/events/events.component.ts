import { Component,ViewChild, OnInit,ElementRef } from '@angular/core';
import { EventsServiceService} from './events-service.service';
import { ActivatedRoute, Router, provideRoutes,Params } from "@angular/router";
import { ModalDirective } from 'ng2-bootstrap/modal';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css'],
  providers:[EventsServiceService]
})
export class EventsComponent implements OnInit {

  @ViewChild('eventModal') public eventModal: ModalDirective;
  @ViewChild('eventName') public eventname:ElementRef;
  @ViewChild('desc') public desc:ElementRef;

public orgName;
public orgId;
public eventName;
public eventDescription;
public eventId;
public eventData:any = [];

  constructor(private eventService:EventsServiceService,private activatedRoute:ActivatedRoute,private router:Router) { }

  ngOnInit() {
    
    this.activatedRoute.params.subscribe ((params:Params)=>{ 
      this.orgName = params['name'];
      this.orgId = params['id'];
      }); 
      this.eventsOrg();
  }

  createEvent(){   
    this.eventModal.show(); 
    this.eventname.nativeElement.value = '';
    this.desc.nativeElement.value = '';   
  }

  logout(){
    localStorage.clear();
    this.router.navigate(['./login']);
  }

  submitEvent(name,desc){
    console.log("event",name,desc);
     this.eventService.createEvent( this.orgId,desc,name)
    .subscribe(
      data => {
        if(data.response_code == 200){ 
        //  alert(data.response_message);
        }else{      
         //  alert(data.response_message);  
        } 
        this.eventsOrg();
      });
    this.eventModal.hide();    
  }

  eventsOrg(){
    this.eventService.eventsOrg(this.orgName,this.orgId)
    .subscribe(
      data => {
        for(let i in data.response_body){
          this.eventName = data.response_body[i].eventName;   
          this.eventDescription = data.response_body[i].description;
          this.eventId = data.response_body[i].eventID;
          this.eventData = data.response_body; 
        }
          
        if(data.response_code == 200){ 
         // alert(data.response_message);
        }else{      
          // alert(data.response_message);  
        } 
      });
  }
}
