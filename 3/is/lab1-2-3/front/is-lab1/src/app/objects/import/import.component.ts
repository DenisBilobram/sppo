import { Component } from '@angular/core';
import { ObjectsService } from '../objects.service';
import { ActivatedRoute, ParamMap, RouterLink } from '@angular/router';
import { EntityType } from '../objects-manager/objects-manager.component';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-import',
  standalone: true,
  imports: [RouterLink, NgIf],
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.scss']
})
export class ImportComponent {

  entityType: EntityType | null = null;
  
  selectedFile: File | null = null;

  result: string = '';

  importHistory: any[] | null = null;

  constructor(private objectsService: ObjectsService, private route: ActivatedRoute) {
    this.route.paramMap.subscribe((params: ParamMap) => {
      const entityTypeParam = params.get('entityType');
      this.entityType = entityTypeParam as EntityType;
      this.objectsService.getImportHistory(this.entityType).subscribe({
        next: (result) => {
          this.importHistory = result;
          console.log(result);
        }
      });
    });
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onUpload() {
    if (this.entityType == null || this.selectedFile == null) return;
  
    const formData = new FormData();
    formData.append('file', this.selectedFile);
  
    this.objectsService.importObjects(this.entityType, formData).subscribe({
      next: (response) => {
        this.result = "Imported objects.";
        this.refreshImportHistory();
      },
      error: (error) => {
        this.result = error.error.message;
        this.refreshImportHistory();
      }
    });
  }

  downloadFile(fileName: string) {
    this.objectsService.downloadFile(fileName).subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        a.remove();
      },
      error: (error) => {
        console.error('Ошибка при скачивании файла', error);
      }
    });
  }

  private refreshImportHistory() {
    this.objectsService.getImportHistory(this.entityType!).subscribe({
      next: (result) => {
        this.importHistory = result;
      }
    });
  }

  trackByFn(index: number, item: any) {
    return index;
  }
}
