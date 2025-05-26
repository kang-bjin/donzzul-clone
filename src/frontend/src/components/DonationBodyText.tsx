// components/DonationBodyCard.tsx
interface BodyProps {
  summaryTitle: string;
  description: string[];
  bottomImage: string;
}

export default function DonationBodyCard({ summaryTitle, description, bottomImage }: BodyProps) {
  return (
    <div className="text-sm sm:text-base text-gray-800 leading-relaxed whitespace-pre-wrap">
      <p className="font-semibold mb-2">{summaryTitle}</p>
      {description.map((line, idx) => (
        <p key={idx} className="mb-1">{line}</p>
      ))}
    </div>
  );
}
