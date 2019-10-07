package by.epam.ayem.main.server;

import by.epam.ayem.main.server.model.Server;

public class ServerRunner {

    public static void main(String[] args) {
        // Создаю новый поток
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                new Server().runServer();
            }
        });
        // Запускаю сервер в новом потоке
        serverThread.start();
    }
}
