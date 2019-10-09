package by.epam.ayem.main.server.service;

import by.epam.ayem.main.server.model.StudentsBase;
import by.epam.ayem.main.server.model.User;
import by.epam.ayem.main.server.model.UserBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @author Aleh Yemelyanchyk on 10/8/2019.
 */
public class ServerService extends Thread {

    private ArchiveService archiveService = new ArchiveService();
    private UserService userService = new UserService();
    private UserBase userBase = new UserBase();
    private StudentsBase studentsBase = new StudentsBase();
    private StudentsBaseService studentsBaseService = new StudentsBaseService();
    private Socket socket;

    public ServerService(Socket socket) {
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

                archiveService.readWhenStart(userBase, studentsBase);

                do {
                    request = inputReader.readLine();

                    User user = enterToArchive(request);
                    if (user != null) {
                        response = "Welcome, " + user.getName() + ";" + user.getRole();
                        outputWriter.write(response + "\n");
                        outputWriter.flush();
                        break;
                    } else {
                        response = "exit;exit";
                        outputWriter.write(response + "\n");
                        outputWriter.flush();
                    }
                } while (true);

                while (true) {
                    request = inputReader.readLine();
                    response = showMenu(request);

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

    public User enterToArchive(String request) {
        String[] command = request.split(" ");
        switch (command[0]) {
            case "LOG_IN":
                return userService.logIn(userBase, command);
            case "SIGN_UP":
                return userService.signUp(userBase, command);
            case "EXIT":
                return null;

        }
        return null;
    }

    public String showMenu(String request) {
        String[] command = request.split(" ");
        switch (command[0]) {
            case "SHOW":
                return studentsBaseService.showRecord(command, studentsBase);
            case "EDIT":
                return studentsBaseService.editRecord(command, studentsBase);
            case "CREATE":
                return studentsBaseService.createRecord(command, studentsBase);
            case "EXIT":
                archiveService.writeWhenFinish(userBase, studentsBase);
                return "exit";

        }
        return "Unknown command";
    }
}
