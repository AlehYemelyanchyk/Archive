package by.epam.ayem.main.client.model;

/*Задание 3: создайте клиент-серверное приложение "Архив".
    Общие требования к заданию:
    1. В архиве хранятся Дела (например, студентов). Архив находится на сервере.
    2. Клиент, в зависимости от прав, может запросить дело на просмотр, внести в него изменения,
    или создать новое дело.
Требования к коду:
1. Для реализации сетевого соединения используйте сокеты.
2. Формат хранения данных на сервере - xml-файлы.*/

import by.epam.ayem.main.client.service.ClientArchiveApp;

import java.io.*;
import java.net.Socket;

public class Client {

    private ClientArchiveApp clientArchiveApp = new ClientArchiveApp();

    public void runClient() {
        try (Socket clientSocket = new Socket("127.0.0.1", 8000)) {

            OutputStreamWriter outputWriter =
                    new OutputStreamWriter(
                            clientSocket.getOutputStream());

            BufferedReader inputReader =
                    new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));

            String response = "";
            String sessionID = "";
            String[] responseLogIn = {response, sessionID};

            while (!responseLogIn[0].equalsIgnoreCase("exit") && !responseLogIn[0].contains("Welcome")) {
                String enterRequest = clientArchiveApp.enterToArchive();
                outputWriter.write(enterRequest + "\n");
                outputWriter.flush();
                responseLogIn = inputReader.readLine().split(";");
                System.out.println(responseLogIn[0]);
            }

            while (!response.equalsIgnoreCase("exit")) {
                String request;
                if (responseLogIn[1].equals("ADMINISTRATOR")) {
                    request = clientArchiveApp.showAdministratorMenu();
                } else {
                    request = clientArchiveApp.showUserMenu();
                }
                outputWriter.write(request);
                outputWriter.flush();
                response = inputReader.readLine();
                System.out.println(response);
            }

            outputWriter.close();
            inputReader.close();
        } catch (IOException e) {
            System.out.println("Client error " + e.getMessage());
        }
    }
}
