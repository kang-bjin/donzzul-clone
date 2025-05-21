import cv2  # OpenCV 라이브러리 (Computer Vision 처리용)
import numpy as np  # 이미지 배열 처리를 위한 NumPy
from PIL import Image  # 이미지 파일을 열기 위한 Pillow
import pytesseract  # OCR 엔진 (Tesseract)
import re  # 정규표현식 (정보 추출용)

def extract_business_info(image_stream):
    """
    OCR 기반 전자영수증 분석 모듈
    - 이미지 전처리: Upscale, Grayscale, Binarization, Morphological Noise 제거
    - 텍스트 인식: Tesseract OCR (한영 지원)
    - 정보 추출: 사업자등록번호, 발행일자
    """

    # STEP 0: 이미지 열기 (Pillow)
    img = Image.open(image_stream)

    # STEP 1: 이미지 확대 (Upscaling for OCR Precision)
    scale_factor = 1.5
    img = img.resize(
        (int(img.width * scale_factor), int(img.height * scale_factor)),
        Image.LANCZOS  # 고해상도 보간 필터
    )

    # STEP 2: Grayscale 변환 (OpenCV 활용)
    img_cv = cv2.cvtColor(np.array(img), cv2.COLOR_RGB2GRAY)

    # STEP 3: 이진화 (Otsu 알고리즘 적용) - 배경 제거 및 대비 향상
    _, binary = cv2.threshold(img_cv, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

    # STEP 4: 형태학적 연산 (Morphological Opening) - 잔여 노이즈 제거
    kernel = np.ones((1,1), np.uint8)
    cleaned = cv2.morphologyEx(binary, cv2.MORPH_OPEN, kernel)

    # STEP 5: Pillow 이미지로 변환 (Tesseract OCR 입력용)
    pil_ready = Image.fromarray(cleaned)

    # STEP 6: OCR 수행 (한글 + 영어 / 기본 텍스트 블록 모드)
    config = r'--oem 3 --psm 6'
    text = pytesseract.image_to_string(pil_ready, lang='kor+eng', config=config)

   # STEP 7: 사업자등록번호 추출 (다양한 라벨 대응)
    business_number = None
    m = re.search(r'(등록번호|사업자번호|사업자 등록번호|사업자)[:\s]*([0-9]{3}[-\s]?[0-9]{2}[-\s]?[0-9]{5})', text)
    if m:
        business_number = m.group(2)
    else:
        # 포맷만 있는 경우 커버
        m1 = re.search(r'(\d{3})[-\s]?(\d{2})[-\s]?(\d{5})', text)
        if  m1:
            business_number = f"{m1.group(1)}-{m1.group(2)}-{m1.group(3)}"

    # STEP 8: 발행일자 추출 (포맷 다양성 고려)
    pay_date = None
    m2 = re.search(r'발행일자[:\s]*([0-9]{4}[-./][0-9]{2}[-./][0-9]{2})', text)
    if m2:
        raw = m2.group(1)
        pay_date = raw.replace('.', '-').replace('/', '-')
    else:
        dm = re.search(r'(\d{4}[-./]\d{2}[-./]\d{2})', text)
        if dm:
            pay_date = dm.group(1).replace('.', '-').replace('/', '-')

    # STEP 9: 결과 구성 및 반환
    success = bool(business_number and pay_date)
    return {
        'business_number': business_number,
        'pay_date': pay_date,
        'success': success,
        'message': '추출 완료' if success else '일부 정보 추출 실패'
    }
