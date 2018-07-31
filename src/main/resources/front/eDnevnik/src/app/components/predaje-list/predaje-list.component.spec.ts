import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PredajeListComponent } from './predaje-list.component';

describe('PredajeListComponent', () => {
  let component: PredajeListComponent;
  let fixture: ComponentFixture<PredajeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PredajeListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PredajeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
