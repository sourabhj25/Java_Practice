import { Component, OnInit,ViewChild,ElementRef } from '@angular/core';
import { BuyticketService } from './buyticket.service'
import { ActivatedRoute, Router, provideRoutes,Params } from "@angular/router";

@Component({
  selector: 'app-buy-ticket',
  templateUrl: './buy-ticket.component.html',
  styleUrls: ['./buy-ticket.component.css'],
  providers:[BuyticketService]  
  
})
export class BuyTicketComponent implements OnInit {
  elementType : 'url' | 'canvas' | 'img' = 'url';
  value : string = 'dadasaheb123';
  //@ViewChild('eventName') public eventname:ElementRef;
  barCodeValue: string ='shekhar123';
  public eventname;
  public eventid;

  IsHidden= true;
  constructor(private buyTicketService:BuyticketService,
              private activatedRoute:ActivatedRoute,
              private router:Router) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe ((params:Params)=>{ 
      this.eventname = params['name'];
      this.eventid = params['id'];

      //this.eventname="Bottle Rock Party Hard";
    //  alert('EventName='+this.eventname);
    this.barCodeValue=this.eventname;
    
    this.value=this.eventname+"QR";
      }); 
      
   }

  onClick(){    
    this.IsHidden= !this.IsHidden;
  }
  

  register(){
    this.router.navigate(['enduserregistr']);

  }

}