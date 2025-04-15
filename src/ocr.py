from PIL import Image
import pytesseract

# Tesseract 실행 파일 경로 (Windows에서 필요)
pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'

# 이미지 열기
image_path = 'receipt2.png'
image = Image.open(image_path)

# 이미지에서 텍스트 추출
extracted_text = pytesseract.image_to_string(image, lang='kor+eng')
print(extracted_text)


# 이미지 확인 (선택 사항)
cv2.imshow('Original Image', image)
cv2.imshow('Processed Image', processed_image)
cv2.imshow('Edges', edges)
cv2.imshow('Dilated & Eroded Image', eroded_image)
cv2.waitKey(0)
cv2.destroyAllWindows()
