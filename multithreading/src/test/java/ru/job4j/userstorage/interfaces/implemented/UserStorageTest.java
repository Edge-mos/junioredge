package ru.job4j.userstorage.interfaces.implemented;

import org.junit.Test;
import ru.job4j.userstorage.User;
import ru.job4j.userstorage.interfaces.Istore;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import static org.junit.Assert.*;

public class UserStorageTest {

    private class ThreadAdd extends Thread {
        private final Istore<User> storage;
        private final User[] users;

        public ThreadAdd(Istore<User> storage, User...users) {
            this.storage = storage;
            this.users = users;
        }

        @Override
        public void run() {
            for (User user : this.users) {
                this.storage.add(user);
            }
        }
    }

    private class ThreadUpdate extends Thread {
        private final Istore<User> storage;
        private final User[] users = new User[2];

        public ThreadUpdate(Istore<User> storage, User user, User updated) {
            this.storage = storage;
            this.users[0] = user;
            this.users[1] = updated;
        }

        @Override
        public void run() {
            this.storage.update(this.users[0], this.users[1]);
        }
    }

    private class ThreadDelete extends Thread {
        private final Istore<User> storage;
        private final User[] users;

        public ThreadDelete(Istore<User> storage, User...users) {
            this.storage = storage;
            this.users = users;
        }

        @Override
        public void run() {
            for (User user : this.users) {
                this.storage.delete(user);
            }
        }
    }

    private class ThreadTransfer extends Thread {
        private final Istore<User> storage;
        private final User[] users = new User[2];
        private final int amount;

        public ThreadTransfer(Istore<User> storage, User from, User to, int amount) {
            this.storage = storage;
            this.users[0] = from;
            this.users[1] = to;
            this.amount = amount;
        }

        @Override
        public void run() {
            this.storage.transfer(this.users[0].getId(), this.users[1].getId(), this.amount);
        }
    }

    @Test
    public void whenExecute2Threads() throws InterruptedException {
        final Istore<User> userStorage = new UserStorage<>();
        Thread thread1 = new ThreadAdd(userStorage, new User(1, 100));
        Thread thread2 = new ThreadAdd(userStorage, new User(2, 200), new User(3, 300));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertThat(userStorage.getStore().get(1), is(new User(1, 100)));
        assertThat(userStorage.getStore().get(3), is(new User(3, 300)));
    }

    @Test
    public void whenAddAndUpdateUsersIn2Threads() throws InterruptedException {
        final Istore<User> userStorage = new UserStorage<>();
        Thread thread1 = new ThreadAdd(userStorage, new User(2, 200), new User(3, 300));
        Thread thread2 = new ThreadUpdate(userStorage, new User(2, 200), new User(22, 22));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertThat(userStorage.getStore().get(2), is(new User(22, 22)));
    }

    @Test
    public void whenAddAndDeleteSameUsersFromStorageIn2ThreadsThanSize0() throws InterruptedException {
        final Istore<User> userStorage = new UserStorage<>();
        Thread thread1 = new ThreadAdd(userStorage, new User(1, 100), new User(2, 200),
                new User(3, 300));
        Thread thread2 = new ThreadDelete(userStorage, new User(1, 100), new User(2, 200),
                new User(3, 300));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertThat(userStorage.getStore().size(), is(0));
    }

    @Test
    public void whenAddAndTransferIn2Threads() throws InterruptedException {
        final Istore<User> userStorage = new UserStorage<>();
        Thread thread1 = new ThreadAdd(userStorage, new User(1, 200), new User(2, 0));
        Thread thread2 = new ThreadTransfer(userStorage, new User(1, 200), new User(2, 0), 100);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(userStorage.getStore().get(1).getAmount(), is(100));
        assertThat(userStorage.getStore().get(2).getAmount(), is(100));
    }

}