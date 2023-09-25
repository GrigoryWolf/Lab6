package server;
import common.core.Exceptions.NotValidArgumentsException;

import java.io.IOException;

public class ServerApp {

    public static void main(String[] args) {
        try {
            ServerInstance serverInstance = new ServerInstance();
            serverInstance.run("data.json");
        }
        catch(IOException | ClassNotFoundException | NotValidArgumentsException e) {
            throw new RuntimeException(e);
        }
    }
}
