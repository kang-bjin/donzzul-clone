'use client';
import { useRouter, usePathname } from 'next/navigation';
import { useState } from 'react';
import LocationPermissionModal from '@/components/modals/LocationPermissionModal';

interface TabItem {
  label: string;
  path: string;
  center?: boolean;
}

const tabs: TabItem[] = [
  { label: '착한 가게', path: '/main' },
  { label: '커뮤니티', path: '/community' },
  { label: '소비 인증', path: '/certify', center: true },
  { label: '지도', path: '/map' },
  { label: '기부하기', path: '/donate' },
];

export default function BottomTab() {
  const router = useRouter();
  const pathname = usePathname();
  const [openModal, setOpenModal] = useState(false);

  return (
    <>
     <footer className="w-full py-2 flex justify-around">
          {tabs.map((tab, index) => {
            const isActive = pathname === tab.path;
            const isCenter = tab.center;

            const handleClick = () => {
            if (isCenter) {
              setOpenModal(true); // ✅ 모달 열기
            } else if (tab.path) {
              router.push(tab.path);
            }
          };
            return (
              <button
                key={index}
                onClick={handleClick}
                className={`flex flex-col items-center w-14 ${
                    tab.center ? '-mt-3' : ''
                          }`}
              >
                <div
                  className={`mb-1 rounded-full flex items-center justify-center ${
                    isCenter
                      ? 'bg-[#FFD735] text-white w-12 h-12'
                      :isActive
                      ? 'bg-[#FFD735] w-6 h-6'
                      : 'bg-[#CFCFCF] w-6 h-6'
                  }`}
                >
                  {tab.center && (
                    <span className="text-4xl leading-none font-bold">+</span>
                  )}
                </div>
                <span className={`text-[11px] ${isActive ? 'text-black' : 'text-black'}`}>
                    {tab.label}
                 </span>
              </button>
            );
          })}
    </footer>
    {/* ✅ 위치 권한 모달 */}
    <LocationPermissionModal isOpen={openModal} onClose={() => setOpenModal(false)} />
    </>
  );
}
