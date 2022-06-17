package ru.lanit.at.dictionaries;

public enum PathOnLogin {
    ADMIN("admin", "asdf"),
    PROJECT1_ADMIN("project1_admin", "hrmhrm1234"),
    HR("hr", "hrmhrm12345"),
    PUBLIC("public", "hrmhrm123456");


    private final String login;
    private final String password;

    private PathOnLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return password;
    }
}
