import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjecteUpdateComponent } from './projecte-update.component';

describe('ProjecteUpdateComponent', () => {
  let component: ProjecteUpdateComponent;
  let fixture: ComponentFixture<ProjecteUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjecteUpdateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjecteUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
