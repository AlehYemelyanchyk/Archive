package by.epam.ayem.main.server.service;

/*Задание 3: создайте клиент-серверное приложение "Архив".
    Общие требования к заданию:
    1. В архиве хранятся Дела (например, студентов). Архив находится на сервере.
    2. Клиент, в зависимости от прав, может запросить дело на просмотр, внести в него изменения,
    или создать новое дело.
Требования к коду:
1. Для реализации сетевого соединения используйте сокеты.
2. Формат хранения данных на сервере - xml-файлы.*/

import by.epam.ayem.main.server.service.model.*;

public class UserService {

    public String logIn(UserBase userBase, String[] command) {
        for (User user : userBase.getUsers()) {
            boolean sameUser = isSameUser(command[1], command[2], command[3], user);
            if (sameUser) {
                userBase.setCurrentUser(user);
                return "Welcome, " + user.getName() + ".";
            }
        }
        return "The user has not found.";
    }

    private String logIn(UserBase userBase, String name, String email, String password) {
        for (User user : userBase.getUsers()) {
            boolean sameUser = isSameUser(name, email, password, user);
            if (sameUser) {
                userBase.setCurrentUser(user);
                return "Welcome, " + user.getName() + ".";
            }
        }
        return "The user has not found.";
    }

    private boolean isSameUser(String name, String email, String password, User user) {
        return user.getName().equals(name) &&
                user.getEmail().equals(email) &&
                user.getPassword().equals(String.valueOf(password.hashCode()));
    }

    public String signUp(UserBase userBase, String[] command) {
        if (userBase.getUsers().isEmpty()) {
            userBase.getUsers().add(new User(command[1], command[2], String.valueOf(command[3].hashCode()), UserRole.ADMINISTRATOR));
            return logIn(userBase, command[1], command[2], command[3]);
        }

        User newUser = new User(command[1], command[2], String.valueOf(command[3].hashCode()), UserRole.USER);

        if (isUserExist(userBase, newUser)) {
            userBase.getUsers().add(newUser);
        } else {
            return "A user with such name already exists.";
        }
        return logIn(userBase, command[1], command[2], command[3]);
    }

    private boolean isUserExist(UserBase userBase, User newUser) {
        for (User user : userBase.getUsers()) {
            if (user.equals(newUser))
                return false;
        }
        return true;
    }
}
