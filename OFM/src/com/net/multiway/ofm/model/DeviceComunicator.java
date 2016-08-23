package com.net.multiway.ofm.model;

import com.net.multiway.ofm.MainApp;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.net.multiway.ofm.entities.StopTest;
import com.net.multiway.ofm.entities.Device;
import com.net.multiway.ofm.entities.Parameter;
import com.net.multiway.ofm.receive.ReceiveParameters;
import com.net.multiway.ofm.receive.ReceiveValues;
import com.net.multiway.ofm.receive.ReceiveStatus;
import com.net.multiway.ofm.send.SendParameters;
import com.net.multiway.ofm.send.SendStopTest;
import com.net.multiway.ofm.send.SendDevice;
import com.net.multiway.ofm.send.SendConfirmationSignal;
import com.net.multiway.ofm.utils.Utils;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceComunicator {

    private int door;
    private String ip;

    private DataInputStream in;
    private DataOutputStream out;
    private Socket client;
    private ReceiveStatus receiveStatusData;
    private ReceiveValues receiveValues;
    private ReceiveParameters receiveParametersData;

    public DeviceComunicator(String i, int d) throws IOException {
        this.door = d;
        this.ip = i;
        this.client = new Socket();
        this.client.connect(new InetSocketAddress(this.ip, this.door), 2000);
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());

        receiveValues = new ReceiveValues(this.in);
    }

    public int getDoor() {
        return door;
    }

    public void setDoor(int door) {
        this.door = door;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void initialize() throws Exception {
        try {
            //this.client = new Socket();
            String msg = "Connected to OTDR.";
            
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
        } catch (Exception ex) {
            throw new Exception("Socket could not connect to the host " + this.ip + ".", ex);
        }
    }

    private void sendPackage(Parameter data) throws IOException {
        SendParameters s = new SendParameters(this.out, data);
        s.sender();
    }

    private void sendPackage(StopTest data) throws IOException {
        SendStopTest s = new SendStopTest(this.out, data);
        s.sender();
    }

    private void sendPackage(Device data) throws IOException {
        SendDevice s = new SendDevice(this.out, data);
        s.sender();
    }

    private void sendPackage() throws IOException {
        SendConfirmationSignal s = new SendConfirmationSignal(this.out);
        s.sender();
    }

    private boolean receivePackage() throws Exception {

        try {
            this.client.setSoTimeout(30000);
            String msg = "ignorando bytes inuteis...";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
            // ignorando bytes inuteis
            byte[] b = new byte[60];
            this.in.read(b);

            msg = "ignorando cabecario...";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
            // ignorando cabecario
            b = new byte[44];
            this.in.read(b);

            byte[] CMcode = new byte[4];
            byte[] DataLen = new byte[4];
            this.in.read(CMcode);
            this.in.read(DataLen);
            msg = "Recebendo Pacote...";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);

            // codigo de resposta padao
            System.out.println("Bytes sobrando = " + this.in.available());

            if (Utils.byte4ToInt(CMcode) == 0xA0000000) {
                receiveStatusData = new ReceiveStatus(this.in, Utils.byte4ToInt(DataLen));
                receiveStatusData.parser();
                msg = "Package, code = " + Utils.byte4ToInt(CMcode) + " .";
                Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                return true;
            } else if (Utils.byte4ToInt(CMcode) == 0x90000001) {
                receiveValues.setInputStream(this.in);
                receiveValues.parser();
                msg = "Package, code = " + Utils.byte4ToInt(CMcode) + " .";
                Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                return true;
            } else if (Utils.byte4ToInt(CMcode) == 0x90000000) {
                receiveParametersData = new ReceiveParameters(this.in);
                receiveParametersData.parser();
                msg = "Package, code = " + Utils.byte4ToInt(CMcode) + " .";
                Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                return false;
            } else {
                msg = "ERRO ao receber pacote com code = " + Utils.byte4ToInt(CMcode) + " .";
                Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                return false;
            }

        } catch (Exception ex) {
            throw new Exception("Error while reading package.", ex);
        }

    }

    public void connect(Parameter data) throws Exception {

//        this.initialize();
        try {
            sendPackage(data);
            boolean flag = true;
            int i = 0;
            while (flag) {
                String msg = "Receiving frame " + i + ".";
                Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                i++;

                flag = receivePackage();
                msg = "Frame Received" + i + ".";
                Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);

                sendPackage();
                msg = "Package Send" + i + ".";
                Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);

            }
        } catch (Exception ex) {
            Logger.getLogger(DeviceComunicator.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
//        finally {
//            this.client.close();
//        }

    }

    public void connect(StopTest data) throws Exception {

        this.initialize();
        try {

            sendPackage(data);
            sendPackage();

        } catch (Exception ex) {
            Logger.getLogger(DeviceComunicator.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            this.client.close();
        }
    }

    public void connect(Device data) throws Exception {

        this.initialize();
        try {

            sendPackage(data);
            sendPackage();

        } catch (Exception ex) {
            Logger.getLogger(DeviceComunicator.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            this.client.close();
        }
    }

    public ReceiveValues getReceiveValues() {
        return this.receiveValues;
    }

    public ReceiveParameters getReceiveParametersData() {
        return receiveParametersData;
    }

    public void runMonitor(int time, Parameter data) throws Exception {
        connect(data);
        long ti = System.currentTimeMillis();
        long tf = ti + (time * 1000);
        while (true) {
            System.out.println(tf - ti);
            if (ti > tf) {
                connect(data);
                ti = System.currentTimeMillis();
                tf = ti + (time * 1000);
            }
            ti = System.currentTimeMillis();
        }
    }

    public void closeConnection() throws Exception {
        try {
            this.out.flush();
            this.out.close();
            this.in.close();
            this.client.close();
            String msg = "Connection terminated with OTDR.";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
        } catch (IOException ex) {
            throw new Exception("Socket could not terminated the host connection!" + this.ip + ".", ex);
        }
    }

}
