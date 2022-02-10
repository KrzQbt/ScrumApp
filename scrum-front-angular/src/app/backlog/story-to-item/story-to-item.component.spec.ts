import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StoryToItemComponent } from './story-to-item.component';

describe('StoryToItemComponent', () => {
  let component: StoryToItemComponent;
  let fixture: ComponentFixture<StoryToItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StoryToItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StoryToItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
