'use client';

import { useCharacterStore } from '@/store/characterStore';
import { useEffect, useState } from 'react';
import Image from 'next/image';
import BottomTab from '@/components/BottomTab';
import Header from '@/components/Header';
import SectionTitle from '@/components/SectionTitle';
import { motion, AnimatePresence } from 'framer-motion';
import { useRouter } from 'next/navigation';

export default function DonationPage() {
  const { name, points, activityCount, updateActivity } = useCharacterStore();
  const [balloonText, setBalloonText] = useState('');
  const [hamsterImage, setHamsterImage] = useState('/donation_hamster.png');
  const [imageKey, setImageKey] = useState(0);
  const router = useRouter();

  useEffect(() => {
    const balloons = [
      '오늘 하루는 어때?',
      '나 배고파ㅜㅜ',
      '심심하다!',
      '오늘 날씨 되게 좋다~!',
    ];
    const random = Math.floor(Math.random() * balloons.length);
    setBalloonText(balloons[random]);
  }, []);

  const iconTypes = ['meal', 'exercise', 'sleep'] as const;
  const iconMap = {
    meal: '🌰',
    exercise: '⚡',
    sleep: '🎮',
  };
  const labelMap = {
    meal: '밥 주기',
    exercise: '달리기',
    sleep: '놀아주기',
  };
  const imageMap = {
    meal: '/(누끼)햄스터_밥뚱.png',
    exercise: '/운동햄스터.png',
    sleep: '/게임햄스터.png',
  };

  const handleAction = (type: 'meal' | 'exercise' | 'sleep') => {
    if (activityCount[type] >= 3) {
      alert('오늘은 더 못해요!');
      return;
    }

    setHamsterImage(imageMap[type]);
    setImageKey(prev => prev + 1);

    setTimeout(() => {
      setHamsterImage('/donation_hamster.png');
      setImageKey(prev => prev + 1);
    }, 2000);

    updateActivity(type);
  };

  const handleDonate = () => {
    if (points >= 10000) {
      router.push('/donation/list');
    } else {
      alert('최소 만 포인트부터 기부가 가능해요!');
    }
  };

  return (
    <div className="flex flex-col min-h-screen bg-white w-full pb-24 overflow-x-hidden relative">
      {/* 배경 원 */}
      <div
        className="absolute left-1/2 top-[171px] w-full max-w-[592px] h-[91.63%] z-0 pointer-events-none -translate-x-1/2"
        style={{
          background:
            'radial-gradient(50% 50% at 50% 50%, #FFD6D6 0%, rgba(255, 214, 214, 0.74) 29%, rgba(224, 77, 77, 0) 100%)',
        }}
      />

      {/* 콘텐츠 */}
      <div className="flex-grow w-full relative z-10 px-4 pt-0">
        <Header />
        <SectionTitle text="기부하기" />

        {/* 말풍선 */}
        <motion.div
          className="mt-6 bg-[#FFF7F7] text-center text-sm sm:text-base text-gray-800 rounded-full px-6 py-4 mb-10 shadow-md w-full"
          initial={{ opacity: 0, y: -10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5 }}
        >
          {balloonText}
        </motion.div>

        {/* 햄스터 이미지 */}
        <div className="relative flex justify-center items-center mb-2">
          <div
            className="absolute w-72 h-72 rounded-full -z-10"
            style={{
              background:
                'radial-gradient(50% 50% at 50% 50%, #FFD6D6 0%, rgba(255, 214, 214, 0.74) 40%, rgba(224, 77, 77, 0) 100%)',
            }}
          />
          <div className="w-44 h-44 rounded-full bg-white flex items-center justify-center shadow-md overflow-hidden p-2">
            <AnimatePresence mode="wait">
              <motion.div
                key={imageKey}
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                transition={{ duration: 0.3 }}
              >
                <Image
                  src={hamsterImage}
                  alt="hamster"
                  width={90}
                  height={90}
                  priority
                  style={{ width: 'auto', height: 'auto' }}
                />
              </motion.div>
            </AnimatePresence>
          </div>
        </div>

        {/* 이름, 포인트 */}
        <div className="flex flex-col items-center gap-2 mb-8">
          <div className="bg-[#FFE5E3] px-4 py-1 rounded-full text-sm font-medium text-gray-700">
            🐹 {name} 🐹
          </div>
          <div className="bg-[#FF9F9F] px-4 py-1 rounded-full text-xl font-bold text-white">
            {points.toLocaleString()} pt
          </div>
        </div>

        {/* 아이콘 상태 표시 */}
        <div className="w-full bg-white rounded-xl shadow-sm p-4 flex justify-between items-center mb-5">
          {iconTypes.map((type) => (
            <div key={type} className="flex gap-1">
              {[0, 1, 2].map((i) => (
                <span
                  key={i}
                  className={
                    activityCount[type] > i
                      ? 'opacity-100 text-xl'
                      : 'opacity-30 text-xl'
                  }
                >
                  {iconMap[type]}
                </span>
              ))}
            </div>
          ))}
        </div>

        {/* 액션 버튼 */}
        <div className="flex justify-center gap-12 lg:gap-105 w-full mb-15">
          {iconTypes.map((type) => (
            <motion.button
              key={type}
              onClick={() => handleAction(type)}
              whileTap={{ scale: 0.9 }}
              className="bg-[#FFE8A3] text-gray-800 text-xs px-4 py-2 rounded-full shadow font-semibold whitespace-nowrap text-center"
            >
              {labelMap[type]}
            </motion.button>
          ))}
        </div>

        {/* 기부하기 버튼 */}
        <div className="w-full ">
          <motion.button
            onClick={handleDonate}
            whileHover={{ scale: 1.03 }}
            whileTap={{ scale: 0.95 }}
            className="w-full bg-white hover:bg-yellow-500 transition text-[#262626] font-bold py-3 rounded-xl text-center shadow"
          >
            기부하기
          </motion.button>
        </div>
      </div>

      {/* 바텀탭 */}
      <div className="bg-white w-full">
        <BottomTab />
      </div>
    </div>
  );
}
