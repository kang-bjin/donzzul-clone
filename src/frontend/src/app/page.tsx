'use client';
import React from "react";
import { useRouter } from 'next/navigation';
import Container from '@/components/Container';
import CardWrapper from "@/components/CardWrapper";


export default function HomePage() {
  const router = useRouter();

  return (
    <Container>
      <CardWrapper>
        <img src="/mainrogo.png" alt="로고" className="mt-15 mb-6 w-[200px] " />
        <button onClick={() => router.push('/login')}>
          <img src="/hamtory_rogo.png" alt="로그인 버튼" className="w-[200px]" />
        </button>
      </CardWrapper>
    </Container>
  );
}
