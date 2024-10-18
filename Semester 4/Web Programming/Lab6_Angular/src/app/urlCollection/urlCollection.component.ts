import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UrlsService } from '../urls.service';
import {Observable} from "rxjs";
import {Router} from "@angular/router";
import { Url } from '../url.model';

const ITEMS_PER_PAGE = 4;

@Component({
  selector: 'app-urlCollection',
  templateUrl: './urlCollection.component.html',
  styleUrls: ['./urlCollection.component.css']
})

export class UrlCollectionComponent implements OnInit {
  urls: Url[] = [];
  currentPage: number = 1;
  category: string = '';
  sortAlphabetically: boolean = false;
  errorMessage: string = '';

  constructor(private urlsService: UrlsService,private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.loadUrls();
  }

  loadUrls(): void {
    // @ts-ignore
    this.urlsService.readUrls(this.category, this.currentPage.toString(), ITEMS_PER_PAGE.toString(), sessionStorage.getItem('userId').toString(),"0").subscribe(
        response => {
          console.log(response);
          this.urls=response;
        },
        error => {
          this.errorMessage = error.error.message;
          //console.log(this.errorMessage, error.error.stuff, error.error.userId);
          console.error('There was an error!', error.error);
        }
    );
  }

  editUrl(id: number): void {
      const url = this.urlsService.findUrlById(id,this.urls);
      if (url) {
          this.router.navigate(['/edit'], { queryParams: { id: url.id, url: url.url, urlDescription: url.urlDescription, category: url.category } });
      } else {
          console.error(`URL with ID ${id} not found.`);
      }
  }

  deleteUrl(id: number): void {
    if (confirm("Are you sure you want to delete this URL?")) {
      // @ts-ignore
      this.urlsService.deleteUrl(id.toString(), sessionStorage.getItem('userId').toString()).subscribe(
          response => {
            console.log(response);
            this.loadUrls();
          },
          error => {
            console.error('Failed to delete URL:', error);
            this.errorMessage = error.error.message;
            alert("Failed deleting URL");
          }
      );
    }
  }

  sortUrlsAlphabetically(): void {
    if (this.sortAlphabetically) {
      //this.urls.sort((a, b) => a.url.localeCompare(b.url));
    // @ts-ignore
        this.urlsService.readUrls(this.category, this.currentPage.toString(), ITEMS_PER_PAGE.toString(), sessionStorage.getItem('userId').toString(),"1").subscribe(
        response => {
            console.log(response);
            this.urls=response;
        },
        error => {
            this.errorMessage = error.error.message;
            //console.log(this.errorMessage, error.error.stuff, error.error.userId);
            console.error('There was an error!', error.error);
        }
    );
    }
  }

  nextPage(): void {
    this.currentPage++;
    if(!this.sortAlphabetically)
        this.loadUrls();
    else
        this.sortUrlsAlphabetically();
  }

  prevPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
        if(!this.sortAlphabetically)
            this.loadUrls();
        else
            this.sortUrlsAlphabetically();
    }
  }
}
