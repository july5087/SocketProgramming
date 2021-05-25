package com.example.socketserver_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.bt_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ServerThread thread = new ServerThread();
//                thread.start();
                Intent intent = new Intent(MainActivity.this, ServerService.class);
                startService(intent); // ServerService 시작
            }
        });
    }


    // Server가 동작하는 부분을 Activity에서 구현할 경우 앱이 background로 이동 시 종료될 가능성이 있다.
    // 따라서 종료되지 않는 Service로 구현하는 것이 좋다. => ServerService.java에 구현

    /*class ServerThread extends Thread {
        @Override
        public void run() {
            int port = 5001;

            try {
                // 1. ServerSocket에 port번호 넣어 객체 생성
                // 포트번호는 이미 사용중인 번호이면 안되고, 클라이언트와 통신을 위해 같은 번호를 사용해야 한다.
                ServerSocket server = new ServerSocket(port);

                Log.d("ServerThread", "Server Started");

                // 2. accept 함수 통해 클라이언트로부터 데이터 수신 대기
                // 데이터가 들어오면 accept() 함수 끝나고 다음코드 실행
                while(true){
                    Socket socket = server.accept();

                    // 3. socket에서 InputStream을 받아와 객체에 넣어준 뒤 readObject()를 통해 데이터 읽어와 로그 찍음
                    ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                    Object input = instream.readObject();
                    Log.d("ServerThread", "input: " + input);

                    // 4. OutputStream에서 다시 writeObject()를 통해 데이터를 써줌
                    ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                    outstream.writeObject(input + " from server.");
                    // 5. flush()통해 버퍼에 담긴 정보를 보냄
                    outstream.flush();
                    Log.d("ServerThread", "output sent.");
                    // 6. 소켓 닫기
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }*/
}