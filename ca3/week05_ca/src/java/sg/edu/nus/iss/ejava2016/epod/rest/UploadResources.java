/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.epod.rest;

import com.sun.xml.ws.xmlfilter.Invocation;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
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
            
            pushToHQ(pod);
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
    
    private void pushToHQ(Pod pod) throws IOException{
        Client client = ClientBuilder.newBuilder()
            .register(MultiPartFeature.class).build();
        WebTarget webTarget = client.target("http://10.10.0.50:8080/epod/upload");
        MultiPart multiPart = new MultiPart();
        
      
        try {
                 FileOutputStream fos = new FileOutputStream("\\week05_ca");
                 String strContent = Arrays.toString(pod.getImage());

                 fos.write(strContent.getBytes());
                 fos.close();
           }
          catch(FileNotFoundException ex)   {
                 System.out.println("FileNotFoundException : " + ex);
         
          }
        
        FormDataMultiPart formData=new FormDataMultiPart();
                formData.field("teamId", "cd485d72")
                .field("podId", pod.getPodId().toString())
                .field("note", pod.getNote())
                .field("callback","http://10.10.2.83:8080/week05_ca/callback");
        multiPart.bodyPart(formData);
        multiPart.bodyPart(new FileDataBodyPart("image","\\week05_ca.jpg"), MediaType.WILDCARD_TYPE);
        
        Invocation.Builder request = webTarget.request();
        
       /* Response resp = client.target("http://10.10.0.50:8080/epod/upload")
            .request(MediaType.APPLICATION_FORM_URLENCODED)
            .post(Entity.form(form), Response.class);
       */
        Response resp=request.post(Entity.entity(multiPart, multiPart.getMediaType()));
        System.out.println("resp.getStatus()"+resp.getStatus());
    }
    
}
