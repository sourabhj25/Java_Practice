import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import * as myGlobals from '../../app/app.global';
import 'rxjs/Rx';

@Injectable()
export class ListService {
  constructor(private http: Http) { }  

    eventUser(eventName:any){      
         const headers = new Headers();
            // headers.append('authorization', localStorage.getItem('token')); 
            // let endtoken = "Basic " + btoa("nwaikar@agsft.com:nilesh123");
            // headers.append('authorization', endtoken);       
            return this.http.get(myGlobals.baseUrl + 'get/enduserevent/'+ eventName ,{
                headers: headers
            })   
    .map((response: Response) => response.json());
  }

  getUser(orgid:any){
    const headers = new Headers();
    return this.http.get(myGlobals.baseUrl + 'get/user/' + orgid,{
          headers: headers
      })   
          .map((response: Response) => response.json());
}

// eventRegister(userid,eventid){
//   const headers = new Headers();   
//   let body = {
//     "eventName":eventName,
//     "eventDescripation":eventDescription,         
//     "organizationId":id 
//   };
//   headers.append('authorization', localStorage.getItem('token'));    
//   return this.http.post(myGlobals.baseUrl + 'add/event' ,body ,{
//       headers: headers
//   })           
//       .map((response: Response) => response.json());
// }

}
