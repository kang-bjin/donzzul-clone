'use client'

import { useState } from 'react'
import Header from '@/components/Header'
import BottomTab from '@/components/BottomTab'
import { FiChevronRight, FiPlus } from 'react-icons/fi'
import { BiLike } from "react-icons/bi";
import { BiDislike } from "react-icons/bi";
import { LiaCommentDots } from "react-icons/lia";
import { useRouter, usePathname } from 'next/navigation'
import { FaStar, FaRegStar } from 'react-icons/fa'
import Image from 'next/image'
import Link from 'next/link'

export default function Store() {
  const router = useRouter()

interface ReviewPost {
  id: number
  title: string
  excerpt?: string
  date: string
  likes: number
  dislikes : number
  comments: number
  rating: number // 별점
  thumbnailUrl?: string
  thumbnails: string[]
}
const dummyReview: ReviewPost[] = [
  {
      id: 1,
      title: '선행하는 빵 맛집 발견했어요~!',
      excerpt : '짱맛 말 모 말 모 튀김 소보루 삼백 개 사감 ㅅㄱ',
      date: '2025-04-10',
      likes: 105,
      dislikes : 3,
      comments: 24,
      rating: 5,
      thumbnailUrl: '/store.jpg',
      thumbnails: ['/성심당3.jpg','/성심당2.jpg','/성심당.jpg'],
  },
]
  return (
    <>
      <Header />
     <div className="px-4">
      {/* ✅ 스크롤 콘텐츠 영역 */}
      <main className="min-h-screen bg-[#FFD735]/85 px-4 py-6 flex flex-col items-center rounded-[50px]">
        <div className="bg-white rounded-[40px] w-full max-w-md p-4 pb-20 shadow-md relative">
          {/* 이미지 + 닫기 */}
          <div className="w-full h-40 rounded-xl overflow-hidden mb-4 relative">
            <Image src="/성심당.jpg" alt="가게 이미지" fill className="object-cover" />
            <button
              onClick={() => router.back()}
              className="absolute top-2 right-2 bg-black/40 text-white w-8 h-8 rounded-full flex items-center justify-center"
            >
              ✕
            </button>
          </div>

          {/* 가게 정보 */}
          <div className="mb-6">
            <div className='flex items-center gap-8'>
                <span className="font-bold text-[25px] ">성심당 본점</span>
                <span className="text-[17px]">베이커리</span>
            </div>    
              <p className="text-left text-[15px] text-[#747483]">대전광역시 중구 대종로480번길 15</p>
              <p className="text-left text-[15px] text-[#747483]">영업시간 : 오전 8:00 ~ 오후 10:00</p>
              <div className="flex justify-between text-[15px] text-[#747483]">
                <p>전화번호 : 1588-8069</p>
                <div className='flex'>
                  <p className="text-blue-500">⭐ 4.5</p>
                  <p className='text-[#747483]'>(447)</p>
                </div>
              </div>
            </div>
      {/* 탭 */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {dummyReview.map((p) => (
            <Link
            key={p.id}
            href={`/store/${p.id}`}
            className="relative border border-gray-200 rounded-lg p-4 pb-16 shadow-xl bg-white"
            >
            {/* 별점 + 제목 */}
                <div>
                  <div className="flex items-center mb-1">
                     {Array.from({ length: 5 }).map((_, i) => {
                        // i < p.rating 이면 채워진 별, 아니면 빈 별
                        return i < p.rating ? (
                        <FaStar key={i} className="w-4 h-4 text-yellow-400" />
                        ) : (
                        <FaRegStar key={i} className="w-4 h-4 text-gray-300" />
                        )
                    })}
                    </div>
                     {/* 썸네일 3개 */}
                    <div className="flex gap-1 mt-2 mb-4">
                        {p.thumbnails.map((src, idx) => (
                        <div key={idx} className="relative h-10 w-10 rounded-md overflow-hidden">
                            <Image
                            src={src}
                            alt={`thumb-${idx}`}
                            fill
                            className="object-cover"
                            />
                        </div>
                        ))}
                    </div>
                {/* 제목 */}
                  <h3 className="font-semibold text-gray-800 line-clamp-2">{p.title}</h3>
                </div>    

            {/* 본문 */}
            <p className="text-sm text-gray-600 mt-2 line-clamp-2">{p.excerpt}</p>

            {/* 날짜: 오른쪽 상단 */}
            <span className="absolute top-4 right-5 text-[15px] text-gray-500">
                {p.date}
            </span>

            {/* 좋아요/싫어요: 왼쪽 하단 */}
            <div className="absolute bottom-4 left-4 flex items-center space-x-2 text-xs text-gray-500">
                <button className="flex items-center gap-1 hover:text-blue-500">
                <BiLike className='w-4 h-4'/> {p.likes}
                </button>
                <button className="flex items-center gap-1 hover:text-red-500">
                <BiDislike className='w-4 h-4'/> {p.dislikes}
                </button>
            </div>

            {/* 코멘트 수: 오른쪽 하단 */}
            <span className="absolute bottom-4 right-4 flex items-center gap-1 text-xs text-gray-500">
                <LiaCommentDots className='w-4 h-4'/> {p.comments}
            </span>
            </Link>

        ))}
        </div>
          

          
       
        </div>
      </main>
      
      {/* ✅ 하단탭 */}
      <BottomTab />
    
  </div> 
  </> 
  )
}