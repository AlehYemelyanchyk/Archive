package by.epam.ayem.main.server.service;

/*Задание 3: создайте клиент-серверное приложение "Архив".
    Общие требования к заданию:
    1. В архиве хранятся Дела (например, студентов). Архив находится на сервере.
    2. Клиент, в зависимости от прав, может запросить дело на просмотр, внести в него изменения,
    или создать новое дело.
Требования к коду:
1. Для реализации сетевого соединения используйте сокеты.
2. Формат хранения данных на сервере - xml-файлы.*/

import by.epam.ayem.main.server.service.model.Student;
import by.epam.ayem.main.server.service.model.StudentsBase;

/**
 * @author Aleh Yemelyanchyk on 10/2/2019.
 */
public class StudentsBaseService {

    public String showRecord(String[] command, StudentsBase studentsBase) {
        String record = "";
        for (Student student : studentsBase.getStudents()) {
            if (student.getId().equals(command[1])) {
                record = student.toString();
                break;
            }
        }
        return record;
    }

    public String editRecord(String[] command, StudentsBase studentsBase) {
        for (Student student : studentsBase.getStudents()) {
            if (student.getId().equals(command[1])) {
                student.setSurname(command[2]);
                student.setName(command[3]);
                student.setGroupNumber(command[4]);
                break;
            }
        }
        return "Record was edited";
    }

    public String createRecord(String[] command, StudentsBase studentsBase) {
        studentsBase.getStudents().add(new Student(command[1], command[2], command[3], command[4]));
        return "Record was created";
    }
}
