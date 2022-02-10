import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UrlBreadcrumpComponent } from './url-breadcrump.component';

describe('UrlBreadcrumpComponent', () => {
  let component: UrlBreadcrumpComponent;
  let fixture: ComponentFixture<UrlBreadcrumpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UrlBreadcrumpComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UrlBreadcrumpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
