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
@RequestScoped
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
        executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
                                
                                Optional<List<Notes>> allNotes = null;
                                
                                switch(type){
                                    case "Social":
                                        allNotes = notesManager.findByCategory("Social");
                                        break;
                                    case "ForSale":
                                        allNotes = notesManager.findByCategory("ForSale");
                                        break;
                                    case "Jobs":
                                        allNotes = notesManager.findByCategory("Jobs");
                                        break;
                                    case "Tuition":
                                        allNotes = notesManager.findByCategory("Tuition");
                                        break;
                                    default:
                                        allNotes = notesManager.findAll();
                                        break;
                                }

                                JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

                                List<Notes> sortedList = allNotes.get();
                                sortedList.sort((n1,n2) -> n2.getCreated().compareTo(n1.getCreated()));
                                sortedList.stream().map(c -> {return(c.toJSON());})
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
            setType(msg);


    }
    
}
