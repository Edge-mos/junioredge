package ru.job4j.executorservice;

/**User.
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 30.07.2018.
 */
public class User {
    private String username;
    private String email;

    public User(String username) {
        this.username = username;
        this.email = this.username + "@gmail.com";
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
