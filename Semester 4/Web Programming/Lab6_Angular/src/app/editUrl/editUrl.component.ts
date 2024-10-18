import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UrlsService } from '../urls.service';

@Component({
    selector: 'app-editUrl',
    templateUrl: './editUrl.component.html',
    styleUrls: ['./editUrl.component.css']
})
export class EditUrlComponent implements OnInit {
    id: number = 0;
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
            this.id = params['id'];
            this.url = params['url'];
            this.urlDescription = params['urlDescription'];
            this.category = params['category'];
        });
    }

    submitForm(): void {
        // @ts-ignore
        this.urlsService.updateUrl(this.id.toString(), this.url, this.urlDescription, this.category, sessionStorage.getItem('userId').toString()).subscribe(
            response => {
                console.log('URL updated successfully:', response);
                // Optionally, navigate to a different page after successful update
                this.router.navigate(['/urlcollection']);
            },
            error => {
                console.error('Failed to update URL:', error);
                this.errorMessage = error.error.message;
            }
        );
    }
}
