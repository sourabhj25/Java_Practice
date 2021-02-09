import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import * as myGlobals from '../../app/app.global';
import 'rxjs/Rx';

@Injectable()
export class PaymentService {

  constructor(private http: Http) { }

  buyTicket(name,email,id){
    const headers = new Headers();   
    let body = {
      "email": email,
      "eventId": id,
      "fullName": name
    };
    return this.http.post(myGlobals.baseUrl + 'addBuyTicketUser' ,body ,{
        headers: headers
    })           
        .map((response: Response) => response.json()); 
  } 
}
