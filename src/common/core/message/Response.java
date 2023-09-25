package common.core.message;

import java.io.Serializable;

public class Response implements Serializable {
    private String response;
    private boolean result;
    public Response(String response, boolean result){
        this.response = response;
        this.result = result;
    }
    public Response(String response){
        this.response = response;
        this.result = true;
    }
    @Override
    public String toString() {
        return "Ответ сервера:\n" + response ;
    }
    public boolean getResult(){return result;}
}
