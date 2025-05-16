'use client';
import { useRouter } from 'next/navigation';
import { PiUserCircleFill } from "react-icons/pi";

export default function Header() {
  const router = useRouter();

  return (
    <header className="flex justify-between items-center w-full py-4">
      {/* 왼쪽: 타이틀 */}
      <div className="flex items-baseline gap-2">
        <h1 className="text-[25px] font-bold">돈쭐</h1>
        <p className="text-[15px] font-bold">: 소비에 선행을 더하다</p>
      </div>

      {/* 오른쪽: 마이페이지 아이콘 */}
      <button onClick={() => router.push('/mypage')}>
        <PiUserCircleFill className="w-6 h-6 text-[#49454F]" />
      </button>
    </header>
  );
}
