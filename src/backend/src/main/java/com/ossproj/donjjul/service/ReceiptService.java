// service/ReceiptService.java
@Service
@RequiredArgsConstructor
public class ReceiptService {

    private final GoodStoreRepository goodStoreRepository;

    public boolean verifyReceipt(String businessNumber, String payDateStr) {
        // 1. 날짜 3일 초과 여부 검사
        LocalDate payDate;
        try {
            payDate = LocalDate.parse(payDateStr); // "yyyy-MM-dd"
        } catch (Exception e) {
            return false;
        }

        if (payDate.isBefore(LocalDate.now().minusDays(3))) {
            return false; // 인증 기한 초과
        }

        // 2. DB에 사업자번호 존재 여부 검사
        return goodStoreRepository.existsByBusinessNumber(businessNumber);
    }
}