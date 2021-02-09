import { Component, OnInit } from '@angular/core';
import { TicketbookService } from './ticketbook.service';
import { ActivatedRoute, Router, provideRoutes,Params } from "@angular/router";



@Component({
  selector: 'app-ticketbook',
  templateUrl: './ticketbook.component.html',
  styleUrls: ['./ticketbook.component.css'],
  providers: [TicketbookService]  
  
})
export class TicketbookComponent implements OnInit {

  constructor(private ticketService:TicketbookService, private activatedRoute:ActivatedRoute,private router:Router) { }

  ngOnInit() {
    this.getTicketData();
  }

  getTicketData(){
    this.ticketService.getTicketData()
    .subscribe(
    data => {  
      if(data.response_code == 200){
        localStorage.get('email',data.response_body.email);        
        localStorage.setItem('partnerId',data.response_body.partnerId);
        localStorage.setItem('roleType',data.response_body.role.roleType);        
        localStorage.setItem('userId', data.response_body.userId);
        console.log("data",data); 
        
      }
    
  
    });
  }
}
