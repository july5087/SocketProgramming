package com.example.socketserver_ex;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService extends Service {
    public ServerService() {}

    @Override
    public void onCreate() {
        super.onCreate();

        ServerThread thread = new ServerThread();
        thread.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO : Return the communication channel to the Service
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class ServerThread extends Thread {
        @Override
        public void run() {
            int port = 5001;

            try {
                ServerSocket server = new ServerSocket(port);
                Log.d("ServerThread", "Server Started");

                while (true) {
                    Socket socket = server.accept();

                    ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                    Object input = instream.readObject();
                    Log.d("ServerThread", "input: " + input);

                    ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                    outstream.writeObject(input + " from server.");
                    outstream.flush();
                    Log.d("ServerThread", "output sent.");

                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
