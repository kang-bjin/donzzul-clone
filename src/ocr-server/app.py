# app.py
from flask import Flask, request, jsonify
from ocr_module import extract_business_info

app = Flask(__name__)

@app.route('/ocr', methods=['POST'])
def ocr():
    # 1. 파일 수신
    if 'file' not in request.files:
        return jsonify({'error': '이미지 파일을 찾을 수 없습니다'}), 400
    file = request.files['file']
    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400

    # 2. OCR 모듈 호출
    try:
        result = extract_business_info(file.stream)
        return jsonify(result)
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)