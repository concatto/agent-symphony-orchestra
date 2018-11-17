package orchestra;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conductor extends Agent {
    private int beatIndex = 1;
    private Set<AID> musicians = new HashSet<>();
    
    public Conductor() {

    }

    protected void setup() {
        addBehaviour(new TickerBehaviour(this, 1000) {
            @Override
            protected void onTick() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                
                for (AID aid : musicians) {
                    msg.addReceiver(aid);
                }
                
                msg.setContent(String.valueOf(beatIndex++));
                send(msg);
                System.out.println(msg.getSender().getLocalName() + ": " + msg.getContent());
                
                if (beatIndex > 4) {
                    beatIndex = 1;
                }
                
                updateMusicians();
            }
        });
    }
    
    public void updateMusicians() {
        ServiceDescription sd = new ServiceDescription();
        DFAgentDescription dfd = new DFAgentDescription();

        sd.setType("musician");
        dfd.addServices(sd);

        try {
            DFAgentDescription[] result = DFService.search(this, dfd);
            for (DFAgentDescription x : result) {
                musicians.add(x.getName());
            }
        } catch (FIPAException ex) {
            Logger.getLogger(Conductor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
