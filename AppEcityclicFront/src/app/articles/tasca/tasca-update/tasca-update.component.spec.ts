import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TascaUpdateComponent } from './tasca-update.component';

describe('TascaUpdateComponent', () => {
  let component: TascaUpdateComponent;
  let fixture: ComponentFixture<TascaUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TascaUpdateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TascaUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
