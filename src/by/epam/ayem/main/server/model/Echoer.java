package by.epam.ayem.main.server.model;

import by.epam.ayem.main.server.service.ServerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @author Aleh Yemelyanchyk on 10/8/2019.
 */
public class Echoer extends Thread {

    private Socket socket;
    private ServerService serverService = new ServerService();

    public Echoer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Server started.");

            // инициирую каналы для общения в сокете для сервера

            // канал записи в сокет
            OutputStreamWriter outputWriter =
                    new OutputStreamWriter(
                            socket.getOutputStream());

            // канал чтения из сокета
            BufferedReader inputReader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            while (true) {

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
            }
        } catch (
                IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
