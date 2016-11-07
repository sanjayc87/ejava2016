/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.epod.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import sg.edu.nus.iss.ejava2016.epod.manager.DeliveryManager;
import sg.edu.nus.iss.ejava2016.epod.manager.PodManager;
import sg.edu.nus.iss.ejava2016.epod.model.Delivery;
import sg.edu.nus.iss.ejava2016.epod.model.Pod;
import sg.edu.nus.iss.ejava2016.epod.utils.SessionUtils;

/**
 *
 * @author Joshua
 */
@RequestScoped
@Named
public class DeliveryView {
    
    @EJB private DeliveryManager deliveryManager;
    @EJB private PodManager podManager;
    
    private String name;
    private String address;
    private String phone;
    private Date createDate=new Date();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void add() throws IOException{
        Delivery delivery=new Delivery();
        delivery.setName(name);
        delivery.setAddress(address);
        delivery.setPhone(phone);
        delivery.setCreateDate(createDate);
        
        Integer id = deliveryManager.add(delivery);
        
        Pod pod = new Pod();
        pod.setPkgId(deliveryManager.find(id).get());
        pod.setDeliveryDate(createDate);
        podManager.add(pod);
        
        SessionUtils.getExternalContext()
                .redirect(SessionUtils.getRequestContextPath()
                        +"/faces/index.xhtml?faces-redirect=true");
    }
}
