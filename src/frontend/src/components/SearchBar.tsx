'use client'

export default function SearchBar() {
  return (
    <div className="flex items-center w-full bg-[#EEEEF0] rounded-lg px-3 py-2 -mt-2">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        strokeWidth={1.5}
        stroke="currentColor"
        className="w-5 h-5 text-[#91929F] flex-shrink-0"
      >
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z"
        />
      </svg>
      <input
        type="text"
        placeholder="어떤 가게를 찾아볼까요?"
        className="flex-grow pl-2 text-sm bg-transparent placeholder:text-[#91929F] focus:outline-none"
      />
      <div className="h-5 w-px bg-gray-300 mx-2" />
      <button className="text-sm text-[#4C4C57] hover:text-black">검색</button>
    </div>
  )
}
