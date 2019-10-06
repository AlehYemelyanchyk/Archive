package by.epam.ayem.main.server.service.model;

/*Задание 3: создайте клиент-серверное приложение "Архив".
    Общие требования к заданию:
    1. В архиве хранятся Дела (например, студентов). Архив находится на сервере.
    2. Клиент, в зависимости от прав, может запросить дело на просмотр, внести в него изменения,
    или создать новое дело.
Требования к коду:
1. Для реализации сетевого соединения используйте сокеты.
2. Формат хранения данных на сервере - xml-файлы.*/

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleh Yemelyanchyk on 10/1/2019.
 */
public class StudentsBase {

    List<Student> students;

    public StudentsBase() {
        this.students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
