package com.example.tcpip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.msg.Msg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    TextView tx_list, tx_msg;
    EditText et_ip, et_msg;

    int port;
    String address;
    String id;
    Socket socket;
    Sender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_list = findViewById(R.id.tx_list);
        tx_msg = findViewById(R.id.tx_msg);
        et_ip = findViewById(R.id.et_ip);
        et_msg = findViewById(R.id.et_msg);
        port = 5555;
        address = "192.168.0.24"; // 본인 ip
        id ="[Chae]";
        //시작되자마자 접속자 리스트를 가져오자

        new Thread(con).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 종료시, 끊는다.
        try{
            // q를 입력하면 종료로 하였음
            Msg msg = new Msg(null,id,"q");
            sender.setMsg(msg);
            new Thread(sender).start();
            if(socket != null){
                socket.close();
            }
            finish();
            onDestroy();
        }catch (Exception e){

        }
    } // back 버튼이 눌렸을때

    //TCPIP의 코드를 가져와야함
    //Thread를 해야함.

    Runnable con = new Runnable() {
        @Override
        public void run() {
            try {
                connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public void connect() throws IOException  {
        //소캣을 만듬
        try {
            socket = new Socket(address,port);
        } catch (Exception e) {
            while(true) {
                try {
                    Thread.sleep(2000);
                    socket = new Socket(address,port);
                    break;
                }catch(Exception e1) {
                    System.out.println("Retry...");
                }

            }
        }  // exception을 내부 처리 할 것임. 무한루프를 통해 계속 접속 시도

        System.out.println("Connected Server:"+address);
        sender = new Sender(socket); //쓰래드 객체의 틀만 만든 것.
        new Receiver(socket).start(); //리시버를 만든다.
        getList();//커넥트 바로 다음 getList호출
    }

    private void getList(){
        Msg msg = new Msg(null,"[Chaegueavara]","1");
        sender.setMsg(msg);
        new Thread(sender).start();
    }
    public void clickBt(View v){
        String ip = et_ip.getText().toString();
        String msg = et_msg.getText().toString();
        Log.d("[id:]","[id:]"+ip);
        ArrayList<String> ips =new ArrayList<>();
        if(ip.equals("") || ip == null){
            ips = null;
        }else{
            ips.add(ip);
            msg = "[귓속말]["+ip+"에게]" + msg;
        }
        Msg msgObj = new Msg(ips,"[ChaePhone]",msg);
        sender.setMsg(msgObj);
        new Thread(sender).start();
        et_msg.setText("");


    }

    class Receiver extends Thread{
        //서버에서 받음
        //소켓이 필요하다. 데이터를 받기 위해
        ObjectInputStream oi;
        public Receiver(Socket socket) throws IOException {
            oi = new ObjectInputStream(socket.getInputStream());
        }
        @Override
        public void run() {
            while(oi != null) {
                Msg msg = null;
                try {
                    msg = (Msg) oi.readObject();
                    if(msg.getMaps() != null) { //Key값을 꺼낸다.
                        HashMap<String,Msg> hm = msg.getMaps();
                        Set<String> keys = hm.keySet();
                        for(final String k: keys) {
                            //서브 스레드는 메인을 바꿀수 없으니까...
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String tx = tx_list.getText().toString();
                                    tx_list.setText(tx+k+"\n");
                                }
                            });
                            System.out.println(k);
                        }
                        continue;
                    }
                    final Msg finalMsg = msg;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String tx = tx_msg.getText().toString();
                            tx_msg.setText(tx+ finalMsg.getId()+ finalMsg.getMsg()+"\n");
                        }
                    });
                    System.out.println(msg.getId()+msg.getMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            } //end while
            try {
                if(oi!=null) {
                    oi.close();
                }
                if(socket!=null) {
                    socket.close();
                }
            }catch(Exception e) {

            }
        }

    }

    class Sender implements Runnable{
        //서버로 보냄, 틀이다
        Socket socket;
        ObjectOutputStream oo;
        Msg msg;
        public Sender(Socket socket) throws IOException {
            this.socket = socket;
            oo = new ObjectOutputStream(socket.getOutputStream());
        }
        public void setMsg(Msg msg) {
            this.msg = msg;
        }
        @Override
        public void run() {
            if(oo != null) {
                try {
                    oo.writeObject(msg); // exception은 서버가 꺼져있을 확률이 큼
                } catch (IOException e) {
                    //e.printStackTrace();
                    try {
                        if(socket!=null) {
                            socket.close();
                        }
                    }catch(Exception e1) {
                        e1.printStackTrace();
                    }
                    try {
                        Thread.sleep(2000);
                        connect();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }//다시 커넥트
                }
            }
        }
    }
}