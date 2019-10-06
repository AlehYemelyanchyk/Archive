package com.alehyemelyanchyk.service;

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
public class ClientArchiveApp {

    private Scanner scanner = new Scanner(System.in);
    private ClientService clientService = new ClientService();

    public String enterToArchive() {
        String request = "";
        boolean quit = false;

        while (!quit) {
            System.out.println("Welcome to the library service.");
            System.out.println("1. Log in.\n" +
                    "2. Sign Up.\n" +
                    "0. Quit.");

            int choice = getNumber();

            switch (choice) {
                case 1:
                    String userLine = clientService.logIn();
                    request = "LOG_IN " + userLine;
                    quit = true;
                    break;
                case 2:
                    userLine = clientService.logIn();
                    request = "SIGN_UP " + userLine;
                    quit = true;
                    break;
                case 0:
                    request = "EXIT";
                    quit = true;
                    break;
            }
        }
        return request;
    }

    public String showAdministratorMenu() {
        String request = "";

        System.out.println("1. Show record.\n" +
                "2. Edit record.\n" +
                "3. Create record.\n" +
                "0. Quit.");

        int choice = getNumber();

        switch (choice) {
            case 1:
                String recordLine = clientService.showRecord();
                request = "SHOW " + recordLine + "\n";
                break;
            case 2:
                recordLine = clientService.createRecord();
                request = "EDIT " + recordLine + "\n";
                break;
            case 3:
                recordLine = clientService.createRecord();
                request = "CREATE " + recordLine + "\n";
                break;
            case 0:
                request = "EXIT" + "\n";
        }
        return request;
    }

    public String showUserMenu() {
        String request = "";

        System.out.println("1. Show record.\n" +
                "0. Quit.");

        int choice = getNumber();

        switch (choice) {
            case 1:
                String recordLine = clientService.showRecord();
                request = "SHOW " + recordLine + "\n";
                break;
            case 0:
                request = "EXIT" + "\n";
        }
        return request;
    }

    private int getNumber() {
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
}
