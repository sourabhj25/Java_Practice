import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
public image;
  constructor() {

    this.image = '/assets/Cool-Wallpaper-for-iPad-1920x1080.jpg-2.jpg';
   }

  ngOnInit() {    
    let email=localStorage.getItem('email');

    let partnerId=localStorage.getItem('partnerId');
    let token=localStorage.getItem('token');
    let roleType=localStorage.getItem('roleType');
    // this.SendViewerStatus(email,partnerId,token,roleType);
    

 
}

  /*  SendViewerStatus(data,partnerId,token,roleType) {    
  }

 /*   SendViewerStatus(data,partnerId,token,roleType) {    
        var jsonData = {}; 
        try {
            jsonData['email'] = data;    
            jsonData['partnerId'] = partnerId;
            jsonData['token'] = token;
            jsonData['roleType'] = roleType;
            top.postMessage(jsonData, '*');  
        }
        catch (err) {
            console.log('postMessage Failed');
        }    
    } */
}
