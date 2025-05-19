# ocr_module.py

import os
from PIL import Image
import pytesseract
import cv2
import numpy as np
import re
from datetime import datetime

# Tesseract 설정
os.environ['TESSDATA_PREFIX'] = r'C:\Program Files\Tesseract-OCR\tessdata'
pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'

def extract_business_info(image_stream):
    # 1. PIL로 읽고 OpenCV 그레이스케일 변환
    img = Image.open(image_stream)
    img_cv = cv2.cvtColor(np.array(img), cv2.COLOR_RGB2GRAY)

    # 2. 이진화(Otsu) & 노이즈 제거
    _, binary = cv2.threshold(img_cv, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
    kernel = np.ones((1,1), np.uint8)
    cleaned = cv2.morphologyEx(binary, cv2.MORPH_OPEN, kernel)

    # 3. OCR 수행 → text 변수에 저장
    pil_ready = Image.fromarray(cleaned)
    config = r'--tessdata-dir "C:/Program Files/Tesseract-OCR/tessdata" --oem 3 --psm 6'
    text = pytesseract.image_to_string(pil_ready, lang='kor+eng', config=config)

    # (디버깅용) 실제 뽑힌 텍스트 확인
    # print("OCR raw text:\n", text)

    # 4. 사업자등록번호 추출 (레이블 우선)
    business_number = None
    m = re.search(r'등록번호[:\s]*([0-9]{3}-[0-9]{2}-[0-9]{5})', text)
    if m:
        business_number = m.group(1)
    else:
        m1 = re.search(r'(\d{3})[-\s]?(\d{2})[-\s]?(\d{5})', text)
        if m1:
            business_number = f"{m1.group(1)}-{m1.group(2)}-{m1.group(3)}"

    # 5. 발행일자(결제일시) 추출 (레이블 우선)
    pay_date = None
    m2 = re.search(r'발행일자[:\s]*([0-9]{4}[-./][0-9]{2}[-./][0-9]{2})', text)
    if m2:
        raw = m2.group(1)
        pay_date = raw.replace('.', '-').replace('/', '-')
    else:
        dm = re.search(r'(\d{4}[-./]\d{2}[-./]\d{2})', text)
        if dm:
            pay_date = dm.group(1).replace('.', '-').replace('/', '-')

    success = bool(business_number and pay_date)
    return {
        'business_number': business_number,
        'pay_date': pay_date,
        'success': success,
        'message': '추출 완료' if success else '일부 정보 추출 실패'
    }
