/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 5985854
 */
public class PianoAgent extends Agent {
    
     private float bpm = Float.NaN;
     private ArrayList<String> friends = new ArrayList<>();
     private StandardMessage objectMessage;
     
    public PianoAgent() {
        this.friends.add("fernandoViolin");
    }
     
    @Override
    protected void setup() {
        
        Melody melody = new Melody("DedosAgeis.com", Arrays.asList(
                new Note(100, 1),
                new Note(255, 1),
                new Note(120, 2),
                new Note(50, 0.25),
                new Note(30, 0.75)
        ));
        
        this.objectMessage = new StandardMessage(melody.getName());
        /*
        addBehaviour(new CyclicBehaviour(this){
            @Override
            public void action() {
                ACLMessage receivedMsg = receive();
                if(receivedMsg != null){
                    System.out.println("TOP");
                    block();
                }
            }
        });*/
        addBehaviour(new MelodyPlayerBehaviour(melody, 60));
        
        
    }
    
    public void communicate() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        try {
                msg.setContentObject(objectMessage);
            } catch (IOException ex) {
                Logger.getLogger(PianoAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
        for (String friend: friends) {
            msg.addReceiver(new AID(friend, AID.ISLOCALNAME)); 
        }
        
        send(msg);
    }
    
    private void playMelody(Melody melody) {
                
    }
    
}
