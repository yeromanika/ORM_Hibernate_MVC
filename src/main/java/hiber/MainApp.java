package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      Car car1 = new Car("Porsche", 911);
      Car car2 = new Car("Mercedes", 777);
      Car car3 = new Car("BMW", 760);

      User user1 = new User("Ваня", "Соколов", "vanya@gmail.com");
      user1.setCar(car1);
      car1.setUser(user1);

      User user2 = new User("Петя", "Петров", "petya@gmail.com");
      user2.setCar(car2);
      car2.setUser(user2);

      User user3 = new User("Стас", "Барецкий", "stas@gmail.com");
      user3.setCar(car3);
      car3.setUser(user3);

      userService.add(user1);
      userService.add(user2);
      userService.add(user3);

      List<User> users = userService.listUsers();
      System.out.println("=== Список пользователей и их автомобилей ===");
      for (User user : users) {
         System.out.printf("Пользователь #%d:%n", user.getId());
         System.out.printf("  Имя: %s %s%n", user.getFirstName(), user.getLastName());
         System.out.printf("  Email: %s%n", user.getEmail());

         if (user.getCar() != null) {
            System.out.printf("  Автомобиль: %s (серия: %d)%n",
                    user.getCar().getModel(),
                    user.getCar().getSeries());
         } else {
            System.out.println("  Автомобиль: нет");
         }
         System.out.println("----------------------------------------");
      }

      User foundUser = userService.getUserByCar("BMW", 760);
      if (foundUser != null) {
         System.out.println("\nНайден пользователь:" + foundUser.getFirstName() + " " + foundUser.getLastName());
         System.out.println("Машина: " + foundUser.getCar().getModel() + " " + foundUser.getCar().getSeries());
      } else {
         System.out.println("Пользователь не найден!");
      }

      context.close();
   }
}
