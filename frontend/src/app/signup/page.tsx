'use client';

import React from "react";
import Image from "next/image";
import Container from '@/components/Container';
import CardWrapper from '@/components/CardWrapper'; // 추가 필요

export default function SignUpPage() {
  return (
    <Container size="sm">
      <CardWrapper >
        <Image
          src="/mainrogo.png"
          alt="로고"
          width={200}
          height={300}
          className="mb-6"
        />

        <form className="space-y-4 mt-4 w-full">
          <input
            id="username"
            type="text"
            placeholder="아이디"
            className="text-[15px] w-full h-12 px-4 py-2 bg-white rounded-lg focus:border-yellow-400 focus:outline-none"
          />
          <input
            id="password"
            type="password"
            placeholder="비밀번호(6~24자의 문자, 숫자, 특수기호 조합)"
            className="text-[15px] w-full h-12 px-4 py-2 bg-white rounded-lg focus:border-yellow-400 focus:outline-none"
          />
          <input
            id="nickname"
            type="text"
            placeholder="닉네임"
            className="text-[15px] w-full h-12 px-4 py-2 bg-white rounded-lg focus:border-yellow-400 focus:outline-none "
          />

          <div className="flex items-center">
            <input
              id="agree"
              type="checkbox"
              className="h-4 w-4 focus:ring-yellow-400 border-[#9D9DAE] rounded"
            />
            <label htmlFor="agree" className="ml-2 text-[11px] text-black">
              <span className="underline text-blue-500">이용약관</span>,{' '}
              <span className="underline text-blue-500">개인정보 수집 및 이용</span>에 동의합니다.
            </label>
          </div>

          <button
            type="submit"
            className="w-full py-3 h-12 bg-[#FFD735] font-bold text-white font-medium rounded-lg hover:bg-yellow-400 transition mt-3"
          >
            회원 가입
          </button>
        </form>

        {/* 구분선 */}
        <div className="flex items-center mt-10 my-6 w-full">
          <hr className="flex-grow border-gray-300" />
          <span className="px-3 text-sm text-[#9D9DAE]">Or</span>
          <hr className="flex-grow border-gray-300" />
        </div>

        {/* 소셜 가입 */}
        <div className="space-y-3 w-full">
          <button className="w-full h-12 flex items-center justify-center rounded-lg hover:bg-gray-100 transition bg-white px-4">
            <Image src="/google.png" alt="Google" width={50} height={50} />
            <span className="ml-2 text-sm leading-none">Google 계정으로 가입</span>
          </button>
          <button className="w-full h-12 flex items-center justify-center rounded-lg hover:bg-gray-100 transition bg-white px-4">
            <Image src="/naver.png" alt="Naver" width={28} height={28} />
            <span className="ml-4 text-sm leading-none">Naver 계정으로 가입</span>
          </button>
        </div>
      </CardWrapper>
    </Container>
  );
}
