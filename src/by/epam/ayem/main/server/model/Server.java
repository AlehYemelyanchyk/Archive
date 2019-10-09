package by.epam.ayem.main.server.model;

/*Задание 3: создайте клиент-серверное приложение "Архив".
    Общие требования к заданию:
    1. В архиве хранятся Дела (например, студентов). Архив находится на сервере.
    2. Клиент, в зависимости от прав, может запросить дело на просмотр, внести в него изменения,
    или создать новое дело.
Требования к коду:
1. Для реализации сетевого соединения используйте сокеты.
2. Формат хранения данных на сервере - xml-файлы.*/

import by.epam.ayem.main.server.service.ServerService;

import java.net.*;
import java.io.*;

public class Server {

    public void runServer() {
        // запускаю сервер на порту 8000
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            System.out.println("Server started.");

            while (true) {
                new ServerService(serverSocket.accept()).start();
            }
        } catch (
                IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
