import { NgModule } from '@angular/core';
import { RouterModule, Route } from '@angular/router';

import { ListadminComponent } from '../components/listadmin/listadmin.component';
import { AdminFormComponent } from '../components/admin-form/admin-form.component';
import { AdminEditComponent } from '../components/admin-edit/admin-edit.component';

import { UcenikListComponent } from '../components/ucenik-list/ucenik-list.component';
import { UcenikFormComponent } from '../components/ucenik-form/ucenik-form.component';
import { UcenikEditComponent } from '../components/ucenik-edit/ucenik-edit.component';
import { PredajeListComponent } from '../components/predaje-list/predaje-list.component';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { NastavnikListComponent } from '../components/nastavnik-list/nastavnik-list.component';
import { NastavnikFormComponent } from '../components/nastavnik-form/nastavnik-form.component';
import { NastavnikEditComponent } from '../components/nastavnik-edit/nastavnik-edit.component';
import { RoditeljListComponent } from '../components/roditelj-list/roditelj-list.component';
import { RoditeljFormComponent } from '../components/roditelj-form/roditelj-form.component';
import { RoditeljEditComponent } from '../components/roditelj-edit/roditelj-edit.component';
import { Dnevnik2Component } from '../components/dnevnik2/dnevnik2.component';
import { OcenaListComponent } from '../components/ocena-list/ocena-list.component';
import { DrziNastavuListComponent } from '../components/drzi-nastavu-list/drzi-nastavu-list.component';
import { OdeljenjeListComponent } from '../components/odeljenje-list/odeljenje-list.component';

const routes: Route[] = [
  { path: 'admin', component: ListadminComponent },
  { path: 'add', component: AdminFormComponent },
  { path: 'edit/:id', component: AdminEditComponent },
  { path: 'nastavnik', component: NastavnikListComponent },
  { path: 'addNastavnik', component: NastavnikFormComponent },
  { path: 'editNastavnik/:id', component: NastavnikEditComponent },
  { path: 'ucenik', component: UcenikListComponent },
  { path: 'addUcenik', component: UcenikFormComponent },
  { path: 'editUcenik/:id', component: UcenikEditComponent },
  { path: 'roditelj', component: RoditeljListComponent },
  { path: 'addRoditelj', component: RoditeljFormComponent },
  { path: 'editRoditelj/:id', component: RoditeljEditComponent },
  { path: 'predaje', component: PredajeListComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'dnevnik2', component: Dnevnik2Component },
  { path: 'ocena', component: OcenaListComponent },
  { path: 'drziNastavu', component: DrziNastavuListComponent },
  { path: 'delete/:id', component: ListadminComponent},
  { path: 'delete/:id', component: UcenikFormComponent},
  { path: 'odeljenje', component: OdeljenjeListComponent },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full'}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
