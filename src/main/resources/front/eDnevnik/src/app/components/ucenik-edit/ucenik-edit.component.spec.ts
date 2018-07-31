import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UcenikEditComponent } from './ucenik-edit.component';

describe('UcenikEditComponent', () => {
  let component: UcenikEditComponent;
  let fixture: ComponentFixture<UcenikEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UcenikEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UcenikEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
