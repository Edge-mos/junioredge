package ru.job4j.xmlxslt.exeptions;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 29.11.2018.
 */
public class DaoExeption extends Exception {

    public DaoExeption() {
        super();
    }

    public DaoExeption(String message) {
        super(message);
    }

    public DaoExeption(Throwable cause) {
        super(cause);
    }

    public DaoExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
