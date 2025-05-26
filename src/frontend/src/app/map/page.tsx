'use client'

import { useEffect, useRef, useState } from 'react'
import Script from 'next/script'

export default function KakaoMapDemo() {
  const mapRef = useRef<HTMLDivElement>(null)
  const [loaded, setLoaded] = useState(false)

  useEffect(() => {
    if (!loaded || !mapRef.current) return

    // 이제 `window.kakao` 는 로드돼 있고,
    // mapRef.current 는 <div id="map" /> 입니다.
    const container = mapRef.current
    const options = {
      center: new window.kakao.maps.LatLng(33.450701, 126.570667),
      level: 3,
    }
    new window.kakao.maps.Map(container, options)
  }, [loaded])

  return (
    <>
      {/* 1) SDK 불러오기 */}
      <Script
        strategy="afterInteractive"
        src={`//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.NEXT_PUBLIC_KAKAO_API_KEY}&autoload=false`}
        onLoad={() => window.kakao.maps.load(() => setLoaded(true))}
      />

      {/* 2) 이 div 가 mapContainer 역할 */}
      <div
        id="map"
        ref={mapRef}
        style={{ width: '100%', height: '400px' }}
      />
    </>
  )
}
