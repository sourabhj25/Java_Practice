import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import * as myGlobals from '../../app/app.global';
import 'rxjs/Rx';

@Injectable()
export class RegistrationService {

  constructor(private http: Http) { }

  registration(token:any,email,password) {
    const headers = new Headers();   
    const body = {
      "email":email,
      "fullName":'abc',
      "password":password
    }
    headers.append('authorization', localStorage.getItem('token'));    
    return this.http.post(myGlobals.baseUrl + 'registerAdminMembers'+'/'+token, body ,{
        headers: headers
    
    })
        .map((response: Response) => response.json());
  }

}
