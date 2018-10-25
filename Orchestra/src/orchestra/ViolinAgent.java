/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Arrays;

/**
 *
 * @author concatto
 */
public class ViolinAgent extends Agent {
    private float bpm = Float.NaN;
    
    
    
    @Override
    protected void setup() {
        Melody melody = new Melody("MetalBlockPictureRock", Arrays.asList(
                new Note(100, 1),
                new Note(255, 1),
                new Note(120, 2),
                new Note(50, 0.25),
                new Note(30, 0.75)
        ));
        
        /*addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("halePiano", AID.ISLOCALNAME));
                msg.setContent("Ola");
                send(msg);
                
            }
        });*/
        addBehaviour(new MelodyPlayerBehaviour(melody, 60));
    }
    
    private void playMelody(Melody melody) {
                
    }
}
