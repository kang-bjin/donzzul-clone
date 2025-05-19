FROM python:3.9-slim

# 필수 패키지 설치
RUN apt-get update && apt-get install -y \
    tesseract-ocr \
    tesseract-ocr-kor \
    tesseract-ocr-eng \
    libglib2.0-0 libsm6 libxext6 libxrender-dev \
    && apt-get clean

# 작업 디렉토리 복사
WORKDIR /app
COPY . /app

# 파이썬 의존성 설치
RUN pip install --no-cache-dir -r requirements.txt

CMD ["python", "app.py"]
