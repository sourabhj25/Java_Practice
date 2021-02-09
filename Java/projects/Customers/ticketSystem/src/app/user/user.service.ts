import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import * as myGlobals from '../../app/app.global';
import 'rxjs/Rx';

@Injectable()
export class UserService {

  constructor(private http: Http) { }

  user(orgId:any){
    const headers = new Headers();   
    headers.append('authorization', localStorage.getItem('token'));    
    return this.http.get(myGlobals.baseUrl + '/get/user/' +  orgId, {
     
        headers: headers
    })
        .map((response: Response) => response.json());
  }

  addUSer(email:any,name:any,orgId:any,role:any){
    console.log("adduser",email,name,orgId,role)
    const headers = new Headers();   
    let body = {
      "email": email,
      "fullName": name,
      "organisationIds":orgId,
      "password":'',
      "roleType":role
    }
    headers.append('authorization', localStorage.getItem('token'));    
    return this.http.post(myGlobals.baseUrl + 'addUser' ,body ,{
        headers: headers
    })
        .map((response: Response) => response.json());
  }

}
