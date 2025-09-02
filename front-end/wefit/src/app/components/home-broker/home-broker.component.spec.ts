import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeBrokerComponent } from './home-broker.component';

describe('HomeBrokerComponent', () => {
  let component: HomeBrokerComponent;
  let fixture: ComponentFixture<HomeBrokerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomeBrokerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HomeBrokerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
