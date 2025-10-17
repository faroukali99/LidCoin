import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 1. KYCService.java
// ============================================
public class KYCService {
    private Map<String, KYCRecord> kycRecords;
    private DocumentVerifier verifier;
    
    public KYCService() {
        this.kycRecords = new ConcurrentHashMap<>();
        this.verifier = new DocumentVerifier();
    }
    
    public void submitKYC(String userId, String fullName, String nationalId, String email, String address, String phoneNumber) {
        KYCRecord record = new KYCRecord(userId, fullName, nationalId, email);
        record.setAddress(address);
        record.setPhoneNumber(phoneNumber);
        kycRecords.put(userId, record);
        System.out.println("KYC soumis pour l'utilisateur: " + userId);
    }
    
    public void verifyKYC(String userId, boolean approved, String verifier) {
        KYCRecord record = kycRecords.get(userId);
        if (record != null) {
            record.setStatus(approved ? KYCRecord.KYCStatus.APPROVED : KYCRecord.KYCStatus.REJECTED);
            record.setVerificationDate(Instant.now().toEpochMilli());
            record.setVerifiedBy(verifier);
            System.out.println("KYC " + (approved ? "approuvé" : "rejeté") + " pour: " + userId);
        }
    }
    
    public boolean isKYCApproved(String userId) {
        KYCRecord record = kycRecords.get(userId);
        return record != null && record.getStatus() == KYCRecord.KYCStatus.APPROVED;
    }
    
    public KYCRecord getKYCRecord(String userId) {
        return kycRecords.get(userId);
    }
    
    public List<KYCRecord> getPendingKYC() {
        List<KYCRecord> pending = new ArrayList<>();
        for (KYCRecord record : kycRecords.values()) {
            if (record.getStatus() == KYCRecord.KYCStatus.PENDING) {
                pending.add(record);
            }
        }
        return pending;
    }
    
    public void uploadDocument(String userId, String documentType, byte[] documentData) {
        KYCRecord record = kycRecords.get(userId);
        if (record != null) {
            record.addDocument(documentType, documentData);
            System.out.println("Document uploadé pour " + userId + ": " + documentType);
        }
    }
    
    public boolean verifyDocument(String userId, String documentType) {
        KYCRecord record = kycRecords.get(userId);
        if (record != null) {
            byte[] document = record.getDocument(documentType);
            return verifier.verifyDocument(document, documentType);
        }
        return false;
    }
}
