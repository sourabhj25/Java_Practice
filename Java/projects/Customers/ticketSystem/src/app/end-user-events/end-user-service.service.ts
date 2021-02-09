import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import * as myGlobals from '../../app/app.global';
import 'rxjs/Rx';

@Injectable()
export class EndUserServiceService {

  constructor(private http: Http) { }

  eventUser(orgName:any){
    
       const headers = new Headers();   
        
    //    console.log('email',localStorage.getItem('email'));
          // headers.append('authorization', localStorage.getItem('token')); 
          // let endtoken = "Basic " + btoa("nwaikar@agsft.com:nilesh123");
          // headers.append('authorization', endtoken);       
          return this.http.get(myGlobals.baseUrl + 'get/enduserevent/'+ orgName ,{
              headers: headers
          })   
              .map((response: Response) => response.json());
}


buyTicket(id:any){
    const headers = new Headers();   
    let body = {
      "email": "hritik@agsft.com",
      "eventId": id,
      "fullName": "Hritik Roshan"
    };
    return this.http.post(myGlobals.baseUrl + 'addBuyTicketUser' ,body ,{
        headers: headers
    })           
        .map((response: Response) => response.json());
 
  } 

getUser(orgid:any){
    const headers = new Headers();
    return this.http.get(myGlobals.baseUrl + 'get/user/'+ orgid ,{
          headers: headers
      })   
          .map((response: Response) => response.json());
}
}
