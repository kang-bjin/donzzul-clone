'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import PointModal from '@/components/modals/PointModal'
import Header from '@/components/Header'
import BottomTab from '@/components/BottomTab'

export default function VerifyPage() {
  const router = useRouter()
  const [category, setCategory] = useState('')
  const [isModalOpen, setIsModalOpen] = useState(false)

  const categoryOptions = ['음식점', '카페', '미용', '기타']

  return (
    <>
      <Header />

      <main className="min-h-screen bg-[#FFD735]/85 px-4 py-6 flex flex-col items-center rounded-[50px]">
        <div className="bg-white rounded-[40px] w-full max-w-md p-4 pb-20 shadow-md relative">
          {/* 사진 등록 */}
          <div className="w-full h-40 rounded-xl overflow-hidden mb-4 relative">
            <div className="relative h-40 bg-gray-200 flex items-center justify-center">
              <p className="text-gray-600">가게 사진 등록하기</p>
              {/* 닫기 버튼 */}
              <button
                onClick={() => router.back()}
                className="absolute top-2 right-2 bg-black/40 text-white w-8 h-8 rounded-full flex items-center justify-center"
              >
                ✕
              </button>
            </div>
          </div>

          {/* 입력 폼 */}
          <div className="space-y-6 p-1 rounded-xl">
            {/* 가게 이름 */}
            <div>
              <p className="border-l-4 border-yellow-400 pl-2 text-gray-800 font-bold text-base">
                제보할 가게 이름을 적어주세요
              </p>
              <input
                type="text"
                placeholder="가게 이름 입력"
                className="w-full rounded-md bg-gray-100 p-3 mt-3"
              />
            </div>

            {/* 업종 선택 */}
            <div>
              <p className="border-l-4 border-yellow-400 pl-2 text-gray-800 font-bold text-base">
                가게 업종에 대해 알려주세요
              </p>
              <select
                className="w-full rounded-md bg-gray-100 p-3 mt-3"
                value={category}
                onChange={(e) => setCategory(e.target.value)}
              >
                <option value="">클릭해서 업종을 선택하세요</option>
                {categoryOptions.map((opt) => (
                  <option key={opt} value={opt}>
                    {opt}
                  </option>
                ))}
              </select>
            </div>

            {/* 돈쭐 이유 */}
            <div>
              <p className="border-l-4 border-yellow-400 pl-2 text-gray-800 font-bold text-base">
                돈쭐 내줘야 하는 이유를 알려주세요
              </p>
              <textarea
                placeholder="사장님의 어떤 선행이 기억에 남았나요?"
                maxLength={500}
                className="w-full bg-gray-100 p-3 rounded-md h-32 resize-none mt-3"
              />
            </div>

            {/* 소비 후기 */}
            <div>
              <p className="border-l-4 border-yellow-400 pl-2 text-gray-800 font-bold text-base">
                소비 후기를 작성해주세요
              </p>
              <textarea
                placeholder="구매하신 상품/서비스는 만족스러우셨나요?"
                className="w-full bg-gray-100 p-3 rounded-md h-28 resize-none mt-3"
              />
            </div>
             {/* 하단 버튼 */}
            <div className="mt-15">
              <button
                onClick={() => setIsModalOpen(true)}
                className="w-full bg-[#FFC300] text-white font-bold py-4 rounded-full shadow-lg"
              >
            등록하기
          </button>
          </div>
          </div> {/* ✅ 입력폼 전체 div 닫음 */}
        </div> {/* ✅ 바깥 카드 div 닫음 (기존 누락됐던 부분) */}

       

        <BottomTab />

        {/* 모달 */}
        <PointModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} />
      </main>
    </>
  )
}
