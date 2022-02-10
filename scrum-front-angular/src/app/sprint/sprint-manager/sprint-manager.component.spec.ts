import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SprintManagerComponent } from './sprint-manager.component';

describe('SprintManagerComponent', () => {
  let component: SprintManagerComponent;
  let fixture: ComponentFixture<SprintManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SprintManagerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SprintManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
