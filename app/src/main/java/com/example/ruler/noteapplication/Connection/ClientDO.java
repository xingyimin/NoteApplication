package com.example.ruler.noteapplication.Connection;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.ruler.noteapplication.MainActivity;

import java.net.DatagramPacket;
import java.net.Proxy;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientDO {

    private static final ThreadFactory sThreadFactory=new ThreadFactory(){
        private final AtomicInteger mCount=new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return null;
        }
    };

    public void sendPakage(){
//        构造数据包
//        DatagramPacket packet = new DatagramPacket(MyMsg.getBytes(),
//                MyMsg.getBytes().length, InetAddress.getByName(TinyKingApp
//                .getInstance().getSPUtil().getUDPAddress()), 3333);
//        DatagramSocket socket = new DatagramSocket();
//        socket.send(packet);
//        socket.close();
//
    }


}
