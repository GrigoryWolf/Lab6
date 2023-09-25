package common.core.reader;

import common.core.Invoker;
import common.core.manager.CommandManager;
import common.core.message.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;
/**
 * Класс, который считывает и обрабатывает ввод командной строки в серверном приложении
 * @author grigoryvolkov
 */

public class InputReader implements IReader {
    private Boolean isWorking = false;
    private CommandManager commandManager;
    private Scanner scanner;
    private Invoker invoker;
    private ObjectInputStream in;
    public InputReader(Invoker invoker, ObjectInputStream in){
        this.invoker = invoker;
        this.in = in;
    }
    public InputReader(Invoker invoker){
        this.invoker = invoker;
    }
    @Override
    public void start() {
        isWorking = true;
        commandManager = new CommandManager(invoker);
        while (isWorking) {
            try {
                Request request = (Request) in.readObject();
                if (request == null) {
                    stop();
                }
                commandManager.callCommand(request.getCommand(), request.getArg(), request.getRoute());
            } catch (IOException | ClassNotFoundException e) {
                isWorking = false;
            }
        }
    }
    public void stop(){
        isWorking = false;
    }
    @Override
    public String nextLine() {
        return null;

    }
    public Boolean getWorking(){
        return isWorking;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
