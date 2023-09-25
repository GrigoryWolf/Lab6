package client;

import common.core.Invoker;
import common.core.Printer;
import common.core.message.Response;

import java.io.*;
import java.net.Socket;

public class ClientInstance {
    public void run(){
        Printer printer = new Printer();
        try{
            Socket clientSocket = new Socket("localhost", 4004);
            ClientInvoker clientInvoker = new ClientInvoker(clientSocket);
            clientInvoker.startReading();
        } catch (IOException e) {
            printer.print("Попытка переподключения к серверу...");
            tryReconnect();
        }
    }
    public void tryReconnect(){
        Printer printer = new Printer();
        boolean connected = false;
        int counter = 0;
        while (!connected) {
            try {
                Socket clientSocket = new Socket("localhost", 4004);
                connected = true;
                counter = 0;
                ClientInvoker clientInvoker = new ClientInvoker(clientSocket);
                clientInvoker.startReading();
            } catch (IOException ex) {
                if(counter>=5){
                    printer.print("Превышено время ожидания ответа!");
                    System.exit(1);
                }
                printer.print("Ошибка подключения! Пробуем подключиться, пожалуйста, ожидайте ответа");
                counter+=1;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    printer.print("Ошибка при приостановке потока!");
                }

            }
        }
    }
}

