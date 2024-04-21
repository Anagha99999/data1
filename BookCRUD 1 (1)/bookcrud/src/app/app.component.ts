import { Component } from '@angular/core';
import { Book } from './Book';
import { Router } from '@angular/router';
import {BookServiceService} from './book-service.service'
import { MatDialog } from '@angular/material/dialog';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'bookcrud';
  books: Book[] | undefined;

  constructor(private router: Router, private bookService: BookServiceService,public dialog: MatDialog) {
  }

  getBooks() {
    this.bookService.getBooks().subscribe(data => {
      this.books = data;
    });
  }

  addBook(): void {
    this.router.navigate(['add-book'])
      .then((e) => {
        if (e) {
          console.log("Navigation is successful!");
        } else {
          console.log("Navigation has failed!");
        }
      });
  };
  deleteBook(id:number):void{
    this.bookService.deleteBook(id).subscribe((data)=>{
      console.log(data)
      this.getBooks();
      location.reload();
    },(error)=>{
      this.getBooks();
    })
  }
  

  ngOnInit(): void {
    this.router.events.subscribe(value => {
      this.getBooks();
    });
  }



}

