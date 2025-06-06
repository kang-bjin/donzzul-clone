// tailwind.config.js
/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './pages/**/*.{js,ts,jsx,tsx,mdx}',
    './components/**/*.{js,ts,jsx,tsx,mdx}',
    './app/**/*.{js,ts,jsx,tsx,mdx}', // App Router 사용 시
    './src/**/*.{js,ts,jsx,tsx,mdx}', // src 디렉토리 전체를 포함하는 것이 안전
  ],
  theme: {
    extend: {},
  },
  plugins: [],
};