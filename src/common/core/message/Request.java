package common.core.message;
import common.core.model.Route;
import java.io.Serializable;

public class Request implements Serializable{
    private String command;
    private String arg;
    private Route route;
    public Request(String command, String arg, Route route) {
        this.command = command;
        this.arg = arg;
        this.route = route;
    }

    public String getCommand() {
        return command;
    }
    public String getArg(){
        return arg;
    }
    public Route getRoute(){return route;}
}
