import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewInvestCdi102Component } from './new-invest-cdi102.component';

describe('NewInvestCdi102Component', () => {
  let component: NewInvestCdi102Component;
  let fixture: ComponentFixture<NewInvestCdi102Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewInvestCdi102Component]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NewInvestCdi102Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
