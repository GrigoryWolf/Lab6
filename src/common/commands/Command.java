package common.commands;

import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;

import java.io.IOException;
import java.io.Serializable;

/**
 * Абстрактный класс содержит базовые методы для реализации команд
 * @author grigoryvolkov
 */
public abstract class Command implements Serializable {
    private Invoker invoker;
    public Command(Invoker invoker){
        this.invoker = invoker;
    }
    public abstract Response execute(String arg, Route route) throws NotValidArgumentsException, IOException;
    public abstract String getDescription();
    public Invoker getInvoker(){
        return invoker;
    }
    public void argsСheck(TypeArg typeArg, String arg, Route route) throws NotValidArgumentsException {
        switch (typeArg){
            case NOARGUMENT -> {if(arg != null || route != null)throw new NotValidArgumentsException();}
            case ROUTE -> {if (route == null || arg != null) throw new NotValidArgumentsException();}
            case STRINGARGUMENT -> {if (route != null || arg == null) throw new NotValidArgumentsException();}
            case BOTH -> {if (route == null || arg == null) throw new NotValidArgumentsException();}
        }
    }
    public enum TypeArg{
        NOARGUMENT,
        ROUTE,
        STRINGARGUMENT,
        BOTH
    }
}
