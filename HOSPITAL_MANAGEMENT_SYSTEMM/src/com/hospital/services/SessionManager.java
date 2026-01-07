package com.hospital.services;

public class SessionManager {
    private static SessionManager instance;
    private String currentUserId;
    private String currentUserName;
    private String currentUserRole;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(String userId, String userName, String role) {
        this.currentUserId = userId;
        this.currentUserName = userName;
        this.currentUserRole = role;
    }

    public void logout() {
        this.currentUserId = null;
        this.currentUserName = null;
        this.currentUserRole = null;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public String getCurrentUserRole() {
        return currentUserRole;
    }

    public boolean isLoggedIn() {
        return currentUserId != null;
    }
}