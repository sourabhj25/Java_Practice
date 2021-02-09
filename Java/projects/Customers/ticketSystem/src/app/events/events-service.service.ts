import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import * as myGlobals from '../../app/app.global';
import 'rxjs/Rx';

@Injectable()
export class EventsServiceService {

  constructor(private http: Http) { }

        eventsOrg(orgName:any,orgId:any){
          const headers = new Headers();   
          let body = {
            // "OrganisationName":orgName,
            // "OrganisationId":orgId
          };
          headers.append('authorization', localStorage.getItem('token'));    
          return this.http.post(myGlobals.baseUrl + 'get/event/by/'+ orgName  ,body ,{
              headers: headers
          })   
              .map((response: Response) => response.json());
        }

        createEvent(id,eventDescription,eventName){
          const headers = new Headers();   
          let body = {
            "eventName":eventName,
            "eventDescripation":eventDescription,         
            "organizationId":id 
          };
          headers.append('authorization', localStorage.getItem('token'));    
          return this.http.post(myGlobals.baseUrl + 'add/event' ,body ,{
              headers: headers
          })           
              .map((response: Response) => response.json());
        }
  }
  

