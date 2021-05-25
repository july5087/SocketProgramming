package com.example.socketclient_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    TextView tvText;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.bt_start);
        tvText = findViewById(R.id.tv_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientThread thread = new ClientThread();
                thread.start();
            }
        });
    }

    class ClientThread extends Thread {
        @Override
        public void run() {
            String host = "localhost";
            int port = 5001;

            try {
                // 1. 클라이언트에서는 서버 주소와 port 번호를 통해 Socket 객체 생성
                // (서버에서는 socket을 accept() 함수를 통해 받아옴)
                Socket socket = new Socket(host, port);

                // 2. 서버는 데이터를 받은 뒤 보냈지만, 클라이언트는 데이터를 보낸 뒤 받도록 구현.
                // OutputStream에 전송할 데이터를 담아 보낸 뒤, InputStream을 통해 데이터를 읽어 로그 찍음
                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                outstream.writeObject("Hello!");
                outstream.flush();
                Log.d("ClientThread", "Sent to server.");

                // 3.Socket에서 InputStream을 받아와 객체에 넣어준 뒤 readObject()를 통해 데이터 읽어와 로그 찍음
                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                Object input = instream.readObject();
                Log.d("ClientThread", "Received data: " + input);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvText.setText(input.toString());
                    }
                });

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}