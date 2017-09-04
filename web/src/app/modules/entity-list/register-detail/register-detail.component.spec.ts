import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterDetailComponent } from './entity-detail.component';

describe('RegisterDetailComponent', () => {
  let component: RegisterDetailComponent;
  let fixture: ComponentFixture<RegisterDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
