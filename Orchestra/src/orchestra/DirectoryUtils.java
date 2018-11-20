/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author concatto
 */
public class DirectoryUtils {
    public static List<AID> queryService(Agent agent, String service) throws FIPAException {
        ServiceDescription sd = new ServiceDescription();
        DFAgentDescription dfd = new DFAgentDescription();

        sd.setType(service);
        dfd.addServices(sd);

        DFAgentDescription[] result = DFService.search(agent, dfd);

        return Arrays.stream(result).map(x -> x.getName()).collect(Collectors.toList());
    }
    
    public static List<AID> broadcast(Agent sender, ACLMessage message, String service) throws FIPAException {
        List<AID> receivers = DirectoryUtils.queryService(sender, service);

        for (AID aid : receivers) {
            message.addReceiver(aid);
        }
        
        sender.send(message);
        return receivers;
    }
    
    public static List<AID> broadcast(Agent sender, int performative, String content, String service) throws FIPAException {
        ACLMessage message = new ACLMessage(performative);
        message.setContent(content);
        return broadcast(sender, message, service);
    }
}
