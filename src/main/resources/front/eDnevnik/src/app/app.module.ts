import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import { MessageService } from './services/message.service';
import { AppRoutingModule } from './app-routing/app-routing.module';

import { AppComponent } from './app.component';
import { ListadminComponent } from './components/listadmin/listadmin.component';
import { AdminFormComponent } from './components/admin-form/admin-form.component';
import { AdminService } from './services/admin.service';
import { MessageListComponent } from './components/message-list/message-list.component';
import { LoginComponent } from './components/login/login.component';
import { AdminEditComponent } from './components/admin-edit/admin-edit.component';
import { UcenikListComponent } from './components/ucenik-list/ucenik-list.component';
import { UcenikFormComponent } from './components/ucenik-form/ucenik-form.component';
import { UcenikService } from './services/ucenik.service';
import { UcenikEditComponent } from './components/ucenik-edit/ucenik-edit.component';
import { PredajeListComponent } from './components/predaje-list/predaje-list.component';
import { PredajeService } from './services/predaje.service';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NastavnikListComponent } from './components/nastavnik-list/nastavnik-list.component';
import { NastavnikEditComponent } from './components/nastavnik-edit/nastavnik-edit.component';
import { NastavnikFormComponent } from './components/nastavnik-form/nastavnik-form.component';
import { RoditeljListComponent } from './components/roditelj-list/roditelj-list.component';
import { RoditeljFormComponent } from './components/roditelj-form/roditelj-form.component';
import { RoditeljEditComponent } from './components/roditelj-edit/roditelj-edit.component';
import { Dnevnik2Component } from './components/dnevnik2/dnevnik2.component';
import { OcenaListComponent } from './components/ocena-list/ocena-list.component';
import { DrziNastavuListComponent } from './components/drzi-nastavu-list/drzi-nastavu-list.component';
import { OdeljenjeListComponent } from './components/odeljenje-list/odeljenje-list.component';


@NgModule({
  declarations: [
    AppComponent,
    ListadminComponent,
    AdminFormComponent,
    MessageListComponent,
    LoginComponent,
    AdminEditComponent,
    UcenikListComponent,
    UcenikFormComponent,
    UcenikEditComponent,
    PredajeListComponent,
    DashboardComponent,
    NastavnikListComponent,
    NastavnikEditComponent,
    NastavnikFormComponent,
    RoditeljListComponent,
    RoditeljFormComponent,
    RoditeljEditComponent,
    Dnevnik2Component,
    OcenaListComponent,
    DrziNastavuListComponent,
    OdeljenjeListComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule

  ],
  providers: [AdminService, MessageService, UcenikService, PredajeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
