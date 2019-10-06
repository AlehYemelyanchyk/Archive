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

/**
 * @author Aleh Yemelyanchyk on 10/6/2019.
 */
public class ArchiveService {

    private ArchiveRepository archiveRepository = new ArchiveRepository();

    public void readWhenStart(UserBase userBase, StudentsBase studentsBase) {
        archiveRepository.readUsersFromFile(userBase, "Users.txt");
        archiveRepository.readStudentsFromFile(studentsBase, "Students.xml");
    }

    public void writeWhenFinish(UserBase userBase, StudentsBase studentsBase) {
        archiveRepository.writeUserToFile(userBase, "Users.txt");
        archiveRepository.writeStudentToFile(studentsBase, "Students.xml");
    }
}
