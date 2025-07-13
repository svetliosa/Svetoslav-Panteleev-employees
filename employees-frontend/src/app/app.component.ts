import { Component } from '@angular/core';
import { ProjectCollaborationDto } from './models/project-collaboration.dto';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Employee Pair Longest Project Time';
  collaborations: ProjectCollaborationDto[] = [];
  resultMessage: string = '';
  isLoading: boolean = false;
  errorMessage: string = '';

  onFileSelected(file: File) {
    this.resultMessage = '';
    this.loadCsvData(file);
  }

  onUploadRequested(file: File) {
    this.uploadFile(file);
  }

  private loadCsvData(file: File) {
    this.isLoading = true;
    this.errorMessage = '';
    
    const formData = new FormData();
    formData.append('file', file);

    fetch('http://localhost:8080/api/employees/load-csv-data', {
      method: 'POST',
      body: formData
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Failed to load CSV data');
      }
      return response.json();
    })
    .then((data: ProjectCollaborationDto[]) => {
      this.collaborations = data;
      this.isLoading = false;
    })
    .catch(error => {
      console.error('Error loading CSV data:', error);
      this.errorMessage = 'Error loading CSV data: ' + error.message;
      this.isLoading = false;
    });
  }

  private uploadFile(file: File) {
    this.isLoading = true;
    this.errorMessage = '';
    
    const formData = new FormData();
    formData.append('file', file);

    fetch('http://localhost:8080/api/employees/upload', {
      method: 'POST',
      body: formData
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Failed to upload file');
      }
      return response.text();
    })
    .then((result: string) => {
      this.resultMessage = 'Pair of employees who have worked together on common projects for the longest period of time - ' + result;
      this.isLoading = false;
    })
    .catch(error => {
      console.error('Error uploading file:', error);
      this.errorMessage = 'Error uploading file: ' + error.message;
      this.isLoading = false;
    });
  }
} 