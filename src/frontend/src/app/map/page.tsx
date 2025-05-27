'use client';

import { useEffect, useRef, useState } from 'react';
import Script from 'next/script';
import Header from '@/components/Header';
import SectionTitle from '@/components/SectionTitle';
import BottomTab from '@/components/BottomTab';

export default function MapPage() {
  const mapRef = useRef<HTMLDivElement>(null);
  const [loaded, setLoaded] = useState(false);
  const [map, setMap] = useState<any>(null);
  const [keyword, setKeyword] = useState('');
  const [currentPosition, setCurrentPosition] = useState<{ lat: number; lng: number } | null>(null);
  const [category, setCategory] = useState<string>('');
  const [markers, setMarkers] = useState<any[]>([]);

  const infoWindowRef = useRef<any>(null);

  useEffect(() => {
    if (!loaded || !mapRef.current) return;

    navigator.geolocation.getCurrentPosition(
      (position) => {
        const lat = position.coords.latitude;
        const lng = position.coords.longitude;
        const center = new window.kakao.maps.LatLng(lat, lng);
        const createdMap = new window.kakao.maps.Map(mapRef.current, {
          center,
          level: 5,
        });
        setMap(createdMap);
        setCurrentPosition({ lat, lng });
      },
      () => {
        const fallback = new window.kakao.maps.LatLng(37.5665, 126.9780);
        const createdMap = new window.kakao.maps.Map(mapRef.current, {
          center: fallback,
          level: 5,
        });
        setMap(createdMap);
      }
    );
  }, [loaded]);

  useEffect(() => {
    if (!map) return;
    window.kakao.maps.event.addListener(map, 'click', () => {
      if (infoWindowRef.current) infoWindowRef.current.close();
    });
  }, [map]);

  const clearMarkers = () => {
    markers.forEach((m) => m.setMap(null));
    setMarkers([]);
  };

  const handleSearch = () => {
    if (!map || !keyword.trim()) return;

    const ps = new window.kakao.maps.services.Places();
    ps.keywordSearch(keyword, (data: any, status: any) => {
      if (status !== window.kakao.maps.services.Status.OK) return;
      clearMarkers();

      const newMarkers = data.map((place: any) => {
        const pos = new window.kakao.maps.LatLng(place.y, place.x);
        const marker = new window.kakao.maps.Marker({
          map,
          position: pos,
          image: getMarkerImage(),
        });

        if (!infoWindowRef.current) {
          infoWindowRef.current = new window.kakao.maps.InfoWindow({ zIndex: 1 });
        }

        window.kakao.maps.event.addListener(marker, 'click', () => {
          infoWindowRef.current.setContent(
            `<div style="padding:5px;font-size:12px;">${place.place_name}</div>`
          );
          infoWindowRef.current.open(map, marker);
        });

        return marker;
      });

      setMarkers(newMarkers);

      const bounds = new window.kakao.maps.LatLngBounds();
      newMarkers.forEach((m) => bounds.extend(m.getPosition()));
      map.setBounds(bounds);
    });
  };

  const getMarkerImage = () => {
    return new window.kakao.maps.MarkerImage(
      '/icon/default_marker_yellow.png',
      new window.kakao.maps.Size(32, 32)
    );
  };

  const handleCategoryClick = (cat: string) => {
    setCategory(cat === category ? '' : cat);
  };

  const moveToCurrentLocation = () => {
    if (map && currentPosition) {
      const pos = new window.kakao.maps.LatLng(currentPosition.lat, currentPosition.lng);
      map.setCenter(pos);
    }
  };

  return (
    <div className="flex flex-col min-h-screen bg-white w-full pb-24 overflow-x-hidden relative">
      <Script
        strategy="afterInteractive"
        src={`//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.NEXT_PUBLIC_KAKAO_API_KEY}&autoload=false&libraries=services`}
        onLoad={() => window.kakao.maps.load(() => setLoaded(true))}
      />

      <div className="flex-grow w-full relative z-10 pt-0">
        <Header />

        <div className="px-4">
          <div className="flex justify-between items-center">
            <SectionTitle text="지도" />
            <div className="ml-auto">
              <button
                onClick={moveToCurrentLocation}
                className="flex items-center gap-1 text-xs font-semibold px-4 py-2 border border-yellow-300 bg-yellow-100 text-yellow-800 rounded-full shadow-sm whitespace-nowrap"
              >
                📍 현재 위치로 이동
              </button>
            </div>
          </div>

          {/* 검색창 */}
          <div className="flex items-center gap-2 mt-5">
            <input
              type="text"
              value={keyword}
              onChange={(e) => setKeyword(e.target.value)}
              placeholder="어떤 가게를 찾아볼까요?"
              className="flex-grow border border-gray-300 rounded-full px-4 py-2 text-sm"
            />
            <button
              onClick={handleSearch}
              className="bg-yellow-400 text-white px-4 py-2 rounded-full text-sm font-semibold"
            >
              검색
            </button>
          </div>

          {/* 카테고리 필터 */}
          <div className="flex flex-wrap gap-2 mt-4 justify-between">
            {["음식점", "의료시설", "스포츠", "생활", "교육"].map((cat) => (
              <button
                key={cat}
                onClick={() => handleCategoryClick(cat)}
                className={`px-3 py-1 rounded-full text-xs font-semibold whitespace-nowrap ${
                  category === cat ? 'bg-orange-400 text-white' : 'bg-yellow-100 text-gray-800'
                }`}
              >
                {cat}
              </button>
            ))}
          </div>

          {/* 지도 */}
          <div ref={mapRef} className="w-full h-[550px] mt-4 rounded-lg border" />
        </div>
      </div>

      <div className="bg-white w-full">
        <BottomTab />
      </div>
    </div>
  );
}
