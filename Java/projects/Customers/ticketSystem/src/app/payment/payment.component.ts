import { Component, OnInit } from '@angular/core';
import { PaymentService } from './payment.service'
import { ActivatedRoute, Router, provideRoutes,Params } from "@angular/router";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css'],
  providers:[PaymentService]  
  
})
export class PaymentComponent implements OnInit {

  public name ;
  public id ; 
  constructor(private paymentService:PaymentService,
              private activatedRoute:ActivatedRoute,
              private router:Router) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe ((params:Params)=>{ 
      this.name = params['name'];
      this.id = params['id']
      }); 
  }

  payment(name,email){
    this.paymentService.buyTicket(name,email,this.id)
    .subscribe(
      data => {
         
      });
       this.router.navigate(['buy-ticket',this.name,this.id]);
  }
}
