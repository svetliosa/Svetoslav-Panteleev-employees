import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent {
  @Output() fileSelected = new EventEmitter<File>();
  @Output() uploadRequested = new EventEmitter<File>();

  selectedFile: File | null = null;

  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      this.fileSelected.emit(file);
    }
  }

  onUpload() {
    if (this.selectedFile) {
      this.uploadRequested.emit(this.selectedFile);
    }
  }

  getFileName(): string {
    return this.selectedFile ? this.selectedFile.name : '';
  }
} 