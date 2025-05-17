from flask import Flask, request, jsonify
from PIL import Image
import pytesseract
import re
import cv2
import numpy as np
from datetime import datetime
pytesseract.pytesseract.tesseract_cmd = r'C:/Program Files/Tesseract-OCR/tesseract.exe'

app = Flask(__name__)

@app.route('/ocr', methods=['POST'])
def ocr():
    try:
        # 1. 백엔드에서 이미지 파일 받아오기
        if 'file' not in request.files:
            return jsonify({'error': '이미지 파일을 찾을 수 없습니다'}), 400
        
        file = request.files['file']  # <- 여기서 이미지 받음
        if file.filename == '':
            return jsonify({'error': 'No selected file'}), 400

        # 2. 이미지 열기
        img = Image.open(file.stream)  # PIL 이미지로 열기

        # 2-1. 이미지 전처리처리 작업 
        img_cv = np.array(img)              # PIL 이미지 → OpenCV로 변환
        img_cv = cv2.cvtColor(img_cv, cv2.COLOR_RGB2GRAY)
        _, img_cv = cv2.threshold(img_cv, 150, 240, cv2.THRESH_BINARY)  # 이진화 (Thresholding)
        img = Image.fromarray(img_cv)  # 다시 PIL 이미지로 변환

        # 3. OCR로 텍스트 추출
        text = pytesseract.image_to_string(img, config='--psm 6')

        # 4-1. 사업자등록번호 정규식으로 추출
        match_n1 = re.search(r'\d{10}', text)
        match_n2 = re.search(r'\d{3}-\d{2}-\d{5}', text)

        if match_n1:
            s = match_n1.group()
            business_number = s[:3] + "-" + s[3:5] + "-" + s[5:]
        elif match_n2:
            business_number = match_n2.group()
        else:
            business_number = None
        
        # 4-2. 결제일시 추출
        pay_date = None
        date_match = re.search(r'(\d{4}[-./]\d{2}[-./]\d{2})|(\d{2}[-./]\d{2}[-./]\d{2})', text)

        # 5. 결제일시 날짜 포맷 통일
        if date_match:
            pay_date_raw = date_match.group().replace('.', '-').replace('/', '-')
            try:
                # 6자리 형식이면 20xx로 앞자리 보정
                if re.match(r'\d{2}-\d{2}-\d{2}', pay_date_raw):
                    pay_date = datetime.strptime(pay_date_raw, "%y-%m-%d").strftime("%Y-%m-%d")
                else:
                    pay_date = datetime.strptime(pay_date_raw, "%Y-%m-%d").strftime("%Y-%m-%d")
            except Exception as e:
                pay_date = None

        # 6. 결과 반환
        return jsonify({
            'business_number': business_number,
            'pay_date': pay_date,
            'success': True if business_number and pay_date else False,
            'message': '추출 완료' if business_number and pay_date else '일부 정보 추출 실패'
        })

    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)