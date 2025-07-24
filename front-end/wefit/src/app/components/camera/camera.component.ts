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

  cameraStatus = false;


  ngOnInit(): void {
    this.startCamera();
  }

  ngOnDestroy(): void {
    this.stopCamera();
  }
  onCamera(): void {
    this.cameraStatus = !this.cameraStatus;

    if (this.cameraStatus) {
      this.startCamera();
    } else {
      this.stopCamera();
      this.capturedImage = null;
    }
  }


  startCamera(): void {
    if (this.cameraStatus == true) {
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

    const videoWidth = video.videoWidth;
    const videoHeight = video.videoHeight;

    const cropWidth = videoWidth * 0.35;
    const cropHeight = videoHeight * 0.57;
    const cropX = (videoWidth - cropWidth) / 2;
    const cropY = (videoHeight - cropHeight) / 2;

    canvas.width = cropWidth;
    canvas.height = cropHeight;

    const context = canvas.getContext('2d');
    if (context) {
      context.clearRect(0, 0, canvas.width, canvas.height);

      // Máscara oval com fundo transparente
      context.save(); // Salva o contexto antes do recorte
      context.beginPath();
      context.ellipse(
        cropWidth / 2, cropHeight / 2,
        cropWidth / 2, cropHeight / 2,
        0, 0, 2 * Math.PI
      );
      context.clip(); // aplica a máscara oval

      // Desenha o vídeo recortado na região oval
      context.drawImage(
        video,
        cropX, cropY,
        cropWidth, cropHeight,
        0, 0,
        cropWidth, cropHeight
      );
      context.restore(); // Restaura o contexto

      // Captura como PNG (transparente fora da máscara)
      this.capturedImage = canvas.toDataURL('image/png');
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
