import { Injectable } from '@angular/core';
import { HttpClient, HttpParams,HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Url } from './url.model';

@Injectable({
  providedIn: 'root'
})
export class UrlsService {
  private readUrl = 'http://localhost/url_project/php/entity/read.php';
  private deleteUrlLink = 'http://localhost/url_project/php/entity/delete.php';
  private updateUrlLink = 'http://localhost/url_project/php/entity/update.php';
  private addUrlLink = 'http://localhost/url_project/php/entity/create.php';

  constructor(private http: HttpClient) {
  }

  readUrls(category: string, page: string, limit: string, userId: string, sorted: string): Observable<any> {
    console.log(sorted);
    const params = new HttpParams()
        .set('category', category)
        .set('page', page)
        .set('limit', limit)
        .set('userId', userId)
        .set('sort',sorted);

    return this.http.get<Url[]>(this.readUrl, { params });
  }

  deleteUrl(id: string, userId: string): Observable<any> {
    const params = new HttpParams()
        .set('id', id)
        .set('userId', userId);
    return this.http.delete<any>(this.deleteUrlLink, {params});
  }

  updateUrl(id: string, url: string, urlDescription: string, category: string, userId:string): Observable<any> {
    return this.http.post<any>(this.updateUrlLink, { id, url, urlDescription, category,userId},{
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }

  addUrl(url: string, urlDescription: string, category: string, userId:string): Observable<any> {
    return this.http.post<any>(this.addUrlLink, { url, urlDescription, category,userId},{
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }

  findUrlById(id: number, urls:Url[]) : Url | undefined {
    return urls.find(url => url.id === id);
  }

}
