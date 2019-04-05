package zip.demo.user;

public enum UserRole {
    USER;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
