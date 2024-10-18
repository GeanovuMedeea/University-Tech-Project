import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UrlCollectionComponent } from './urlCollection.component';

describe('UrlCollectionComponent', () => {
  let component: UrlCollectionComponent;
  let fixture: ComponentFixture<UrlCollectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UrlCollectionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UrlCollectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
