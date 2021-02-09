import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import * as myGlobals from '../../app/app.global';
import 'rxjs/Rx';

@Injectable()
export class MyoragnisationserviceService {

  constructor(private http: Http) { }

  organisation() {
    const headers = new Headers();
    headers.append('authorization', localStorage.getItem('token'));
    return this.http.get(myGlobals.baseUrl + 'get/orgnisation/' + localStorage.getItem('partnerId'), {
      headers: headers
    })
      .map((response: Response) => response.json());
  }

  addOrganisation(org: any, webAddr: any, web: any) {
    const headers = new Headers();
    let body = {
      "orgName": org,
      "orgUrl": webAddr + '.' + web,
      "partnerId": localStorage.getItem('partnerId')
    }
    headers.append('authorization', localStorage.getItem('token'));
    return this.http.post(myGlobals.baseUrl + 'add/orgnisation', body, {
      headers: headers
    })
      .map((response: Response) => response.json());
  }
}
