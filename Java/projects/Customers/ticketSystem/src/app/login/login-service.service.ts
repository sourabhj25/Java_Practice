import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import * as myGlobals from '../../app/app.global';
import 'rxjs/Rx';

@Injectable()
export class LoginServiceService {
  constructor(private http: Http) { }

  loginSubmit(arr:any) {
    const headers = new Headers();
    headers.append('Authorization', localStorage.getItem('partnerId'));
    return this.http.post(myGlobals.baseUrl + 'login', arr, {
        headers: headers
    })
        .map((response: Response) => response.json());
  }

}
