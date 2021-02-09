import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import * as myGlobals from '../../app/app.global';
import 'rxjs/Rx';

@Injectable()
export class TicketbookService {

  constructor(private http: Http) { }

  getTicketData(){
    const headers = new Headers();
    headers.append('authorization', localStorage.getItem('token'));
    return this.http.get(myGlobals.baseUrl + 'get/registerUser/event' , {
      headers: headers
    })
      .map((response: Response) => response.json());
  }
 
}
