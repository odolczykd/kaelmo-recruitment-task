import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {HttpClient} from '@angular/common/http';

const API_URL = 'http://localhost:8080/api/users';

interface User {
  name: string;
  username: string;
  email: string;
  gender: string;
  phone: string;
  photoUrl: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  displayedColumns: string[] = ['picture', 'username', 'name', 'email', 'gender', 'phone'];
  dataSource = new MatTableDataSource<User>([]);
  isLoading: boolean = true;
  isError: boolean = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchUsers();
  }

  applyFilter(event: Event) {
    this.dataSource.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  private fetchUsers() {
    this.http.get<User[]>(API_URL).subscribe({
      next: (response) => {
        this.dataSource.data = response;
        console.log(this.dataSource.data);
        setTimeout(() => {
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
          this.isLoading = false;
        });
      },
      error: () => {
        this.isLoading = false;
        this.isError = true;
      }
    });
  }
}
