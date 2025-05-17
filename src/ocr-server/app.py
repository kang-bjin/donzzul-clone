from PIL import Image
import pytesseract
import cv2
import numpy as np
import os

pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'

BASE_DIR = os.path.dirname(__file__)   # app.py 파일이 있는 폴더 경로
image_path = os.path.join(BASE_DIR, 'test_data', 'receipt1.png')

image_cv = cv2.imread(image_path)
if image_cv is None:
    raise FileNotFoundError(f"이미지 파일을 찾을 수 없습니다: {image_path}")

# 1. 이미지 확대 (2배)
image_cv = cv2.resize(image_cv, None, fx=2, fy=2, interpolation=cv2.INTER_CUBIC)

# 2. 그레이스케일 변환
gray_image = cv2.cvtColor(image_cv, cv2.COLOR_BGR2GRAY)

# 3. 가우시안 블러 (노이즈 제거)
blurred_image = cv2.GaussianBlur(gray_image, (5, 5), 0)

# 4. 이진화 (Otsu)
_, threshold_image = cv2.threshold(blurred_image, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

# 5. 노이즈 제거 (열림 연산)
kernel = np.ones((1, 1), np.uint8)
processed_image = cv2.morphologyEx(threshold_image, cv2.MORPH_OPEN, kernel)

# 6. 경계선 강조
edges = cv2.Canny(processed_image, 100, 200)

# 7. 이미지 반전
inverted_image = cv2.bitwise_not(processed_image)

# 8. 모폴로지 변환 (Dilate/Erode)
kernel2 = np.ones((2, 2), np.uint8)
dilated_image = cv2.dilate(inverted_image, kernel2, iterations=1)
eroded_image = cv2.erode(dilated_image, kernel2, iterations=1)

# 9. PIL로 변환 후 OCR
image_for_ocr = Image.fromarray(eroded_image)
custom_config = r'--oem 3 --psm 6'
extracted_text = pytesseract.image_to_string(image_for_ocr, lang='kor+eng', config=custom_config)

print("추출된 텍스트:")
print(extracted_text)

# 시각화
cv2.imshow('Original Image', image_cv)
cv2.imshow('Processed Image', processed_image)
cv2.imshow('Edges', edges)
cv2.imshow('Dilated & Eroded Image', eroded_image)
cv2.waitKey(0)
cv2.destroyAllWindows()
