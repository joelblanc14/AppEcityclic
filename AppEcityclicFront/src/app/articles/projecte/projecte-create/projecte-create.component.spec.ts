import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjecteCreateComponent } from './projecte-create.component';

describe('ProjecteCreateComponent', () => {
  let component: ProjecteCreateComponent;
  let fixture: ComponentFixture<ProjecteCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjecteCreateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjecteCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
