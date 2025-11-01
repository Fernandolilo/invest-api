import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListInvestComponent } from './list-invest.component';

describe('ListInvestComponent', () => {
  let component: ListInvestComponent;
  let fixture: ComponentFixture<ListInvestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListInvestComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListInvestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
