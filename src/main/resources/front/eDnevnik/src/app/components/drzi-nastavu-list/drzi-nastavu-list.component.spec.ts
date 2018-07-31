import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DrziNastavuListComponent } from './drzi-nastavu-list.component';

describe('DrziNastavuListComponent', () => {
  let component: DrziNastavuListComponent;
  let fixture: ComponentFixture<DrziNastavuListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DrziNastavuListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DrziNastavuListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
