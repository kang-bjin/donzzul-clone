// components/NearbySection.tsx
'use client';

import Image from 'next/image';
import Link from 'next/link'

const dummyNearbyStores = [
  { id: 1, name: '필동 서점', description: '우리동네 따뜻한 서점', img: '/서점.jpg' },
  { id: 2, name: '필동 세탁소', description: '행복을 빨래해요', img: '/세탁소.png' },
  { id: 3, name: '진짜 파스타', description: '진심을 담아 요리해요', img: '/파스타집.jpg' },
];

const dummyNewStores = [
  { id: 4, name: '성심당', description: '마음을 먹어요', img: '/성심당.jpg' },
  { id: 5, name: '부산 살롱', description: '마음씨처럼 손도 예뻐', img: '/네일가게.jpg' },
  { id: 6, name: '진짜 파스타', description: '진심을 담아 요리해요', img: '/파스타집.jpg' },
];

export default function NearbySection() {
  return (
    <div className="w-full p-4 ">
      {/* 내 주변 */}
      <section className="self-start">
        <h2 className="text-lg font-bold mb-2">
          <span className="text-[#FFD735]">|</span> 내 주변 돈쭐 가게
        </h2>
        {dummyNearbyStores.map((store) => (
          <div key={store.id} className="flex items-center mb-4">
            <div className="w-[70px] h-[70px] relative flex-shrink-0 rounded-lg overflow-hidden">
              <Image src={store.img} alt={store.name} fill style={{ objectFit: 'cover' }} />
            </div>
            <div className="ml-3 flex flex-col flex-grow">
              <p className="text-[15px] font-bold">{store.name}</p>
              <p className="text-[11px] font-bold text-[#A3A3A3]">{store.description}</p>
            </div>
            <button className="ml-15 bg-[#FFD735] text-sm px-5 py-2 rounded-full">more</button>
          </div>
        ))}
      <Link
        href={`/nearby_donjjul`}
        className="w-full"      // Link에도 너비 꽉 채우기
      >
        <button className="flex justify-center w-full">
          <p className="text-center text-[13px] font-bold -mt-2">
            더보기
          </p>
        </button>
      </Link>
      </section>

      {/* 신규 */}
      <section className="self-start mt-6 mb-17">
        <h2 className="text-lg font-bold mb-2">
          <span className="text-[#FFD735]">|</span> 신규 돈쭐 가게
        </h2>
        {dummyNewStores.map((store) => (
          <div key={store.id} className="flex items-center mb-4">
            <div className="w-[70px] h-[70px] relative flex-shrink-0 rounded-lg overflow-hidden">
              <Image src={store.img} alt={store.name} fill style={{ objectFit: 'cover' }} />
            </div>
            <div className="ml-3 flex flex-col flex-grow">
              <p className="text-[15px] font-bold">{store.name}</p>
              <p className="text-[11px] font-bold text-[#A3A3A3]">{store.description}</p>
            </div>
            <button className="ml-15 bg-[#FFD735] text-sm px-5 py-2 rounded-full">more</button>
          </div>
        ))}
        <Link
        href={`/nearby_new`}
        className="w-full"      // Link에도 너비 꽉 채우기
      >
        <button className="flex justify-center w-full">
          <p className="text-center text-[13px] font-bold -mt-2">
            더보기
          </p>
        </button>
      </Link>
      </section>
    </div>
  );
}
