import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SprintBoardRowComponent } from './sprint-board-row.component';

describe('SprintBoardRowComponent', () => {
  let component: SprintBoardRowComponent;
  let fixture: ComponentFixture<SprintBoardRowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SprintBoardRowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SprintBoardRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
