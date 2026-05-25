package com.ingjcfv.rendicash11.utils;

public class UsernameNormalizer {
    private String Username;

    public UsernameNormalizer() {
    }

    public UsernameNormalizer(String username) {
        Username = username;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
    public String normalizeUsername(String username){
        String[] parts = Username.split(" ");
        StringBuilder normalizedUsername = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                normalizedUsername.append(part.toLowerCase());
            }
        }
        return normalizedUsername.toString();
    }
    public String normalizeName(String username){
        String[] parts = Username.split(" ");
        StringBuilder normalizedUsername = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                char firstChar = Character.toUpperCase(part.charAt(0));
                String restOfString = part.substring(1).toLowerCase();
                normalizedUsername.append(firstChar).append(restOfString);
            }
        }
        return normalizedUsername.toString();
    }
}
