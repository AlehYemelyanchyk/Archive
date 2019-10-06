package by.epam.ayem.main.server.service.model;

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

    private ServerService serverService = new ServerService();

    public void runServer() {
        // запускаю сервер на порту 8000
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            System.out.println("Server started.");

            while (true) {

                // ожидаю подключения к сокету под именем - "client" на серверной стороне
                Socket clientSocket = serverSocket.accept();

                // сервер выводит сообщение о подключившемся клиенте с этим сокет-соединением.
                System.out.println("Client accepted.");

                // инициирую каналы для общения в сокете для сервера

                // канал записи в сокет
                OutputStreamWriter outputWriter =
                        new OutputStreamWriter(
                                clientSocket.getOutputStream());

                // канал чтения из сокета
                BufferedReader inputReader = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));

                // начинаю диалог с подключенным клиентом в цикле, пока сокет не закрыт

                String response;
                String request;
                serverService.readWhenStart();

                do {
                    request = inputReader.readLine();

                    response = serverService.enterToArchive(request);
                    outputWriter.write(response + "\n");
                    outputWriter.flush();
                } while (!response.contains("Welcome"));

                while (true) {
                    request = inputReader.readLine();
                    response = serverService.showMenu(request);

                    if (response.equals("exit")) {
                        outputWriter.write(response + "\n");
                        outputWriter.flush();
                        break;
                    }
                    outputWriter.write(response + "\n");
                    outputWriter.flush();
                }


                outputWriter.close();
                inputReader.close();
                clientSocket.close();
            }
        } catch (
                IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
