package server;
import common.core.Deserializer;
import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.Printer;
import common.core.Serializer;
import common.core.manager.CommandManager;
import common.core.message.Request;
import common.core.manager.FileManager;
import common.core.message.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ServerInstance {
    private ServerSocketChannel server;
    private ByteBuffer byteBuffer;
    private final int BUFFER = 4096;
    private Invoker invoker;
    private CommandManager commandManager;
    private FileManager fileManager;


    public void run(String arg) throws IOException, ClassNotFoundException, NotValidArgumentsException {
        Printer printer = new Printer();
        String path = arg;
        try {
            fileManager = new FileManager(arg, printer);
        }
        catch (IOException ex){}
        invoker = new Invoker(printer, fileManager);
        commandManager = new CommandManager(invoker);
        Thread consoleThread = new Thread(() -> {
            Invoker invoker = new Invoker(printer, fileManager);
            Scanner commandServer = new Scanner(System.in);
            while (true) {
                printer.print("Для сервера доступны комманды save и exit");
                String commandLine = commandServer.nextLine();
                if ("exit".equals(commandLine)) {
                    try {
                        invoker.getFileManager().collectionSave(invoker.getModelManager().getCollection(), path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    printer.print("До свидания!");
                    System.exit(0);
                    break;
                } else if ("save".equals(commandLine)) {
                    try {
                        invoker.getFileManager().collectionSave(invoker.getModelManager().getCollection(), path);
                        printer.print("Коллекция сохранена!!!");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        consoleThread.start();
        server = ServerSocketChannel.open();
        server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        InetSocketAddress address = new InetSocketAddress(4004);
        server.bind(address);
        server.configureBlocking(false);
        Selector selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);
        while(true){
            if(selector.select()==0) continue;
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = keys.iterator();
            while (selectionKeyIterator.hasNext()){
                SelectionKey key = selectionKeyIterator.next();
                selectionKeyIterator.remove();
                if(!key.isValid()) continue;
                if (key.isAcceptable()) {
                    SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
                    if(channel == null) return;
                    channel.configureBlocking(false);
                    key.attach(channel);
                    channel.register(selector, SelectionKey.OP_READ);
                    selector.wakeup();
                }
                else if(key.isReadable()){
                    byteBuffer = ByteBuffer.allocate(BUFFER);
                    SocketChannel channel = (SocketChannel) key.channel();
                    int bytesRead = channel.read(byteBuffer);
                    if (bytesRead == -1) {
                        key.cancel();
                        channel.close();
                    }
                    else {
                        byte[] bytes = new byte[bytesRead];
                        System.arraycopy(byteBuffer.array(), 0, bytes, 0, bytesRead);
                        Request request = Deserializer.deserializeRequest(bytes);
                        Response response = commandManager.callCommand(request.getCommand(), request.getArg(), request.getRoute());
                        write(key, response);
                    }
                }
            }
        }
    }
    private void write(SelectionKey key, Response response) throws IOException {
        byte[] buffer;
        SocketChannel channel = (SocketChannel) key.channel();
        buffer = Serializer.serializeResponse(response);
        byteBuffer = ByteBuffer.wrap(buffer);
        while(byteBuffer.hasRemaining()){
            channel.write(byteBuffer);
        }
    }
}

