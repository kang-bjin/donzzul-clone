'use client';

import { useState } from 'react';
import Image from 'next/image';
import Header from '@/components/Header';
import BottomTab from '@/components/BottomTab';
import DonationHeaderCard from '@/components/DonationHeaderCard';
import DonationBodyText from '@/components/DonationBodyText';
import DonationModal from '@/components/modals/DonationModal';

const donation = {
  title: '국경없는 의사회',
  subtitle: '국제 의료 구호 단체',
  phone: '02-3703-3555',
  email: 'support@seoul.msf.org',
  favorites: 447,
  logo: '/donation_msf.jpg',
  bottomImage: '/donation_msf_bottom.png',
  summaryTitle: '“국경도, 정체도, 이념도 없이 오직 생명을 위해”',
  description: [
    '국경 없는 의사회는 가장 위험하고 의료가 닿기 어려운 곳에서 긴급 의료 지원 활동을 펼치는 국제 인도주의 단체입니다.',
    '• 무력 분쟁 속 부상자 치료',
    '• 난민 캠프 내 전염병 대응',
    '• 의료 인프라가 붕괴된 지역의 산모·아동 치료',
    '• 백신 접종, 응급 수술, 심리치료 등 폭넓은 의료 지원',
    '',
    '당신의 작은 기부가 생명을 살리는 큰 힘이 됩니다.',
    '국경 없는 의사회를 통해 지금, 전 세계 위기 속 생명을 위한 연대에 함께 해주세요.',
    '',
    '💛 기부금은 의료 장비, 백신, 의약품, 응급 구조 활동에 사용됩니다.💛',
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
            />

            {/* 이미지 */}
            <div className="mb-6 rounded-xl overflow-hidden shadow">
              <Image
                src={donation.bottomImage}
                alt="기부 활동 이미지"
                width={800}
                height={400}
                className="w-full h-auto object-cover"
              />
            </div>

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
