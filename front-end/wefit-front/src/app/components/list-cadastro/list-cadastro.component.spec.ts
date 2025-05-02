import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListCadastroComponent } from './list-cadastro.component';

describe('ListCadastroComponent', () => {
  let component: ListCadastroComponent;
  let fixture: ComponentFixture<ListCadastroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListCadastroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListCadastroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
