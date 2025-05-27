import cv2
import numpy as np
from PIL import Image
import pytesseract
import easyocr
import re
import datetime
pytesseract.pytesseract.tesseract_cmd = r'C:/Program Files/Tesseract-OCR/tesseract.exe'

# EasyOCR Reader(한 번만 생성)
reader = easyocr.Reader(['ko', 'en'], gpu=False)

def extract_business_info(image_stream):
    img = Image.open(image_stream)
    # 전처리: 업스케일 + Grayscale + Otsu + Morphology
    img = img.resize((int(img.width * 1.5), int(img.height * 1.5)), Image.LANCZOS)
    img_cv = cv2.cvtColor(np.array(img), cv2.COLOR_RGB2GRAY)
    _, binary = cv2.threshold(img_cv, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
    kernel = np.ones((1,1), np.uint8)
    cleaned = cv2.morphologyEx(binary, cv2.MORPH_OPEN, kernel)

    # 1) 룰베이스 텍스트 추출 (Tesseract)
    raw_text = pytesseract.image_to_string(
        Image.fromarray(cleaned),
        lang='eng',
        config='--oem 3 --psm 6'
    )

    # 2) 정규식으로 정보 추출 시도
    info = _parse_text(raw_text)
    if not (info['businessNumber'] and info['payDate']):
        # 3) 실패 시 EasyOCR로 재시도
        easy_text = "\n".join(reader.readtext(cleaned, detail=0))
        info = _parse_text(easy_text)
        info['rawText'] = easy_text
        info['usedFallback'] = True
    else:
        info['rawText'] = raw_text
        info['usedFallback'] = False

    # 4) 최종 성공 여부
    info['success'] = bool(info['businessNumber'] and info['payDate'])
    if not info['success']:
        info['message'] = '일부 정보 추출 실패'
    else:
        info['message'] = '추출 완료'

    return info

def _parse_text(text):
    # 사업자번호 추출
    business_number = None
    m = re.search(
        r'(등록번호|사업자번호|사업자 등록번호|사업자)[:\s]*([0-9]{3}[-\s]?[0-9]{2}[-\s]?[0-9]{5})',
        text
    )
    if m:
        business_number = m.group(2)
    else:
        m1 = re.search(r'(\d{3})[-\s]?(\d{2})[-\s]?(\d{5})', text)
        if m1:
            business_number = f"{m1.group(1)}-{m1.group(2)}-{m1.group(3)}"

    # 결제일(pay_date) 추출
    pay_date = None
    m2 = re.search(
        r'(발행일자|정산일시|거래일시)[:\s]*([0-9]{4}[-./][0-9]{2}[-./][0-9]{2})',
        text
    )
    if m2:
        raw = m2.group(2)
        pay_date = raw.replace('.', '-').replace('/', '-')
    else:
        dm = re.search(r'(\d{4}[-./]\d{2}[-./]\d{2})', text)
        if dm:
            pay_date = dm.group(1).replace('.', '-').replace('/', '-')

    # 매장 이름(store_name)은 첫 번째 non-empty 라인
    lines = [ln.strip() for ln in text.splitlines() if ln.strip()]
    store_name = lines[0] if lines else None

    return {
        'businessNumber': business_number,
        'payDate': pay_date,
        'storeName': store_name
    }
