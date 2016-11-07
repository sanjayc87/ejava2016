/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.epod.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Optional;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import sg.edu.nus.iss.ejava2016.epod.manager.PodManager;
import sg.edu.nus.iss.ejava2016.epod.model.Pod;

/**
 *
 * @author sanja
 */
@WebServlet(urlPatterns = "/upload")
@MultipartConfig
public class UploadResources extends HttpServlet{
    
    @EJB private PodManager podManager;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long podId = new Long(new String(readPart(req.getPart("podId"))));
        String note = new String(readPart(req.getPart("note")));
        byte[] image = readPart(req.getPart("image"));
        Long time = new Long(new String(readPart(req.getPart("time"))));
        
        Optional<Pod> opt = podManager.find(podId.intValue());
        
        System.out.println("opt: "+opt.isPresent());
        
        if(opt.isPresent()){
            Pod pod = opt.get();
            pod.setNote(note);
            pod.setImage(image);
            pod.setDeliveryDate(new Date(time));

            podManager.update(pod);
        }
    }
    
    private byte[] readPart(Part p) throws IOException { 
        byte[] buffer = new byte[1024 * 8]; 
        int sz = 0; 
        try (InputStream is = p.getInputStream()) { 
            BufferedInputStream bis = new BufferedInputStream(is); 
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) { 
                while (-1 != (sz = bis.read(buffer))) baos.write(buffer, 0, sz); 
                buffer = baos.toByteArray(); 
            } 
        }
        return (buffer); 
    }
    
}
