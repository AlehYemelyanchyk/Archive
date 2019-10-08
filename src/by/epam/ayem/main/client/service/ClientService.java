package by.epam.ayem.main.client.service;

/*Задание 3: создайте клиент-серверное приложение "Архив".
    Общие требования к заданию:
    1. В архиве хранятся Дела (например, студентов). Архив находится на сервере.
    2. Клиент, в зависимости от прав, может запросить дело на просмотр, внести в него изменения,
    или создать новое дело.
Требования к коду:
1. Для реализации сетевого соединения используйте сокеты.
2. Формат хранения данных на сервере - xml-файлы.*/

import java.util.Scanner;

/**
 * @author Aleh Yemelyanchyk on 10/5/2019.
 */
public class ClientService {

    private Scanner scanner = new Scanner(System.in);

    public String logIn() {
        System.out.println("Enter a name:");
        String name = scanner.nextLine();
        System.out.println("Enter an e-mail:");
        String email = scanner.nextLine();
        System.out.println("Enter a password:");
        String password = scanner.nextLine();

        return name + " " + email + " " + password;
    }

    public String showRecord() {
        System.out.println("Enter student's id:");
        return scanner.nextLine();
    }

    public String createRecord() {
        System.out.println("Enter student's id:");
        String id = scanner.nextLine();
        System.out.println("Enter student's surname:");
        String surname = scanner.nextLine();
        System.out.println("Enter student's name:");
        String name = scanner.nextLine();
        System.out.println("Enter group number:");
        String groupNumber = scanner.nextLine();
        return id + " " + surname + " " + name + " " + groupNumber;
    }
}
