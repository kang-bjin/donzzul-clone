'use client';

import { useState } from 'react';
import Header from '@/components/Header';
import BottomTab from '@/components/BottomTab';
import DonationHeaderCard from '@/components/DonationHeaderCard';
import DonationBodyText from '@/components/DonationBodyText';
import DonationModal from '@/components/modals/DonationModal';

const donation = {
  title: '산불 이재민 기부',
  subtitle: '구호 성금 전달',
  phone: '1544-9595',
  email: ' kdra@hopebridge.or.kr',
  favorites: 328,
  logo: '/firevictims.png',
  bottomImage: '/donation_msf_bottom.png',
  summaryTitle: '“잿더미 속에서도, 다시 일어설 희망을 위해”',
  description: [
    '희망브리지는 갑작스러운 산불로 삶의 터전을 잃은 이재민들을 돕기 위해 긴급 지원을 펼치는 사단법인 전국재해구호협회입니다.',
    '• 산불 피해 지역에 구호물품 및 생필품 긴급 지원',
    '• 임시 거주지 제공 및 주거 복구 지원',
    '• 트라우마 치유를 위한 심리 상담 및 재활 프로그램',
    '• 복구 활동에 참여한 자원봉사자 안전 지원',
    '',
    '작은 손길이 모여, 삶을 다시 세울 수 있습니다.',
    '희망브리지를 통해 산불 피해 이웃들의 일상 회복을 함께 응원해주세요.',
    '',
    '💛 기부금은 생필품, 거주지 복구, 의료 지원, 심리 상담 등에 사용됩니다.💛',
  ],
};

export default function DonationDetailPage() {
  const [showModal, setShowModal] = useState(false);

  return (
    <div className="bg-white min-h-screen pb-28">
      <Header />

      <div className="w-full px-4 sm:px-6 pt-4 max-w-xl mx-auto">
        {/* 핑크 배경 */}
        <div className="bg-[#FFF4F4] rounded-2xl px-4 py-6">
          {/* 흰색 카드 영역 */}
          <div className="bg-white rounded-xl px-4 py-6 shadow-md w-full">
            {/* 헤더 카드 삽입 */}
            <DonationHeaderCard
              title={donation.title}
              subtitle={donation.subtitle}
              phone={donation.phone}
              email={donation.email}
              favorites={donation.favorites}
              logo={donation.logo}
            />

            {/* 구분선 */}
            <hr className="my-6 border-t border-gray-200" />

            {/* 본문 */}
            <DonationBodyText
              summaryTitle={donation.summaryTitle}
              description={donation.description}
              bottomImage={donation.bottomImage}
            />

            {/* 버튼 */}
            <div className="w-full">
              <button 
              onClick={() => setShowModal(true)}
              className="w-full bg-white text-black text-base font-semibold py-3 rounded-xl border border-gray-300 shadow-md">
                기부하기
              </button>

              <DonationModal isOpen={showModal} onClose={() => setShowModal(false)} />
            </div>
          </div>
        </div>
      </div>

      <BottomTab />
    </div>
  );
}
