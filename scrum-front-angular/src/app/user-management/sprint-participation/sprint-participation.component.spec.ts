import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SprintParticipationComponent } from './sprint-participation.component';

describe('SprintParticipationComponent', () => {
  let component: SprintParticipationComponent;
  let fixture: ComponentFixture<SprintParticipationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SprintParticipationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SprintParticipationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
