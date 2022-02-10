import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectParticipationComponent } from './project-participation.component';

describe('ProjectParticipationComponent', () => {
  let component: ProjectParticipationComponent;
  let fixture: ComponentFixture<ProjectParticipationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProjectParticipationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectParticipationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
