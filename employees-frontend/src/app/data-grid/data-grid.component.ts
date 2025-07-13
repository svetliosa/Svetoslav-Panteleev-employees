import { Component, Input } from '@angular/core';
import { ProjectCollaborationDto } from '../models/project-collaboration.dto';

@Component({
  selector: 'app-data-grid',
  templateUrl: './data-grid.component.html',
  styleUrls: ['./data-grid.component.css']
})
export class DataGridComponent {
  @Input() collaborations: ProjectCollaborationDto[] = [];
} 