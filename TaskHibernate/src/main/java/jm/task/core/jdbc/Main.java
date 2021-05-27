package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String name;
        String lastName;
        byte age;

        List<User>userList = new ArrayList();
        userList.add(new User("Petya", "Petrov", (byte) 30));
        userList.add(new User("Vasya", "Vasiliev", (byte) 23));
        userList.add(new User("Ivan", "Ivanov", (byte) 21));
        userList.add(new User("Gosha", "Goshin", (byte) 23));

        UserService service = new UserServiceImpl();
        service.createUsersTable();
        for (User user : userList) {
            name = user.getName();
            lastName = user.getLastName();
            age = user.getAge();
            service.saveUser(name, lastName, age);
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        }
        List<User> allUsers = service.getAllUsers();

        System.out.println(allUsers);

        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
