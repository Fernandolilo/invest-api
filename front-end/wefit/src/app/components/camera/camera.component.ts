import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-camera',
  templateUrl: './camera.component.html',
  styleUrl: './camera.component.scss'
})
export class CameraComponent implements OnInit {
  @ViewChild('video') videoRef!: ElementRef<HTMLVideoElement>;
  @ViewChild('canvas') canvasRef!: ElementRef<HTMLCanvasElement>;
  capturedImage: string | null = null;

  ngOnInit(): void {
    this.startCamera();
  }

  startCamera(): void {
    navigator.mediaDevices.getUserMedia({ video: true })
      .then(stream => {
        const videoElement = this.videoRef.nativeElement;
        videoElement.srcObject = stream;
        videoElement.play();
      })
      .catch(err => {
        console.error('Erro ao acessar a câmera: ', err);
        alert('Não foi possível acessar a câmera.');
      });
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

}
