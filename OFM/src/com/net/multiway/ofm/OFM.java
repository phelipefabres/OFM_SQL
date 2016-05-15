/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm;

import com.net.multiway.ofm.daos.DataGraphicDAO;
import com.net.multiway.ofm.daos.DeviceDAO;
import com.net.multiway.ofm.daos.UserDAO;
import com.net.multiway.ofm.daos.exceptions.IllegalOrphanException;
import com.net.multiway.ofm.entities.Data;
import com.net.multiway.ofm.entities.DataGraphic;
import com.net.multiway.ofm.entities.Device;
import com.net.multiway.ofm.entities.User;
import java.util.Date;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author phelipe
 */
public class OFM extends Application {

    private EntityManagerFactory emf;

    @Override
    public void start(Stage primaryStage) throws IllegalOrphanException, Exception {
        emf = Persistence.createEntityManagerFactory("OFMPU");

        UserDAO userDao = new UserDAO(emf);
        User user = userDao.findUser(1);
        if (user == null) {
            user = new User();
            user.setUsername("admin");
            user.setEmail("teste@vai.dar.certo");
            user.setPassword("13");
            user.setCreateTime(new Date());

            userDao.create(user);
        }
        System.out.println(user.getEmail());

        DeviceDAO deviceDao = new DeviceDAO(emf);
        Device device = deviceDao.findDevice(1);
//        System.out.println(device.toString());
//        System.out.println(device.getParameter().toString());
//        System.out.println(device.getLimit().toString());
//        System.out.println(device.getData().toString());
//        
//        Data data = device.getData();
//
//        DataGraphic dg1 = new DataGraphic(1);
//        dg1.setData(device.getData());
//        DataGraphicDAO dgDao = new DataGraphicDAO(emf);
//        
//        DataGraphic dg2 = new DataGraphic(2);
//        dg2.setData(device.getData());
//        
//        device.getData().getDataGraphicList().add(dg1);
//        device.getData().getDataGraphicList().add(dg2);

//        
//        device.setName("DEVICE_1");
//        device.setIp("123");
//        device.setMask("1");
//        device.setGateway("2");
//        device.setUser(user);
//        device.setCreateTime(new Date());
//        deviceDao.create(device);
//        
//        Parameter parameter = new Parameter(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, new Date());
//        parameter.setUser(user);
//        parameter.setDevice(device);
//        
//        ParameterDAO pdao = new ParameterDAO(emf);
//        pdao.create(parameter);
//        
//        Limit limit = new Limit(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, new Date());
//        limit.setUser(user);
//        limit.setDevice(device);
//        
//        LimitDAO ldao = new LimitDAO(emf);
//        ldao.create(limit);
//        
        Data data = device.getData();
        if (data == null) {
            System.out.println("Fudeu");
            System.exit(0);
        }

        data.setDevice(device);
        System.out.println("Fudeu " + device.getDeviceId());
//        DataDAO ddao = new DataDAO(emf);
//        ddao.create(data);

//        DataGraphic dataGraphic = new DataGraphic();
//        dataGraphic.setValue(1);
//       
        DataGraphicDAO gdao = new DataGraphicDAO(emf);
//        gdao.create(dataGraphic);
//
//        data.getDataGraphicList().add(dataGraphic);

        DataGraphic dataGraphic2 = new DataGraphic();
        dataGraphic2.setValue(5);
        dataGraphic2.setData(data);
        gdao.create(dataGraphic2);
        data.getDataGraphicList().add(dataGraphic2);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
