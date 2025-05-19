from PIL import Image
import pytesseract
import cv2
import numpy as np

# Tesseract 실행 파일 경로 (Windows에서 필요)
pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'

# 이미지 열기
image_path = 'receipt2.png'
image = cv2.imread(image_path)

# 1. 이미지 확대 (2배)
image = cv2.resize(image, None, fx=2, fy=2, interpolation=cv2.INTER_CUBIC)

# 2. 그레이스케일 변환
gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# 3. 가우시안 블러 (노이즈 제거용)
blurred_image = cv2.GaussianBlur(gray_image, (5, 5), 0)

# 4. 이진화 (Otsu Thresholding 사용)
_, threshold_image = cv2.threshold(blurred_image, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

# 5. 노이즈 제거 (열림 연산 적용)
kernel = np.ones((1, 1), np.uint8)
processed_image = cv2.morphologyEx(threshold_image, cv2.MORPH_OPEN, kernel)

# 6. 경계선 강조 (Edge Detection)
edges = cv2.Canny(processed_image, 100, 200)

# 7. 이미지 반전 (흰 배경 & 검은 텍스트로 변환)
inverted_image = cv2.bitwise_not(processed_image)

# 8. 모폴로지 변환 (Dilate / Erode)
kernel = np.ones((2, 2), np.uint8)
dilated_image = cv2.dilate(inverted_image, kernel, iterations=1)
eroded_image = cv2.erode(dilated_image, kernel, iterations=1)

# 9. Tesseract 설정 옵션 지정
custom_config = r'--oem 3 --psm 6'  # oem 3: LSTM 기반 인식, psm 6: 단일 블록 인식

# 10. Tesseract로 텍스트 추출
extracted_text = pytesseract.image_to_string(eroded_image, lang='kor+eng', config=custom_config)

# 결과 출력
print("추출된 텍스트:")
print(extracted_text)