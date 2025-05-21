'use client';

import React, { useEffect, useRef } from 'react';

const CameraScreen: React.FC = () => {
  const videoRef = useRef<HTMLVideoElement | null>(null);
  const canvasRef = useRef<HTMLCanvasElement | null>(null);

  // 1. 카메라 시작
  useEffect(() => {
    const startCamera = async () => {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({ video: true });
        if (videoRef.current) {
          videoRef.current.srcObject = stream;
          await videoRef.current.play();
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

  // 2. 촬영 및 OCR 전송
  const takePhoto = () => {
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

  // ✅ 지금은 콘솔에만 출력 (백엔드 요청 X)
  console.log('📸 캡처한 이미지 base64:', imageData);

  // 👉 여기에 이미지 미리보기 기능 추가할 수도 있음
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
