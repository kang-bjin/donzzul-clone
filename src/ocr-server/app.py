from flask import Flask, request, jsonify
from ocr_module import extract_business_info
import datetime

app = Flask(__name__)

@app.route('/ocr', methods=['POST'])
def ocr():
    if 'file' not in request.files:
        return jsonify({'success': False, 'error': '이미지 파일이 포함되어 있지 않습니다.'}), 400

    file = request.files['file']
    if file.filename == '':
        return jsonify({'success': False, 'error': '선택된 파일이 없습니다.'}), 400

    if not allowed_file(file.filename):
        return jsonify({'success': False, 'error': '지원되지 않는 파일 형식입니다.'}), 400

    try:
        info = extract_business_info(file.stream)
        # extract_business_info 반환 예시:
        # {
        #   'business_number': '123-45-67890',
        #   'date': datetime.date(2025, 5, 20),
        #   'store_name': '도넛가게'
        # }
        success = info.get('success', True)
        # 결제일 처리
        raw_date = info.get('date')
        pay_date = None
        if isinstance(raw_date, datetime.date):
            pay_date = raw_date.isoformat()                # "yyyy-MM-dd"
        elif isinstance(raw_date, str):
            pay_date = raw_date                             # 이미 문자열이면 그대로
        # 매장 이름
        store_name = info.get('store_name')

        payload = {
            'success': success,
            'businessNumber': info.get('business_number'),
            'payDate': pay_date,
            'storeName': store_name
        }
        status_code = 200 if success else 422
        return jsonify(payload), status_code

    except Exception as e:
        return jsonify({'success': False, 'error': str(e)}), 500

def allowed_file(filename):
    ext = filename.rsplit('.', 1)[-1].lower()
    return ext in {'jpg', 'jpeg', 'png', 'bmp', 'tiff'}

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
