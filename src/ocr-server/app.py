from flask import Flask, request, jsonify
from ocr_module import extract_business_info
import datetime

app = Flask(__name__)

@app.route('/ocr', methods=['POST'])
def ocr():
    # 1) 파일 유무 확인
    if 'file' not in request.files:
        return jsonify({'success': False, 'error': '이미지 파일이 포함되어 있지 않습니다.'}), 400

    file = request.files['file']
    if file.filename == '':
        return jsonify({'success': False, 'error': '선택된 파일이 없습니다.'}), 400

    if not allowed_file(file.filename):
        return jsonify({'success': False, 'error': '지원되지 않는 파일 형식입니다.'}), 400

    try:
        info = extract_business_info(file.stream)

        # OCR 추출 실패
        if not info.get('success', False):
            return jsonify({
                'success': False,
                'error': info.get('message')
            }), 422

        # 필드 매핑
        business_num = info.get('businessNumber')
        pay_date     = info.get('payDate')

        # 정보 누락 체크
        if not business_num or not pay_date:
            return jsonify({
                'success': False,
                'error': 'OCR에서 일부 정보를 추출하지 못했습니다.'
            }), 422

        # 정상 응답
        payload = {
            'success':        True,
            'businessNumber': business_num,
            'payDate':         pay_date
        }
        return jsonify(payload), 200

    except Exception as e:
        return jsonify({'success': False, 'error': str(e)}), 500


def allowed_file(filename):
    ext = filename.rsplit('.', 1)[-1].lower()
    return ext in {'jpg', 'jpeg', 'png', 'bmp', 'tiff'}

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
