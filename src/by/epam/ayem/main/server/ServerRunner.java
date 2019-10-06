package by.epam.ayem.main.server;

import by.epam.ayem.main.server.service.model.Server;

public class ServerRunner {

    public static void main(String[] args) {
        // Создаю новый поток, запускаю в нем сервер.
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                new Server().runServer();
            }
        });
        serverThread.start();
    }
}
