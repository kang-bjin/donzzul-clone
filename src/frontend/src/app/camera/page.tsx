'use client';

import React, { useEffect, useRef } from 'react';
import { useRouter } from 'next/navigation';

// 1. base64를 Blob으로 변환하는 함수
function dataURLtoBlob(dataurl:string): Blob{
  const arr = dataurl.split(',');
  const mime = arr[0].match(/:(.*?);/)![1];
  const bstr = atob(arr[1]);
  let n = bstr.length;
  const u8arr = new Uint8Array(n);
  while (n--) {
    u8arr[n] = bstr.charCodeAt(n);
  }
  return new Blob([u8arr], { type: mime });
}

const CameraScreen: React.FC = () => {
  const router = useRouter();
  const videoRef = useRef<HTMLVideoElement | null>(null);
  const canvasRef = useRef<HTMLCanvasElement | null>(null);

  // 2. 카메라 시작
  useEffect(() => {
  const startCamera = async () => {
    try {
      const stream = await navigator.mediaDevices.getUserMedia({ video: true });
      if (videoRef.current) {
        videoRef.current.srcObject = stream;
        videoRef.current
          .play()
          .catch((e) => {
            console.warn('video play interrupted:', e);
          });
      }
    } catch (err) {
      console.error('카메라 접근 오류:', err);
    }
  };

  startCamera();

  return () => {
    if (videoRef.current?.srcObject) {
      const tracks = (videoRef.current.srcObject as MediaStream).getTracks();
      tracks.forEach(track => track.stop());
    }
  };
}, []);


  // 3. 촬영 및 백엔드로 전송
  const takePhoto = async () => {
    const video = videoRef.current;
    const canvas = canvasRef.current;

    if (!video || !canvas) return;

    const width = video.videoWidth;
    const height = video.videoHeight;

    canvas.width = width;
    canvas.height = height;

    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    ctx.drawImage(video, 0, 0, width, height);

    const imageData = canvas.toDataURL('image/png');
    const imageBlob = dataURLtoBlob(imageData);
    const imageFile = new File([imageBlob], 'photo.png', { type: 'image/png' });

    // FormData로 묶어서 백엔드로 전송
    const formData = new FormData();
    formData.append('file', imageFile);

    // 필요하면 추가 데이터(예시)
    formData.append('rating', 5);
    formData.append('content', '캡처한 이미지');

    try {
      const response = await fetch('http://localhost:8080/api/receipt/process', {
        method: 'POST',
        body: formData,
        // headers: { ... } // 인증 필요하면 추가
      });

      if (response.ok) {
        const result = await response.json();
        alert('이미지 업로드 성공: ' + JSON.stringify(result));
        router.push('review')
      } else {
        alert('이미지 업로드 실패: ' + response.status);
      }
    } catch (err) {
      console.error('이미지 업로드 에러:', err);
    }
  };

  return (
    <div className="flex flex-col h-screen items-center justify-between">
      {/* 상단 바 */}
      <div className="w-full bg-[#FDDC55] min-h-[50px] relative z-10 flex items-center justify-center">
      </div>

      {/* 카메라 미리보기 */}
      <div className="flex-1 w-full bg-black flex items-center justify-center relative z-0">
        <video
          ref={videoRef}
          className="w-full h-full max-h-full object-cover"
          playsInline
          muted
        />
      </div>

      {/* 하단 바 + 촬영 버튼 */}
      <div className="w-full bg-[#FDDC55] h-30 flex items-center justify-center relative">
        <div className="w-16 h-16 bg-[#FDDC55] border border-black rounded-full flex items-center justify-center mt-2 mb-2">
          <div
            onClick={takePhoto}
            className="w-14 h-14 bg-white rounded-full border border-black active:scale-95 cursor-pointer"
          />
        </div>

        {/* 캡처용 canvas (숨김) */}
        <canvas ref={canvasRef} className="hidden" />
      </div>
    </div>
  );
};

export default CameraScreen;
