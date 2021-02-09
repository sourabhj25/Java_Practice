import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA, Component } from '@angular/core';
import { RouterModule, Routes, CanActivate } from '@angular/router';
import { HttpModule } from '@angular/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { MyorganisationComponent } from './myorganisation/myorganisation.component';
import { ModalModule } from 'ng2-bootstrap';
import { ButtonsModule, PaginationModule } from 'ng2-bootstrap';
import { EventsComponent } from './events/events.component';
import { AuthGuard } from './authGuard/authGuard.service';
// import { RouteGuard } from './authGuard/routeGuard.service';
import { EndUserEventsComponent } from './end-user-events/end-user-events.component';
import { NavbarComponent } from './navbar/navbar.component';
import { searchFilterPipe } from './globalUse/pipes/searchFilter/searchFilter.module';
import { UserComponent } from './user/user.component';
import { RegistrationComponent } from './registration/registration.component';
import { EnduserregistrComponent } from './enduserregistr/enduserregistr.component';
import { ListComponent } from './list/list.component';
import { Ng2PaginationModule } from 'ng2-pagination';
// import { MatInputModule, MatButtonModule, MatSelectModule, MatIconModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormControl} from "@angular/forms";
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { TicketbookComponent } from './ticketbook/ticketbook.component';
import { BuyTicketComponent } from './buy-ticket/buy-ticket.component';
import { NgxQRCodeModule } from 'ngx-qrcode2';
import { NgxBarcodeModule } from 'ngx-barcode';
import { PaymentComponent } from './payment/payment.component';
import {CrossStorageClient,CrossStorageHub} from 'cross-storage';

const APP_ROUTES = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'event/list', component: EndUserEventsComponent}, 
  {path: 'ticketbook', component: TicketbookComponent }, 
  {path: 'myorganisation', component: MyorganisationComponent,canActivate:[AuthGuard] },  
  {path: 'event/list/:id', component: ListComponent,canActivate:[AuthGuard] },
  {path: 'events/:name/:id', component: EventsComponent,canActivate: [AuthGuard]},
  {path: 'event/list', component: EndUserEventsComponent},
  {path: 'myorganisation', component: MyorganisationComponent,canActivate: [AuthGuard] },

  {path: 'ticketbook', component: TicketbookComponent }, 
  // {path: 'event/list/:id', component: ListComponent },
  // {path: 'events/:name/:id', component: EventsComponent,canActivate: [AuthGuard] },
  {path: 'user/:name/:id', component: UserComponent,canActivate: [AuthGuard] },  
  {path: ':token/:email', component: RegistrationComponent } ,
  {path: 'enduserregistr', component: EnduserregistrComponent },
  {path: 'payment/:name/:id', component:PaymentComponent},  
  {path: 'buy-ticket/:name/:id', component: BuyTicketComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    MyorganisationComponent,
    EventsComponent,
    EndUserEventsComponent,
    NavbarComponent,
    UserComponent,
    RegistrationComponent,
    EnduserregistrComponent,
    ListComponent,
    TicketbookComponent,
    BuyTicketComponent,
    PaymentComponent   
  ],
  imports: [
    BrowserModule,
    HttpModule,
    ModalModule.forRoot(),
    RouterModule.forRoot(APP_ROUTES),
    ButtonsModule.forRoot(),
    PaginationModule.forRoot(),
    Ng2PaginationModule,
    CommonModule,
    FormsModule,
    searchFilterPipe,
    NgxBarcodeModule,
    NgxQRCodeModule,
    // MatInputModule, 
    // MatButtonModule,
    // MatSelectModule,
    // MatIconModule ,BrowserAnimationsModule
  ],
  providers: [AuthGuard,{provide: LocationStrategy, useClass: HashLocationStrategy}],
  // providers: [AuthGuard,{provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
  
})

export class AppModule { }


