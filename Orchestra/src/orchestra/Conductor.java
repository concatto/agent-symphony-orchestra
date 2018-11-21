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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conductor extends Agent {
    private int timer = 700;
    private int beatIndex = 1;
    private Set<AID> musicians = new HashSet<>();
    
    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg2 = receive();
                if (msg2 != null) {
                    if (msg2.getPerformative() == ACLMessage.PROPAGATE) {
                        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
                        message.setContent(msg2.getContent());
                        message.addReceiver(new AID("spalla", AID.ISLOCALNAME));
                        send(message);
                    } else {
                        try {
                            timer = Integer.parseInt(msg2.getContent());
                            System.out.println("Changed time to: " + timer);
                        } catch (NumberFormatException e) {
                            System.err.println("Tried to parse " + msg2.getContent() + " as integer but failed");
                        }
                    }
                }
                block();
            }
        });
        
        addBehaviour(new TickerBehaviour(this, this.timer) {
            @Override
            protected void onTick() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                
                for (AID aid : musicians) {
                    msg.addReceiver(aid);
                }
                
                msg.addReceiver(new AID("agentMap", AID.ISLOCALNAME));
                msg.setContent(String.valueOf(beatIndex++));
                send(msg);
                
                if (beatIndex > 4) {
                    beatIndex = 1;
                }
                
                updateMusicians();
                reset(timer);
            }
        });
    }
    
    public void updateMusicians() {
        try {
            DirectoryUtils.queryService(this, "musician").forEach(aid -> {
                musicians.add(aid);
            });
        } catch (FIPAException ex) {
            Logger.getLogger(Conductor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
