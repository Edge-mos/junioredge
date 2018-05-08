package ru.job4j.store.models;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.store.interfases.Store;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class StoreTest {
    private Store<User> userStore;
    private Store<Role> roleStore;

    @Before
    public void setup() {
         this.userStore = new UserStore();
         this.roleStore = new RoleStore();
    }

    @Test
    public void whenAddNewElementsInStoreThanCanFindElementByTheirIds() {
        this.userStore.add(new User("123"));
        assertThat(this.userStore.findById("123").getId(), is("123"));

        this.roleStore.add(new Role("456"));
        assertThat(this.roleStore.findById("456").getId(), is("456"));
    }

    @Test
    public void whenElementInStoreThanCanReplaceItByNewElement() {
        this.userStore.add(new User("123"));
        assertThat(this.userStore.replace("123", new User("321")), is(true));
        assertThat(this.userStore.findById("321").getId(), is("321"));

        this.roleStore.add(new Role("456"));
        assertThat(this.roleStore.replace("456", new Role("654")), is(true));
        assertThat(this.roleStore.findById("654").getId(), is("654"));
    }

    @Test
    public void whenElementNotInStoreThanReturnFalse() {
        this.userStore.add(new User("123"));
        assertThat(this.userStore.replace("777", new User("321")), is(false));

        this.roleStore.add(new Role("456"));
        assertThat(this.roleStore.replace("777", new Role("654")), is(false));
    }

    @Test
    public void whenElementInStoreThanCanDeleteThisElementFromStore() {
        this.userStore.add(new User("123"));
        assertThat(this.userStore.delete("123"), is(true));
        assertThat(this.userStore.delete("123"), is(false));

        this.roleStore.add(new Role("456"));
        assertThat(this.roleStore.delete("456"), is(true));
        assertThat(this.roleStore.delete("456"), is(false));
    }
}