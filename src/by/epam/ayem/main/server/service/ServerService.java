package by.epam.ayem.main.server.service;

/*Задание 3: создайте клиент-серверное приложение "Архив".
    Общие требования к заданию:
    1. В архиве хранятся Дела (например, студентов). Архив находится на сервере.
    2. Клиент, в зависимости от прав, может запросить дело на просмотр, внести в него изменения,
    или создать новое дело.
Требования к коду:
1. Для реализации сетевого соединения используйте сокеты.
2. Формат хранения данных на сервере - xml-файлы.*/

import by.epam.ayem.main.server.model.StudentsBase;
import by.epam.ayem.main.server.model.UserBase;

/**
 * @author Aleh Yemelyanchyk on 10/1/2019.
 */
public class ServerService {

    private ArchiveService archiveService = new ArchiveService();
    private UserService userService = new UserService();
    private UserBase userBase = new UserBase();
    private StudentsBase studentsBase = new StudentsBase();
    private StudentsBaseService studentsBaseService = new StudentsBaseService();

    public void readWhenStart() {
        archiveService.readWhenStart(userBase, studentsBase);
    }

    public String enterToArchive(String request) {
        String[] command = request.split(" ");
        switch (command[0]) {
            case "LOG_IN":
                return userService.logIn(userBase, command) + ";" + userBase.getCurrentUser().getRole();
            case "SIGN_UP":
                return userService.signUp(userBase, command);
            case "EXIT":
                archiveService.writeWhenFinish(userBase, studentsBase);
                return "exit";

        }
        return "Unknown command";
    }

    public String showMenu(String request) {
        String[] command = request.split(" ");
        switch (command[0]) {
            case "SHOW":
                return studentsBaseService.showRecord(command, studentsBase);
            case "EDIT":
                return studentsBaseService.editRecord(command, studentsBase);
            case "CREATE":
                return studentsBaseService.createRecord(command, studentsBase);
            case "EXIT":
                archiveService.writeWhenFinish(userBase, studentsBase);
                return "exit";

        }
        return "Unknown command";
    }
}
