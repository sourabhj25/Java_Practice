import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import * as myGlobals from '../../app/app.global';
import 'rxjs/Rx';

@Injectable()
export class EnduserregistrService {

  constructor(private http: Http) { }


  endUserRegistration(name:any,email:any,password:any){
    const headers = new Headers();   
    let body = {
      "email": email,
      "fullName": name,
      "password": password
    };
    return this.http.post(myGlobals.baseUrl + 'register' ,body ,{
        headers: headers
    })           
        .map((response: Response) => response.json());
  }
}
