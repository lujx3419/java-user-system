
import java.time.LocalDateTime;

public class User {

    private final String username;
    private String password;
    private boolean isAdmin;
    private LocalDateTime registerTime;
    

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.registerTime = LocalDateTime.now();
    }

    // 重载 toString：用于写入文件
    @Override
    public String toString() {
        return username + "," + password + "," + isAdmin + "," + registerTime.toString();
    }

    // 静态方法：从字符串创建用户（用于读取文件）
    public static User fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) {
            return null;
        }

        String username = parts[0];
        String password = parts[1];
        boolean isAdmin = Boolean.parseBoolean(parts[2]);
        LocalDateTime registerTime = LocalDateTime.parse(parts[3]);

        User user = new User(username, password, isAdmin);
        user.registerTime = registerTime;
        return user;
    }

    // Getter
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    // 可选 Setter
    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
