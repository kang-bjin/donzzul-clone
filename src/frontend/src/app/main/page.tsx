'use client';
import React, { useState } from 'react';
import Image from 'next/image';
import { useRouter, usePathname } from 'next/navigation';
import Container from '@/components/Container';
import CardWrapper from '@/components/CardWrapper';
import { FaStar } from 'react-icons/fa'
import BottomTab from '@/components/BottomTab';
import Header from '@/components/Header';


const categories = ['🍚 한식', '🥟 중식', '☕️ 카페', '+'];
const stores = [
  { id: 1, name: '팔팔식당', ex: '일본 가정식 맛집 & 선술집', rating: 4.6, reviews: 67, img: '/store1.png' },
  { id: 2, name: '6번지버거', ex: '육즙 가득 수제 햄버거집', rating: 4.6, reviews: 67, img: '/store2.png' },
];

const tabs = [
  { label: '착한 가게', path: '/main' },
  { label: '커뮤니티', path: '/community' },
  { label: '소비 인증', path: '/certify', center: true },
  { label: '지도', path: '/map' },
  { label: '기부하기', path: '/donate' },
];

export default function MainPage() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  

  return (
    <Container size="sm" bgColor="bg-white">
      <CardWrapper>
        {/* 헤더 */}
        <Header/>

        {/* 배너 */}
        <section className="w-full h-70 relative my-2 overflow-hidden rounded-lg">
          <Image src="/banners.png" alt="메인 배너" fill style={{ objectFit: 'cover' }} />
        </section>

        {/* 위치 안내 */}
        <div className="w-full">
          <h2 className="text-base font-bold">서울특별시 중구 필동,</h2>
          <p className="text-sm text-gray-600">내 주변 돈쭐 내줄 가게는 ?</p>
        </div>

        {/* 카테고리 버튼 */}
        <nav className="flex gap-1 my-3 w-full flex-wrap">
          {categories.map((cat) => (
            <button
              key={cat}
              className="px-4 py-1 border border-[#B5B5B5] text-sm rounded-full hover:bg-gray-100"
            >
              {cat}
            </button>
          ))}
        </nav>

        {/* 추천 섹션 제목 */}
        <h2 className="mt-4 mb-2 text-sm font-semibold w-full">
          다른 돈쭐러들은 이 가게를 돈쭐냈어요!
        </h2>

        {/* 가게 카드 리스트 */}
        <section className="overflow-x-scroll flex space-x-4 mb-4 w-full">
          {stores.map((store) => (
            <div key={store.id} className="w-40 flex-shrink-0 bg-white rounded-lg">
              <div className="w-full h-20 relative">
                <Image
                  src={store.img}
                  alt={store.name}
                  fill
                  style={{
                    objectFit: 'cover',
                    borderTopLeftRadius: '0.5rem',
                    borderTopRightRadius: '0.5rem',
                  }}
                />
              </div>
              <div className='flex items-center justify-between w-full' >
                <p className="text-[14px]">{store.name}</p>
                <div className="flex items-center gap-[3px] text-sm">
                  <FaStar className="text-[#FF7A00]" />
                  <span className="text-[#FF7A00] font-medium">{store.rating}</span>
                  <span className="text-gray-400">({store.reviews})</span>
                </div> 
              </div>
              <div className='text-[11px]'>
                {store.ex}
              </div>
            </div>
          ))}
        </section>

        {/* 하단 탭바 */}
       <BottomTab/>
      </CardWrapper>
    </Container>
  );
}
