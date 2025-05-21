import cv2  # OpenCV 라이브러리 (Computer Vision 처리용)
import numpy as np  # 이미지 배열 처리를 위한 NumPy
from PIL import Image  # 이미지 파일을 열기 위한 Pillow
import pytesseract  # OCR 엔진
import re  # 정규표현식

def extract_business_info(image_stream):
    # 이미지 열기 (Pillow 사용)
    img = Image.open(image_stream)

    # CV 처리 시작: Pillow 이미지를 NumPy 배열로 변환 후 그레이스케일로 변환 (OpenCV)
    img_cv = cv2.cvtColor(np.array(img), cv2.COLOR_RGB2GRAY)  # <-- CV

    # Otsu 알고리즘으로 이진화 처리 (흑백 처리, 배경 제거 등)  <-- CV
    _, binary = cv2.threshold(img_cv, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

    # 노이즈 제거 (Morphological Opening 적용)  <-- CV
    kernel = np.ones((1,1), np.uint8)
    cleaned = cv2.morphologyEx(binary, cv2.MORPH_OPEN, kernel)

    # 다시 Pillow 이미지로 변환 (OCR을 위해)
    pil_ready = Image.fromarray(cleaned)

    # Tesseract 설정 (한글 + 영어, 기본 OCR 모드, 일반적인 텍스트)
    config = r'--oem 3 --psm 6'
    text = pytesseract.image_to_string(pil_ready, lang='kor+eng', config=config)

    # 사업자등록번호 추출
    business_number = None
    m = re.search(r'등록번호[:\s]*([0-9]{3}-[0-9]{2}-[0-9]{5})', text)
    if m:
        business_number = m.group(1)
    else:
        # 형식이 살짝 다른 경우 커버
        m1 = re.search(r'(\d{3})[-\s]?(\d{2})[-\s]?(\d{5})', text)
        if m1:
            business_number = f"{m1.group(1)}-{m1.group(2)}-{m1.group(3)}"

    # 발행일자 추출
    pay_date = None
    m2 = re.search(r'발행일자[:\s]*([0-9]{4}[-./][0-9]{2}[-./][0-9]{2})', text)
    if m2:
        raw = m2.group(1)
        pay_date = raw.replace('.', '-').replace('/', '-')
    else:
        # 포맷 없이 날짜만 있을 경우 커버
        dm = re.search(r'(\d{4}[-./]\d{2}[-./]\d{2})', text)
        if dm:
            pay_date = dm.group(1).replace('.', '-').replace('/', '-')

    # 성공 여부 체크
    success = bool(business_number and pay_date)

    # 결과 반환
    return {
        'business_number': business_number,
        'pay_date': pay_date,
        'success': success,
        'message': '추출 완료' if success else '일부 정보 추출 실패'
    }
