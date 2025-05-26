'use client';

import React, { useState } from "react";
import Image from "next/image";
import Container from '@/components/Container';
import CardWrapper from '@/components/CardWrapper';
import { useRouter } from 'next/navigation';

export default function Login() {
  const router = useRouter();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const res = await fetch('http://localhost:8080/users/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });

      if (!res.ok) {
        throw new Error('로그인 실패');
      }

      const data = await res.json();
      localStorage.setItem('token', data.token);
      alert('로그인 성공!');
      router.push('/main');  // 메인 페이지로 이동
    } catch (err) {
      console.error(err);
      alert('로그인에 실패했습니다.');
    }
  };

  return (
    <Container size="sm">
      <CardWrapper>
        <Image
          src="/mainrogo.png"
          alt="로고"
          width={200}
          height={200}
          className="mb-6"
        />

        <form className="space-y-4 mt-4 w-full" onSubmit={handleLogin}>
          <input
            id="username"
            type="text"
            placeholder="아이디"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="text-[15px] w-full h-12 px-4 py-2 bg-white rounded-lg focus:border-yellow-400 focus:outline-none"
          />
          <input
            id="password"
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="text-[15px] w-full h-12 px-4 py-2 bg-white rounded-lg focus:border-yellow-400 focus:outline-none"
          />
          <button
            type="submit"
            className="w-full h-12 py-3 bg-[#FFD735] text-white font-bold rounded-lg hover:bg-yellow-400 transition mt-3"
          >
            로그인
          </button>
        </form>

        <button
          onClick={() => router.push('/signup')}
          className="w-full h-12 py-3 bg-[#FFFFFF] text-black border-2 border-[#FFCD00] font-medium rounded-lg hover:bg-yellow-400 transition mt-3"
        >
          회원가입
        </button>

        <div className="flex items-center mt-20 my-6 w-full">
          <hr className="flex-grow border-gray-300" />
          <span className="px-3 text-sm text-[#9D9DAE]">Or</span>
          <hr className="flex-grow border-gray-300" />
        </div>

        <div className="space-y-3 w-full">
          <button className="w-full h-12 flex items-center justify-center rounded-lg hover:bg-gray-100 transition bg-white px-4">
            <Image src="/google.png" alt="Google" width={50} height={50} />
            <span className="ml-2 text-sm leading-none">Google 계정으로 로그인</span>
          </button>
          <button className="w-full h-12 flex items-center justify-center rounded-lg hover:bg-gray-100 transition bg-white px-4">
            <Image src="/naver.png" alt="Naver" width={28} height={28} />
            <span className="ml-4 text-sm leading-none">Naver 계정으로 로그인</span>
          </button>
        </div>
      </CardWrapper>
    </Container>
  );
}
