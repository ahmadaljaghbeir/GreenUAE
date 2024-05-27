package org.example.greenuae.util;

import org.springframework.context.annotation.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class PointAllocator {
    public int determinePoints(String impactCategory) {
        switch (impactCategory.toLowerCase()) {
            case "very low":
                return 10;
            case "low":
                return 20;
            case "medium":
                return 30;
            case "big":
                return 40;
            case "significant":
                return 50;
            default:
                return 0;
        }
    }

    public String extractImpactCategory(String treeInfo) {
        Pattern pattern = Pattern.compile("\\b(very low|low|medium|big|significant)\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(treeInfo);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "unknown";
        }
    }
}

