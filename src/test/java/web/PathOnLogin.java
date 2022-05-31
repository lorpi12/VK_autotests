package web;

public enum PathOnLogin {
    admin("src/test/resources/json/authAdmin.json"),
    project1_admin("src/test/resources/json/authProjectAdmin.json"),
    hr("src/test/resources/json/authHr.json"),
    publik("src/test/resources/json/authPublic.json");


    private final String path;

    private PathOnLogin(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }


}
