import { Component, ElementRef, EventEmitter, OnInit, Output, ViewChild, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-camera',
  templateUrl: './camera.component.html',
  styleUrl: './camera.component.scss'
})
export class CameraComponent implements OnInit, OnDestroy {
  @ViewChild('video') videoRef!: ElementRef<HTMLVideoElement>;
  @ViewChild('canvas') canvasRef!: ElementRef<HTMLCanvasElement>;

  @Output() selfieCaptured = new EventEmitter<string>();

  capturedImage: string | null = null;
  private stream?: MediaStream;

  ngOnInit(): void {
    this.startCamera();
  }

  ngOnDestroy(): void {
    this.stopCamera();
  }

  startCamera(): void {
    navigator.mediaDevices.getUserMedia({ video: true })
      .then(stream => {
        this.stream = stream;
        const videoElement = this.videoRef.nativeElement;
        videoElement.srcObject = stream;
        videoElement.play();
      })
      .catch(err => {
        console.error('Erro ao acessar a câmera: ', err);
        alert('Não foi possível acessar a câmera.');
      });
  }

  stopCamera(): void {
    if (this.stream) {
      this.stream.getTracks().forEach(track => track.stop());
      this.stream = undefined;
    }
  }

  capture(): void {
    const video = this.videoRef.nativeElement;
    const canvas = this.canvasRef.nativeElement;

    canvas.width = video.videoWidth;
    canvas.height = video.videoHeight;

    const context = canvas.getContext('2d');
    if (context) {
      context.drawImage(video, 0, 0, canvas.width, canvas.height);
      this.capturedImage = canvas.toDataURL('image/jpeg');
    }
  }

  confirm(): void {
    if (this.capturedImage) {
      this.selfieCaptured.emit(this.capturedImage);
      this.stopCamera();
    }
  }


  retake(): void {
    this.capturedImage = null;
    this.startCamera();
  }
}
