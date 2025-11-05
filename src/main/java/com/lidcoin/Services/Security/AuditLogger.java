package com.lidcoin.security;

import java.util.*;
import java.time.Instant;

class AuditLogger {
    private List<AuditEntry> auditLog;
    
    private static class AuditEntry {
        String userId;
        String action;
        String ipAddress;
        long timestamp;
        
        AuditEntry(String userId, String action, String ipAddress) {
            this.userId = userId;
            this.action = action;
            this.ipAddress = ipAddress;
            this.timestamp = Instant.now().toEpochMilli();
        }
    }
    
    public AuditLogger() {
        this.auditLog = new ArrayList<>();
    }
    
    public void log(String userId, String action, String ipAddress) {
        auditLog.add(new AuditEntry(userId, action, ipAddress));
        System.out.println("📝 AUDIT: " + action + " - " + userId + " from " + ipAddress);
    }
    
    public List<AuditEntry> getAuditLog(String userId) {
        return auditLog.stream()
            .filter(e -> e.userId.equals(userId))
            .toList();
    }
}
