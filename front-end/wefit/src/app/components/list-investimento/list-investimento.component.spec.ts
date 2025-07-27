import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListInvestimentoComponent } from './list-investimento.component';

describe('ListInvestimentoComponent', () => {
  let component: ListInvestimentoComponent;
  let fixture: ComponentFixture<ListInvestimentoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListInvestimentoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListInvestimentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
