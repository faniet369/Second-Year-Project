package lettery.models;

import java.util.Date;

public class Account implements Comparable{
    private String username;
    private String password;
    private String role;
    private String name;
    private Date lastLogin = new Date(0);

    public Account(String username, String password, String role, String name, Date lastLogin) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.lastLogin = lastLogin;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "admin";
    }

    public Account(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.role = "officer";
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public String getStringLastLogin() {
        lettery.models.Date date = new lettery.models.Date();
        return date.formatDate(lastLogin);
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return name+" "+password+" "+role;
    }

    @Override
    public int compareTo(Object o) {
        Account other = (Account) o;
        return Integer.compare(lastLogin.compareTo(other.getLastLogin()), 0);
    }

}
