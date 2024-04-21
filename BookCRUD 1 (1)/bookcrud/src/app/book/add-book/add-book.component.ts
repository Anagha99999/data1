import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BookServiceService } from '../../book-service.service';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrl: './add-book.component.css'
})
export class AddBookComponent {
  constructor(private formBuilder: FormBuilder, private router: Router, private bookService: BookServiceService) {
  }

  addForm!: FormGroup;

  ngOnInit() {
    this.addForm = this.formBuilder.group({
      id: [],
      title: ['', Validators.required],
      author: ['', Validators.required]
    });

  }

  onSubmit() {
    this.bookService.addBook(this.addForm.value)
      .subscribe((data: any) => {
        this.router.navigate(['list-books']);
      });
  }

}
