'use client';

import Image from 'next/image';

interface HeaderProps {
  title: string;
  subtitle: string;
  phone: string;
  email: string;
  rating: number;
  reviews: number;
  logo: string;
}

export default function DonationHeaderCard({
  title,
  subtitle,
  phone,
  email,
  rating,
  reviews,
  logo,
}: HeaderProps) {
  return (
    <div className="flex flex-col w-full gap-4">
      {/* 로고 */}
      <div className="flex justify-center">
        <Image
          src={logo}
          alt={title}
          width={160}
          height={100}
          className="object-contain mb-2"
        />
      </div>

      {/* 제목 + 부제목 */}
      <div className="flex items-baseline gap-x-2">
        <h1 className="text-lg lg:text-2xl font-bold text-[#26262C]">{title}</h1>
        <p className="text-sm lg:text-base text-[#3D3D3D]">{subtitle}</p>
      </div>

      {/* 후원문의 + 평점 */}
      <div className="flex justify-between items-center text-xs lg:text-sm text-gray-600">
        <p>후원 문의 : {phone}</p>
        <div className="flex items-center gap-1">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="#3578FF"
            viewBox="0 0 24 24"
            className="w-4 h-4"
          >
            <path d="M12 .587l3.668 7.568 8.332 1.151-6.064 5.837 1.48 8.282L12 18.896l-7.416 4.529 1.48-8.282-6.064-5.837 8.332-1.151z" />
          </svg>
          <span className="text-[#3578FF]">{rating}</span>
          <span>({reviews})</span>
        </div>
      </div>

      {/* 이메일 */}
      <div className="text-xs lg:text-sm text-blue-700 underline ml-16 lg:ml-19">
        {email}
      </div>
    </div>
  );
}
