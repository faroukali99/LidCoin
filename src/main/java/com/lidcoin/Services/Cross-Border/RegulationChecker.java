// ============================================
// 3. Cross-Border - RegulationChecker.java
// ============================================
package com.lidcoin.crossborder;

import java.util.Map;

public class RegulationChecker {
    
    public boolean validate(String fromCountry, String toCountry, double amount,
                           Map<String, String> regulations) {
        
        // Vérifier les sanctions
        if (isSanctioned(fromCountry) || isSanctioned(toCountry)) {
            System.out.println("⚠️ Pays sous sanctions: " + fromCountry + " ou " + toCountry);
            return false;
        }
        
        // Vérifier les limites par pays
        String fromRegulation = regulations.get(fromCountry);
        if (fromRegulation != null && !checkLimit(amount, fromRegulation)) {
            System.out.println("⚠️ Limite dépassée pour " + fromCountry);
            return false;
        }
        
        return true;
    }
    
    private boolean isSanctioned(String country) {
        // Liste de pays sous sanctions (à adapter)
        String[] sanctioned = {"XX", "YY"};
        for (String s : sanctioned) {
            if (s.equals(country)) return true;
        }
        return false;
    }
    
    private boolean checkLimit(double amount, String regulation) {
        // Extraire la limite de la régulation (format: "Limite: 50000 USD/jour")
        try {
            String[] parts = regulation.split(":");
            if (parts.length > 1) {
                String limit = parts[1].trim().split(" ")[0];
                double limitValue = Double.parseDouble(limit);
                return amount <= limitValue;
            }
        } catch (Exception e) {
            return true;
        }
        return true;
    }
}
