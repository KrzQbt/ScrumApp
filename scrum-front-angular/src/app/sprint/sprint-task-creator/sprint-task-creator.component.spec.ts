import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SprintTaskCreatorComponent } from './sprint-task-creator.component';

describe('SprintTaskCreatorComponent', () => {
  let component: SprintTaskCreatorComponent;
  let fixture: ComponentFixture<SprintTaskCreatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SprintTaskCreatorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SprintTaskCreatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
