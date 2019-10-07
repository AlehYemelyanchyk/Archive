package by.epam.ayem.main.server.model;

/*Задание 3: создайте клиент-серверное приложение "Архив".
    Общие требования к заданию:
    1. В архиве хранятся Дела (например, студентов). Архив находится на сервере.
    2. Клиент, в зависимости от прав, может запросить дело на просмотр, внести в него изменения,
    или создать новое дело.
Требования к коду:
1. Для реализации сетевого соединения используйте сокеты.
2. Формат хранения данных на сервере - xml-файлы.*/

public class Student {

    private String id;
    private String surname;
    private String name;
    private String groupNumber;

    public Student(String id, String surname, String name, String groupNumber) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.groupNumber = groupNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    @Override
    public String toString() {
        return "Student " + id + ": " + surname + " " + name + ", group: " + groupNumber + ".";
    }
}
