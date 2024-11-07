import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TascaCreateComponent } from './tasca-create.component';

describe('TascaCreateComponent', () => {
  let component: TascaCreateComponent;
  let fixture: ComponentFixture<TascaCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TascaCreateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TascaCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
