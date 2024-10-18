import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UrlsService } from '../urls.service';

@Component({
  selector: 'app-addUrl',
  templateUrl: './addUrl.component.html',
  styleUrls: ['./addUrl.component.css']
})
export class AddUrlComponent implements OnInit {
  url: string = '';
  urlDescription: string = '';
  category: string = '';
  errorMessage: string = '';

  constructor(
      private route: ActivatedRoute,
      private router: Router,
      private urlsService: UrlsService
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.url = params['url'];
      this.urlDescription = params['urlDescription'];
      this.category = params['category'];
    });
  }

  submitForm(): void {
    // @ts-ignore
    this.urlsService.addUrl(this.url, this.urlDescription, this.category, sessionStorage.getItem('userId').toString()).subscribe(
        response => {
          console.log('URL added successfully:', response);
          this.router.navigate(['/urlcollection']);
        },
        error => {
          console.error('Failed to add URL:', error);
          this.errorMessage = error.error.message;
        }
    );
  }
}
