import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TascaListProjecteComponent } from './tasca-list-projecte.component';

describe('TascaListProjecteComponent', () => {
  let component: TascaListProjecteComponent;
  let fixture: ComponentFixture<TascaListProjecteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TascaListProjecteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TascaListProjecteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
