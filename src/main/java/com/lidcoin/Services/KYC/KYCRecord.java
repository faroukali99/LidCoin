// ============================================
// 2. KYCRecord.java
// ============================================
class KYCRecord {
    private String userId;
    private String fullName;
    private String nationalId;
    private String address;
    private String phoneNumber;
    private String email;
    private KYCStatus status;
    private KYCLevel level;
    private long submissionDate;
    private long verificationDate;
    private String verifiedBy;
    private Map<String, byte[]> documents;
    private List<String> notes;
    
    public enum KYCStatus {
        PENDING, APPROVED, REJECTED, UNDER_REVIEW, EXPIRED
    }
    
    public enum KYCLevel {
        BASIC, INTERMEDIATE, ADVANCED
    }
    
    public KYCRecord(String userId, String fullName, String nationalId, String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.nationalId = nationalId;
        this.email = email;
        this.status = KYCStatus.PENDING;
        this.level = KYCLevel.BASIC;
        this.submissionDate = Instant.now().toEpochMilli();
        this.documents = new HashMap<>();
        this.notes = new ArrayList<>();
    }
    
    public void addDocument(String type, byte[] data) {
        documents.put(type, data);
    }
    
    public byte[] getDocument(String type) {
        return documents.get(type);
    }
    
    public void addNote(String note) {
        notes.add(note);
    }
    
    // Getters et Setters
    public String getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getNationalId() { return nationalId; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public KYCStatus getStatus() { return status; }
    public void setStatus(KYCStatus status) { this.status = status; }
    public KYCLevel getLevel() { return level; }
    public void setLevel(KYCLevel level) { this.level = level; }
    public long getSubmissionDate() { return submissionDate; }
    public long getVerificationDate() { return verificationDate; }
    public void setVerificationDate(long date) { this.verificationDate = date; }
    public String getVerifiedBy() { return verifiedBy; }
    public void setVerifiedBy(String verifier) { this.verifiedBy = verifier; }
    public Map<String, byte[]> getDocuments() { return documents; }
    public List<String> getNotes() { return notes; }
}
