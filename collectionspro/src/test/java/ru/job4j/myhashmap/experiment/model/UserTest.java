package ru.job4j.myhashmap.experiment.model;

import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class UserTest {
   private Map<User, Object> map = new HashMap<>();

   @Before
   public void setup() {
       User user1 = new User("Ivan", 1, new GregorianCalendar(1987, 6, 20));
       User user2 = new User("Ivan", 1, new GregorianCalendar(1987, 6, 20));
       System.out.println(user1.hashCode());
       System.out.println(user2.hashCode());
       this.map.put(user1, 1);
       this.map.put(user2, 1);
   }


   @Test
    public void whenEqualsAndHashCodeNotOverriden() {
       System.out.println(map);
       // 2 разные ссылки, компилятор воспринимает как 2 разных объекта.
   }

   @Test
    public void whenHashCodeIsOverriden() {
       System.out.println(map);
       // одного hashCode недостаточно для разрешения коллизий, метода equals нету, сравнения не производится.
   }

   @Test
    public void whenOnlyEqualsIsOverriden() {
       System.out.println(map);
       // hashCode разный, компилятор воспринимает как разные объекты, метод equals не задействуется
   }

   @Test
    public void whenEqualAndHashCodeIsOverriden() {
       System.out.println(map);
       // у двух объектов одинаковый hashCode, наступает коллизия, вступает в действие метод equals и сравнивает поля объектов,
       // объекты признаются равнозначными и последний объект затирает предыдущий. В коллекции одно значение.
   }


}