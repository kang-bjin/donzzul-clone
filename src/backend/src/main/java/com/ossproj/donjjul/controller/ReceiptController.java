// controller/ReceiptController.java
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receipt")
public class ReceiptController {

    private final OcrClient ocrClient;
    private final ReceiptService receiptService;

    @PostMapping("/verify")
    public ResponseEntity<?> verifyReceipt(@RequestParam("file") MultipartFile file) throws IOException {
        OcrResponseDto ocrResult = ocrClient.requestOcr(file);

        if (!ocrResult.isSuccess()) {
            return ResponseEntity.badRequest().body(Map.of("message", "OCR 실패"));
        }

        boolean isValid = receiptService.verifyReceipt(
                ocrResult.getBusiness_number(),
                ocrResult.getPay_date()
        );

        return ResponseEntity.ok(Map.of(
                "valid", isValid,
                "business_number", ocrResult.getBusiness_number(),
                "pay_date", ocrResult.getPay_date()
        ));
    }
}