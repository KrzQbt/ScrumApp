import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SprintItemPickComponent } from './sprint-item-pick.component';

describe('SprintItemPickComponent', () => {
  let component: SprintItemPickComponent;
  let fixture: ComponentFixture<SprintItemPickComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SprintItemPickComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SprintItemPickComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
