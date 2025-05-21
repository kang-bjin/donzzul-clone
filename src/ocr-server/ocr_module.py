import cv2
import numpy as np
from PIL import Image
import pytesseract
import easyocr
import re

def extract_business_info(image_stream):
    """
    OCR 기반 전자영수증 분석 모듈
    - 1차: Tesseract 기반 전처리 + 인식
    - 2차: 실패 시 EasyOCR 딥러닝 엔진으로 재시도
    - 추출 정보: 사업자등록번호, 발행일자
    """

    def extract_fields_from_text(text):
        # 사업자등록번호
        business_number = None
        m = re.search(r'(등록번호|사업자번호|사업자 등록번호|사업자)[:\s]*([0-9]{3}[-\s]?[0-9]{2}[-\s]?[0-9]{5})', text)
        if m:
            business_number = m.group(2)
        else:
            m1 = re.search(r'(\d{3})[-\s]?(\d{2})[-\s]?(\d{5})', text)
            if m1:
                business_number = f"{m1.group(1)}-{m1.group(2)}-{m1.group(3)}"

        # 발행일자
        pay_date = None
        m2 = re.search(r'발행일자[:\s]*([0-9]{4}[-./][0-9]{2}[-./][0-9]{2})', text)
        if m2:
            raw = m2.group(1)
            pay_date = raw.replace('.', '-').replace('/', '-')
        else:
            dm = re.search(r'(\d{4}[-./]\d{2}[-./]\d{2})', text)
            if dm:
                pay_date = dm.group(1).replace('.', '-').replace('/', '-')

        return business_number, pay_date

    # STEP 0: 이미지 열기
    img = Image.open(image_stream)

    # STEP 1: Upscale
    scale_factor = 1.5
    img = img.resize(
        (int(img.width * scale_factor), int(img.height * scale_factor)),
        Image.LANCZOS
    )

    # STEP 2~4: Grayscale + Otsu + Morphology
    img_cv = cv2.cvtColor(np.array(img), cv2.COLOR_RGB2GRAY)
    _, binary = cv2.threshold(img_cv, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
    kernel = np.ones((1,1), np.uint8)
    cleaned = cv2.morphologyEx(binary, cv2.MORPH_OPEN, kernel)

    # STEP 5: Tesseract OCR 시도
    pil_ready = Image.fromarray(cleaned)
    config = r'--oem 3 --psm 6'
    text = pytesseract.image_to_string(pil_ready, lang='kor+eng', config=config)

    # STEP 6: 정보 추출
    business_number, pay_date = extract_fields_from_text(text)

    # STEP 7: Tesseract 실패 시 EasyOCR Fallback
    if not (business_number and pay_date):
        reader = easyocr.Reader(['ko', 'en'], gpu=False)
        result_lines = reader.readtext(np.array(img), detail=0)
        fallback_text = "\n".join(result_lines)
        business_number, pay_date = extract_fields_from_text(fallback_text)
        text = fallback_text  # 로그용으로 덮어쓰기

    # STEP 8: 결과 구성
    success = bool(business_number and pay_date)
    return {
        'business_number': business_number,
        'pay_date': pay_date,
        'success': success,
        'message': '추출 완료' if success else '일부 정보 추출 실패',
        'raw_text': text[:500]
    }
