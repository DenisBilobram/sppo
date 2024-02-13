import { AfterViewInit, Component, ElementRef, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { MainService } from '../main.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SelectItem } from 'primeng/api';
import { HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { RouteConfigLoadEnd, Router } from '@angular/router';


@Component({
  selector: 'app-main-requests',
  templateUrl: './main-requests.component.html',
  styleUrls: ['./main-requests.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MainRequestsComponent implements AfterViewInit, OnInit {

  requestsHistoryData = new MatTableDataSource<any>();

  requestForm: FormGroup;
  requestFields: string[];

  xrOptions: SelectItem[];

  errorMessages: any;

  step: number;

  @ViewChild('coordinates') coordinates!: ElementRef;
  public coordinatesCtx!: CanvasRenderingContext2D;
  @ViewChild('region') region!: ElementRef;
  public regionCtx!: CanvasRenderingContext2D;
  @ViewChild('dots') dots!: ElementRef;
  public dotsCtx!: CanvasRenderingContext2D;


  constructor(private fb: FormBuilder, private authService: AuthService, private mainService: MainService, private router: Router) {

    this.xrOptions = [
      { label: '-3', value: -3 },
      { label: '-2', value: -2 },
      { label: '-1', value: -1 },
      { label: '0', value: 0 },
      { label: '1', value: 1 },
      { label: '2', value: 2 },
      { label: '3', value: 3 },
      { label: '4', value: 4 },
      { label: '5', value: 5 },
    ];

    this.requestForm = this.fb.group({
      x: ['', [Validators.required]],
      y: ['', [Validators.required, Validators.min(-5), Validators.max(3)]],
      r:['', [Validators.required]]
    });

    this.requestFields = ['x', 'y', 'r', 'result', 'timestamp', 'owner'];

    this.step = 25;

  }

  ngAfterViewInit(): void {
    let ctx = (this.coordinates.nativeElement as HTMLCanvasElement).getContext('2d');
    if (ctx !== null) {
      this.coordinatesCtx = ctx;
    }
    ctx = (this.region.nativeElement as HTMLCanvasElement).getContext('2d');
    if (ctx !== null) {
      this.regionCtx = ctx;
    }
    ctx = (this.dots.nativeElement as HTMLCanvasElement).getContext('2d');
    if (ctx !== null) {
      this.dotsCtx = ctx;
    }

    this.drowCoordinates();

    
    

  }


  ngOnInit(): void {

    this.authService.status().subscribe(response => {

      this.mainService.getRequestsHistory().subscribe(data => {
        this.requestsHistoryData.data = data.reverse();
        if (this.requestsHistoryData.data.length > 0) {
          const req = this.requestsHistoryData.data[0]
          this.requestForm.setValue({x: Math.trunc(req.x), y: Math.trunc(req.y), r: Math.trunc(req.r)});
          const radius = this.requestForm.get('r')?.value;
          console.log(radius);
          if (radius != null) {
            this.drowRegion({value: radius});
          }
        }
      })

    })

    

  }

  onCreateRequest(): void {
    if (this.requestForm.valid) {
      const x = this.requestForm.get("x")?.value;
      const y = this.requestForm.get("y")?.value;
      const r = this.requestForm.get("r")?.value;

      this.mainService.createRequest(x, y, r).subscribe(
        (response) =>  {
          this.requestsHistoryData.data = [response, ...this.requestsHistoryData.data];
          this.errorMessages = null;
        },
        (error) => this.showError(error)
      )

    }
  }

  createRequestByClick(event: MouseEvent) {

    const canvas = this.dots.nativeElement as HTMLCanvasElement
    const rect = canvas.getBoundingClientRect();
    const x = (event.clientX - rect.left - 150)/this.step;
    const y = -(event.clientY - rect.top - 150)/this.step;
    const r = this.requestForm.get("r")?.value;

    if (r == undefined || r == null || r == '') {
      this.showError(new HttpErrorResponse({error: "Не установленно значение R."}));
      return;
    }

    this.mainService.createRequest(x, y, r).subscribe(
      (response) =>  {
        this.drowDot(response.x, response.y, response.result);
        this.requestsHistoryData.data = [response, ...this.requestsHistoryData.data];
        this.errorMessages = null;
      },
      (error) => this.showError(error)
    )

  }

  clearRequestsHistory() {
    this.mainService.clearRequestsHistory().subscribe(
      (response) => {
        this.requestsHistoryData.data = [];
      },
      (error) => this.showError(error)
    )
  }
  
  showError(error: HttpErrorResponse): void {
    this.errorMessages = error.error;
  }


  drowCoordinates() {

    const canvas = this.coordinates.nativeElement as HTMLCanvasElement;
    this.coordinatesCtx.beginPath();

    this.coordinatesCtx.moveTo(0, canvas.height / 2);
    this.coordinatesCtx.lineTo(canvas.width - 10, canvas.height / 2);
    this.coordinatesCtx.stroke();
    this.coordinatesCtx.lineTo(canvas.width - 10, canvas.height / 2 + 5);
    this.coordinatesCtx.lineTo(canvas.width, canvas.height / 2);
    this.coordinatesCtx.lineTo(canvas.width - 10, canvas.height / 2 - 5);
    this.coordinatesCtx.lineTo(canvas.width - 10, canvas.height / 2);
    this.coordinatesCtx.fillStyle = "black";
    this.coordinatesCtx.fill();
    this.coordinatesCtx.strokeText("X", canvas.width - 10, canvas.height / 2 - 15);

    this.coordinatesCtx.moveTo(canvas.width / 2 + 25, canvas.height / 2 + 5);
    this.coordinatesCtx.lineTo(canvas.width / 2 + 25, canvas.height / 2 - 5);
    this.coordinatesCtx.moveTo(canvas.width / 2 + 50, canvas.height / 2 + 5);
    this.coordinatesCtx.lineTo(canvas.width / 2 + 50, canvas.height / 2 - 5);
    this.coordinatesCtx.moveTo(canvas.width / 2 + 75, canvas.height / 2 + 5);
    this.coordinatesCtx.lineTo(canvas.width / 2 + 75, canvas.height / 2 - 5);
    this.coordinatesCtx.moveTo(canvas.width / 2 + 100, canvas.height / 2 + 5);
    this.coordinatesCtx.lineTo(canvas.width / 2 + 100, canvas.height / 2 - 5);
    this.coordinatesCtx.moveTo(canvas.width / 2 - 25, canvas.height / 2 + 5);
    this.coordinatesCtx.lineTo(canvas.width / 2 - 25, canvas.height / 2 - 5);
    this.coordinatesCtx.moveTo(canvas.width / 2 - 50, canvas.height / 2 + 5);
    this.coordinatesCtx.lineTo(canvas.width / 2 - 50, canvas.height / 2 - 5);
    this.coordinatesCtx.moveTo(canvas.width / 2 - 75, canvas.height / 2 + 5);
    this.coordinatesCtx.lineTo(canvas.width / 2 - 75, canvas.height / 2 - 5);
    this.coordinatesCtx.moveTo(canvas.width / 2 - 100, canvas.height / 2 + 5);
    this.coordinatesCtx.lineTo(canvas.width / 2 - 100, canvas.height / 2 - 5);
    this.coordinatesCtx.moveTo(canvas.width / 2 - 5, canvas.height / 2 + 25);
    this.coordinatesCtx.lineTo(canvas.width / 2 + 5, canvas.height / 2 + 25);
    this.coordinatesCtx.moveTo(canvas.width / 2 - 5, canvas.height / 2 + 50);
    this.coordinatesCtx.lineTo(canvas.width / 2 + 5, canvas.height / 2 + 50);
    this.coordinatesCtx.moveTo(canvas.width / 2 - 5, canvas.height / 2 + 75);
    this.coordinatesCtx.lineTo(canvas.width / 2 + 5, canvas.height / 2 + 75);
    this.coordinatesCtx.moveTo(canvas.width / 2 - 5, canvas.height / 2 + 100);
    this.coordinatesCtx.lineTo(canvas.width / 2 + 5, canvas.height / 2 + 100);
    this.coordinatesCtx.moveTo(canvas.width / 2 - 5, canvas.height / 2 - 25);
    this.coordinatesCtx.lineTo(canvas.width / 2 + 5, canvas.height / 2 - 25);
    this.coordinatesCtx.moveTo(canvas.width / 2 - 5, canvas.height / 2 - 50);
    this.coordinatesCtx.lineTo(canvas.width / 2 + 5, canvas.height / 2 - 50);
    this.coordinatesCtx.moveTo(canvas.width / 2 - 5, canvas.height / 2 - 75);
    this.coordinatesCtx.lineTo(canvas.width / 2 + 5, canvas.height / 2 - 75);
    this.coordinatesCtx.moveTo(canvas.width / 2 - 5, canvas.height / 2 - 100);
    this.coordinatesCtx.lineTo(canvas.width / 2 + 5, canvas.height / 2 - 100);
    this.coordinatesCtx.stroke();

    this.coordinatesCtx.moveTo(canvas.width / 2, canvas.height);
    this.coordinatesCtx.lineTo(canvas.width / 2, 10);
    this.coordinatesCtx.stroke();
    this.coordinatesCtx.lineTo(canvas.width / 2 + 5, 10);
    this.coordinatesCtx.lineTo(canvas.width / 2, 0);
    this.coordinatesCtx.lineTo(canvas.width / 2 - 5, 10);
    this.coordinatesCtx.lineTo(canvas.width / 2, 10);
    this.coordinatesCtx.fill();
    this.coordinatesCtx.strokeText("Y", canvas.width / 2 + 15, 10);
    this.coordinatesCtx.closePath();

  }

  drowRegion(event: any): void {

    const canvas = this.region.nativeElement as HTMLCanvasElement;

    const rval = event.value;

    if (rval < 0) {
      return;
    }

    this.regionCtx.clearRect(0, 0, canvas.width, canvas.height);

    var step = 25;
    this.regionCtx.beginPath();
    this.regionCtx.fillStyle = "rgba(0, 0, 255, 0.7)";

    this.regionCtx.moveTo(canvas.width / 2, canvas.height / 2);
    this.regionCtx.lineTo((canvas.width / 2) + (rval)*step, (canvas.height / 2));
    this.regionCtx.lineTo((canvas.width / 2) + (rval)*step, (canvas.height / 2) + (rval/2)*step);
    this.regionCtx.lineTo(canvas.width / 2, (canvas.height / 2) + (rval/2)*step);
    this.regionCtx.lineTo(canvas.width / 2, (canvas.height / 2));
    this.regionCtx.fill();
    this.regionCtx.closePath();

    this.regionCtx.beginPath();
    this.regionCtx.moveTo(canvas.width / 2, canvas.height / 2);
    this.regionCtx.lineTo(canvas.width / 2, canvas.height / 2 - rval*step/2);
    this.regionCtx.lineTo(canvas.width / 2 + (rval * step), canvas.height / 2);
    this.regionCtx.moveTo(canvas.width / 2, canvas.height / 2);
    this.regionCtx.fill();
    this.regionCtx.closePath();

    this.regionCtx.beginPath();
    this.regionCtx.arc(canvas.width / 2, canvas.height / 2, (rval/2) * step, Math.PI / 2, Math.PI );
    this.regionCtx.lineTo(canvas.width / 2, canvas.height / 2);
    this.regionCtx.lineTo(canvas.width / 2, canvas.height / 2 + (rval/2)*step);
    this.regionCtx.fill();
    this.regionCtx.closePath();

  }

  

  drowDot(x: number, y: number, result: boolean): void {

    const canvas = this.dots.nativeElement as HTMLCanvasElement;

    this.dotsCtx.clearRect(0, 0, canvas.width, canvas.height);
    this.dotsCtx.beginPath();
    this.dotsCtx.arc(canvas.width/2 + x*this.step, canvas.height/2 - y*this.step, 2, 0, 2 * Math.PI);
    
    if (result) {
        this.dotsCtx.fillStyle = "red";
    } else {
        this.dotsCtx.fillStyle = "green";
    }
    this.dotsCtx.fill();
    this.dotsCtx.closePath();

  }

}
