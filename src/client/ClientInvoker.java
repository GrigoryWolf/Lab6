package client;
import common.core.Deserializer;
import common.core.Printer;
import common.core.Serializer;
import common.core.manager.ModelManager;
import common.core.message.*;
import common.core.reader.IReader;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientInvoker{
    private Printer printer;
    private ClientCLIReader reader;
    private RequestManager requestManager;
    private Socket socket;
    private final int BUFFER = 2048;
    private byte[] buffer;
    public ClientInvoker(Socket socket){
        this.socket = socket;
        printer = new Printer();
        reader = new ClientCLIReader(this);
        requestManager = new RequestManager(this);
        this.buffer = new byte[BUFFER];
    }
    public ModelManager getModelManager() {
        return null;
    }

    public void startReading() throws IOException{
        printer.print("Введите команду help для получения списка доступных команд");
        reader.start();
    }
    public Response getDataFromServer() throws IOException {
        try {
            InputStream inputStream = new BufferedInputStream(socket.getInputStream());
            int bytesRead = inputStream.read(buffer);
            if(bytesRead==-1) throw new SocketException();
            byte[] bytes = new byte[bytesRead];
            System.arraycopy(buffer, 0, bytes, 0, bytesRead);
            return Deserializer.deserializeResponse(bytes);
        }
        catch (IOException | ClassNotFoundException exception){throw new IOException();}
    }

    public void sendDataToServer(Request request)  {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStreamWriter = new ObjectOutputStream(byteArrayOutputStream);
            outputStreamWriter.writeObject(request);
            OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
        }catch (IOException ex){
            System.out.println("Ошибка IO");
        }
    }
    public Printer getPrinter() {
        return printer;
    }

    public IReader getReader() {
        return reader;
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }
}