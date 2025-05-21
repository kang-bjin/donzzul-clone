from flask import Flask, request, jsonify
from ocr_module import extract_business_info
import werkzeug

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
        result = extract_business_info(file.stream)
        result['success'] = result.get('success', False)
        return jsonify(result), 200 if result['success'] else 422
    except Exception as e:
        return jsonify({'success': False, 'error': str(e)}), 500

def allowed_file(filename):
    ext = filename.rsplit('.', 1)[-1].lower()
    return ext in {'jpg', 'jpeg', 'png', 'bmp', 'tiff'}

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
