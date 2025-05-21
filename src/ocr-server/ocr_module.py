import cv2
import numpy as np
from PIL import Image
import pytesseract
import re

def extract_business_info(image_stream):
    img = Image.open(image_stream)

    # 전처리: 업스케일 + Grayscale + Otsu + Morphology
    img = img.resize((int(img.width * 1.5), int(img.height * 1.5)), Image.LANCZOS)
    img_cv = cv2.cvtColor(np.array(img), cv2.COLOR_RGB2GRAY)
    _, binary = cv2.threshold(img_cv, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
    kernel = np.ones((1,1), np.uint8)
    cleaned = cv2.morphologyEx(binary, cv2.MORPH_OPEN, kernel)

    text = pytesseract.image_to_string(Image.fromarray(cleaned), lang='kor+eng', config='--oem 3 --psm 6')

    business_number = None
    m = re.search(r'(등록번호|사업자번호|사업자 등록번호|사업자)[:\s]*([0-9]{3}[-\s]?[0-9]{2}[-\s]?[0-9]{5})', text)
    if m:
        business_number = m.group(2)
    else:
        m1 = re.search(r'(\d{3})[-\s]?(\d{2})[-\s]?(\d{5})', text)
        if m1:
            business_number = f"{m1.group(1)}-{m1.group(2)}-{m1.group(3)}"

    pay_date = None
    m2 = re.search(r'(발행일자|정산일시|거래일시)[:\s]*([0-9]{4}[-./][0-9]{2}[-./][0-9]{2})', text)
    if m2:
        raw = m2.group(2)  
        pay_date = raw.replace('.', '-').replace('/', '-')
    else:
        dm = re.search(r'(\d{4}[-./]\d{2}[-./]\d{2})', text)
        if dm:
            pay_date = dm.group(1).replace('.', '-').replace('/', '-')

    return {
        'business_number': business_number,
        'pay_date': pay_date,
        'success': bool(business_number and pay_date),
        'message': '추출 완료' if business_number and pay_date else '일부 정보 추출 실패',
        'raw_text': text[:500]
    }
