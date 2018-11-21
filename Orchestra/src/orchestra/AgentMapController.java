package orchestra;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgentMapController extends Agent{

    Map map = new Map();
    
    int i = 100;
    
    @Override
    protected void setup() {
        map.onCommand(command -> {
            ACLMessage message = new ACLMessage(ACLMessage.PROPAGATE);
            message.addReceiver(new AID("conductor", AID.ISLOCALNAME));
            message.setContent(command);
            send(message);
        });
        
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    if (msg.getSender().getLocalName().equals("conductor")) {
                        //changeMapState(msg.getContent(), 3);
                    }
                    else if (msg.getSender().getLocalName().equals("violin")) {
                        changeMapState(msg.getContent(), 0);
                    }
                    else if (msg.getSender().getLocalName().equals("cello")) {
                        changeMapState(msg.getContent(), 1);      
                    }
                    else if (msg.getSender().getLocalName().equals("wind")) {
                        changeMapState(msg.getContent(), 2);
                    }
                }
                block();
            }
        });
        
        addBehaviour(new TickerBehaviour(this, 1000) {
            @Override
            protected void onTick() {
                if (map.checkChangeBpm()) {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID("conductor", AID.ISLOCALNAME));
                    msg.setContent(Integer.toString(map.getBpmCount()));
                    send(msg);
                }
                //System.out.println(msg.getSender().getLocalName() + ": " + msg.getContent());
            }
        });
    }
    
    private void changeMapState(String msg, int agentIndex) {
        //process the msg
        //imageName, Flag = msg.treatment();
        String note;
        String flag;
        
        try {
            note = msg.substring(0, 2);
            if (note.charAt(1) == '#') {
                note = note.substring(0, 1) + "sus";
            }
            flag = msg.substring(2, 16);
            
            if (agentIndex != 3 ) {
                map.stage.changeNoteImage(note, agentIndex);   
            }
        } catch(Exception e) {
            map.stage.changeImage(agentIndex);
        }
    }
    
}
