package ru.job4j.executorservice;

/**EmailNotification<T extends User>.
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 30.07.2018.
 */
public class EmailNotification<T extends User> {

    public void emailTo(T obj) {
        String subject = String.format("Notification %s to email %s", obj.getUsername(), obj.getEmail());
        String body = String.format("Add a new event to %s", obj.getUsername());
        this.send(subject, body, obj.getEmail());
    }

    private void send(String subject, String body, String email) {
        System.out.println(email);
        System.out.println(subject);
        System.out.println(body);
        System.out.println();
    }
}
