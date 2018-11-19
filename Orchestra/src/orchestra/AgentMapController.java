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
        
        ServiceDescription sd = new ServiceDescription();
        DFAgentDescription dfd = new DFAgentDescription();
        
        sd.setName(getLocalName());
        sd.setType("musician");
        dfd.setName(getAID());
        dfd.addServices(sd);
        
        try {
            DFService.register(this, dfd);
        } catch (FIPAException ex) {
            Logger.getLogger(MusicianAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    if (msg.getSender().getLocalName().equals("conductor")) {
                        changeMapState(3);
                    }
                    else if (msg.getSender().getLocalName().equals("violin")) {
                        changeMapState(0);
                    }
                    else if (msg.getSender().getLocalName().equals("cello")) {
                        changeMapState(1);
                    }
                    else if (msg.getSender().getLocalName().equals("wind")) {
                        changeMapState(2);
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
    
    private void changeMapState(int agentIndex) {
        map.stage.changeImage(agentIndex);
    }
    
}
