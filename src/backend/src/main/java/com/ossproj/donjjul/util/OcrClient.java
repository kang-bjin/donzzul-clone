@Component
public class OcrClient {

    public OcrResponseDto requestOcr(MultipartFile imageFile) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(imageFile.getInputStream(), imageFile.getOriginalFilename()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String ocrUrl = "http://ocr:5000/ocr"; // Docker Compose 기준 (혹은 http://localhost:5000)

        ResponseEntity<OcrResponseDto> response = restTemplate.postForEntity(
                ocrUrl, requestEntity, OcrResponseDto.class
        );

        return response.getBody();
    }
}