// ============================================
// 3. DocumentVerifier.java
// ============================================
class DocumentVerifier {
    private Map<String, List<String>> allowedDocuments;
    
    public DocumentVerifier() {
        this.allowedDocuments = new HashMap<>();
        initializeAllowedDocuments();
    }
    
    private void initializeAllowedDocuments() {
        allowedDocuments.put("BASIC", Arrays.asList("ID_CARD", "PASSPORT"));
        allowedDocuments.put("INTERMEDIATE", Arrays.asList("ID_CARD", "PASSPORT", "PROOF_OF_ADDRESS"));
        allowedDocuments.put("ADVANCED", Arrays.asList("ID_CARD", "PASSPORT", "PROOF_OF_ADDRESS", "BANK_STATEMENT"));
    }
    
    public boolean verifyDocument(byte[] document, String documentType) {
        if (document == null || document.length == 0) {
            System.out.println("Document vide ou invalide");
            return false;
        }
        
        // Vérification basique du type de fichier
        if (!isValidFileType(document)) {
            System.out.println("Type de fichier invalide");
            return false;
        }
        
        // Simulation de vérification OCR/AI
        System.out.println("Vérification du document " + documentType + "...");
        return true;
    }
    
    private boolean isValidFileType(byte[] data) {
        // Vérifier les magic numbers pour PDF, JPEG, PNG
        if (data.length < 4) return false;
        
        // PDF
        if (data[0] == 0x25 && data[1] == 0x50 && data[2] == 0x44 && data[3] == 0x46) {
            return true;
        }
        
        // JPEG
        if (data[0] == (byte)0xFF && data[1] == (byte)0xD8) {
            return true;
        }
        
        // PNG
        if (data[0] == (byte)0x89 && data[1] == 0x50 && data[2] == 0x4E && data[3] == 0x47) {
            return true;
        }
        
        return false;
    }
    
    public boolean verifyIdentity(String nationalId, String fullName) {
        // Simulation de vérification auprès d'une base de données gouvernementale
        System.out.println("Vérification d'identité pour " + fullName + " (ID: " + nationalId + ")");
        return nationalId != null && !nationalId.isEmpty() && fullName != null && !fullName.isEmpty();
    }
}
