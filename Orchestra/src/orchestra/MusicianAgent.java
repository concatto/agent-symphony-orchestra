/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.net.URL;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author concatto
 */
public class MusicianAgent extends Agent {
    private BoundedQueue<Long> beatQueue;
    private float bpm = Float.NaN;
    private MelodyPlayingBehaviour melodyBehaviour = null;
    private MusicalInstrument instrument;
    private int degreeShift = 0;
    private int cumulativeBeats = 0;
    
    @Override
    protected void setup() {
        Object[] args = getArguments();
        
        DFAgentDescription dfd = new DFAgentDescription();
        
        dfd.setName(getAID());
        dfd.addServices(createService("musician"));
        dfd.addServices(createService(args[3].toString()));
        
        try {
            DFService.register(this, dfd);
        } catch (FIPAException ex) {
            Logger.getLogger(MusicianAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        beatQueue = new BoundedQueue<>(4);
        instrument = InstrumentSystem.requestInstrument(InstrumentDescriptor.valueOf(args[1].toString()));
        instrument.setBaseOctave(Integer.parseInt(args[2].toString()));

        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg;
                
                do {
                    msg = receive();

                    if (msg != null) {
                        System.out.println("Agent " + getLocalName() + " received \"" + msg.getContent() + "\" from " + msg.getSender().getLocalName() + "@" + Instant.now());
                        handleMessage(msg);
                    }
                } while (msg != null);
                
                block();
            }
        });
    }
    
    protected void handleMessage(ACLMessage msg) {
        if (msg.getSender().getLocalName().equalsIgnoreCase("conductor")) {
           int beat = Integer.parseInt(msg.getContent());

           beat(beat);
           //sendMessageToMap();

        }
        
        if (msg.getContent().equalsIgnoreCase("remainingBeats")) {
            ACLMessage reply = msg.createReply();
            int remaining = melodyBehaviour.getCurrentMelody().countBeats() - cumulativeBeats;
            reply.setContent(remaining + " remaining");
            send(reply);
        }
        
        if (msg.getContent().startsWith("synchronize")) {
            String[] parts = msg.getContent().split(" ");
            int melody = Integer.parseInt(parts[1]);
            int toWait = Integer.parseInt(parts[3]);
            
            melodyBehaviour.synchronize(toWait, melody);
        }
    }
    
    private void sendMessageToMap() {
        ACLMessage msg3 = new ACLMessage(ACLMessage.INFORM);
        msg3.addReceiver(new AID("agentMap", AID.ISLOCALNAME));
        msg3.setContent("changeImage");
        send(msg3);
    }
    
    public void play(Note note) {
        instrument.play(note.getPitch() + degreeShift, note.getAccident());
    }
    
    public void stop(Note note) {
        instrument.stop(note.getPitch() + degreeShift, note.getAccident());
    }
    
    public void beat(int index) {
        beatQueue.insert(System.currentTimeMillis());
        
        computeBPM();
        cumulativeBeats++;
        
        if (index == 1 && beatQueue.isFull() && melodyBehaviour == null) {
            beginPlaying();
        }
    }

    private void computeBPM() {
        if (beatQueue.getSize() > 1) {
            long deltaSum = 0;
            List<Long> list = beatQueue.asList();
            list.sort((a, b) -> (int) (a - b));
            
            for (int i = 1; i < list.size(); i++) {
                long previous = list.get(i - 1);
                long current = list.get(i);
                
                deltaSum += (current - previous);
            }
            
            float averageDelta = deltaSum / ((float) (list.size() - 1));
            bpm = (60f * 1000f) / averageDelta;
            
            if (melodyBehaviour != null) {
                melodyBehaviour.setBpm(bpm);
            }
        }
    }

    private void beginPlaying() {
        Object[] args = getArguments();
        try {
            String file = (args != null && args.length > 0) ? args[0].toString() : "melodies.txt";
            List<Melody> melodies = MelodyReader.read(file);
            System.out.println(melodies.size() + " melodies");
            
            melodyBehaviour = new MelodyPlayingBehaviour(this, melodies, bpm);
            addBehaviour(melodyBehaviour);
            
            cumulativeBeats = 0;
        } catch (IOException ex) {
            Logger.getLogger(MusicianAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void notifyMelodyCompletion() {
        //degreeShift = new Random().nextInt(3) * 2;
        cumulativeBeats = 0;
    }
    
    private ServiceDescription createService(String type) {
        ServiceDescription sd = new ServiceDescription();
        
        sd.setName(getLocalName());
        sd.setType(type);
        
        return sd;
    }

    public MelodyPlayingBehaviour getMelodyBehaviour() {
        return melodyBehaviour;
    }

}
