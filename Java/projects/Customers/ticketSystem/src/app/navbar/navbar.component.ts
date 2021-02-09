import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {CrossStorageClient,CrossStorageHub} from 'cross-storage';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {
public email:any;
constructor(private router:Router) { }

  ngOnInit() {
    this.email = localStorage.getItem('email');
  }

  
  logout(){
    var storage = new CrossStorageClient('http://trackwayz.com');
        storage.onConnect()
    .then(function() {
      return storage.del('email', 'partnerId','token','roleType');
    }).then(function(res) {
    })['catch'](function(err) {
      console.log(err);
    });

    localStorage.clear();
    this.router.navigate(['./login']);
  }
}
