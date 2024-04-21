import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookServiceService {

  private url = 'http://localhost:8081';

  constructor(private http: HttpClient) {
  }

  getBooks(): Observable<any> {
    return this.http.get(`${this.url}/getAllBooks`);
  }

  addBook(book: Object): Observable<Object> {
    return this.http.post(`${this.url}/addBook`, book);
  }

  deleteBook(id: number): Observable<any> {
    return this.http.delete(`${this.url}/deleteBook/${id}`);
  }
}
