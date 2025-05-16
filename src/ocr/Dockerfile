FROM python:3.10-slim
WORKDIR /app
COPY . /app
RUN pip install flask pillow pytesseract
RUN apt-get update && apt-get install -y tesseract-ocr
CMD ["python", "ocr_module.py"]