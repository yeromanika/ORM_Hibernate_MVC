package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
   public static void main(String[] args) {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      Car porsche = new Car("Porsche", 911);
      Car mercedes = new Car("Mercedes", 777);
      Car bmw = new Car("BMW", 760);


      User vanya = new User("Ваня", "Соколов", "vanya@gmail.com");
      vanya.setCar(porsche);

      User petya = new User("Петя", "Петров", "petya@gmail.com");
      petya.setCar(mercedes);

      User stas = new User("Стас", "Барецкий", "stas@gmail.com");
      stas.setCar(bmw);


      userService.add(vanya);
      userService.add(petya);
      userService.add(stas);


      System.out.println("=== Все пользователи и их машины ===");
      userService.listUsers().forEach(user -> {
         System.out.printf(
                 "%s %s -> %s (серия: %d)\n",
                 user.getFirstName(),
                 user.getLastName(),
                 user.getCar().getModel(),
                 user.getCar().getSeries()
         );
      });


      System.out.println("\nПоиск владельца:");
      User owner = userService.getUserByCar("BMW", 760);
      if (owner != null) {
         System.out.printf("Найден: %s %s\n",
                 owner.getFirstName(),
                 owner.getLastName());
      } else {
         System.out.println("Владелец не найден!");
      }

      context.close();
   }
}
