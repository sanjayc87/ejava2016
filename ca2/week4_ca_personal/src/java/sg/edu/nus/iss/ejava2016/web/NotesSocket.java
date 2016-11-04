/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import sg.edu.nus.iss.ejava2016.manager.notes.NotesManager;
import sg.edu.nus.iss.ejava2016.model.notes.Notes;

/**
 *
 * @author sanja
 */
@ApplicationScoped
@ServerEndpoint("/notesdisplay")
public class NotesSocket {
    
    @EJB private NotesManager notesManager;
    
    private String type = "Default";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Resource(lookup = "concurrent/myThreadPool")
    private ManagedScheduledExecutorService executor;
    
    @OnOpen
    public void open(Session session) {
        System.out.println(">>> new session: " + session.getId());
        executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println(">>> in thread");
                                
                                Optional<List<Notes>> allNotes = null;
                                
                                switch(type){
                                    case "Social":
                                        allNotes = notesManager.findByCategory("Social");
                                        System.out.println(">>> in thread : Social");
                                        break;
                                    case "ForSale":
                                        allNotes = notesManager.findByCategory("ForSale");
                                        System.out.println(">>> in thread : For Sale");
                                        break;
                                    case "Jobs":
                                        allNotes = notesManager.findByCategory("Jobs");
                                        System.out.println(">>> in thread : Jobs");
                                        break;
                                    case "Tution":
                                        allNotes = notesManager.findByCategory("Tution");
                                        System.out.println(">>> in thread : Tution");
                                        break;
                                    default:
                                        allNotes = notesManager.findAll();
                                        System.out.println(">>> in thread : All");
                                        break;
                                }

                                JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

                                allNotes.get().stream()
                                        .map(c -> {return(c.toJSON());})
                                        .forEach(j -> {arrBuilder.add(j);});
                                
				final JsonObject message = Json.createObjectBuilder()
						.add("message", arrBuilder.build().toString())
						.build();

				for (Session s: session.getOpenSessions())
					try {
                                            if(session.getId().equals(s.getId())){
						s.getBasicRemote().sendText(message.toString());
                                                break;
                                            }
					} catch(IOException ex) {
						try { s.close(); } catch (IOException e) { }
					}
			}
		}, 3, 2, TimeUnit.SECONDS);
    }
    
    @OnMessage
    public void message(final Session session, final String msg) {
            System.out.println(">>> message: " + msg);
            setType(msg);


    }
    
}
