FROM python:3.9

# 1) 시스템 의존성 설치
RUN apt-get update && apt-get install -y \
    tesseract-ocr \
    tesseract-ocr-kor \
    tesseract-ocr-eng \
    libglib2.0-0 \
    libsm6 \
    libxext6 \
    libxrender-dev \
    libgl1-mesa-glx \
 && apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# 2) requirements.txt 복사 및 설치
COPY requirements.txt /app/
RUN pip install --no-cache-dir -r requirements.txt

# 3) 애플리케이션 코드 복사
COPY . /app

# 4) 실행
#    - 개발용: python app.py
#    - 운영용: gunicorn 쓰고 싶으면 아래처럼 바꿔서 사용
CMD ["python", "app.py"]
# CMD ["gunicorn", "-b", "0.0.0.0:5000", "app:app"]
