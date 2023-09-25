package client;
import common.core.reader.IReader;
import common.core.manager.CommandManager;

import java.io.IOException;
import java.util.Scanner;

public class ClientCLIReader implements IReader {
    private Boolean isWorking = false;
    private RequestManager requestManager;
    private Scanner scanner;
    private ClientInvoker clientInvoker;
    public ClientCLIReader(ClientInvoker clientInvoker){
        this.clientInvoker = clientInvoker;
    }
    @Override
    public void start() throws IOException {
        scanner = new Scanner(System.in);
        isWorking = true;
        requestManager = new RequestManager(clientInvoker);
        while(isWorking){
            String line = nextLine();
            if (line == null) {
                System.exit(0);
                return;
            }
            requestManager.parseLine(line);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }

        }
    }
    public void stop(){
        isWorking = false;
    }
    @Override
    public String nextLine() {
        System.out.printf(">");
        if (scanner.hasNextLine()){return scanner.nextLine();}
        stop();
        return null;

    }
    public Boolean getWorking(){
        return isWorking;
    }

    @Override
    public CommandManager getCommandManager() {
        return null;
    }
    public RequestManager getRequestManager() {
        return requestManager;
    }

}
