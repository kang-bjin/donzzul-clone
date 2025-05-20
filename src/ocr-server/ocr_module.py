import cv2
import numpy as np
from PIL import Image
import pytesseract
import re

def extract_business_info(image_stream):
    img = Image.open(image_stream)
    img_cv = cv2.cvtColor(np.array(img), cv2.COLOR_RGB2GRAY)

    _, binary = cv2.threshold(img_cv, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
    kernel = np.ones((1,1), np.uint8)
    cleaned = cv2.morphologyEx(binary, cv2.MORPH_OPEN, kernel)

    pil_ready = Image.fromarray(cleaned)
    config = r'--oem 3 --psm 6'
    text = pytesseract.image_to_string(pil_ready, lang='kor+eng', config=config)

    business_number = None
    m = re.search(r'등록번호[:\s]*([0-9]{3}-[0-9]{2}-[0-9]{5})', text)
    if m:
        business_number = m.group(1)
    else:
        m1 = re.search(r'(\d{3})[-\s]?(\d{2})[-\s]?(\d{5})', text)
        if m1:
            business_number = f"{m1.group(1)}-{m1.group(2)}-{m1.group(3)}"

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
