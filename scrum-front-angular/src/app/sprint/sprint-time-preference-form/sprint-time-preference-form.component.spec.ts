import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SprintTimePreferenceFormComponent } from './sprint-time-preference-form.component';

describe('SprintTimePreferenceFormComponent', () => {
  let component: SprintTimePreferenceFormComponent;
  let fixture: ComponentFixture<SprintTimePreferenceFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SprintTimePreferenceFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SprintTimePreferenceFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
