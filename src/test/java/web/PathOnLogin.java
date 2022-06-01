package web;

public enum PathOnLogin {
    admin("admin"),
    project1_admin("project1_admin"),
    hr("hr"),
    publik("public");


    private final String login;

    private PathOnLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }



}
