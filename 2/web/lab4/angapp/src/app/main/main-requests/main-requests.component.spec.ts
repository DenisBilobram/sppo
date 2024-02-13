import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainRequestsComponent } from './main-requests.component';

describe('MainRequestsComponent', () => {
  let component: MainRequestsComponent;
  let fixture: ComponentFixture<MainRequestsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MainRequestsComponent]
    });
    fixture = TestBed.createComponent(MainRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
