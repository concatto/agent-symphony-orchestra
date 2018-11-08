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
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
    
    @Override
    protected void setup() {        
        
        beatQueue = new BoundedQueue<>(4);
        instrument = InstrumentSystem.requestInstrument(InstrumentDescriptor.VIOLIN);
        
        addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action() {
				ACLMessage msg2 = receive();
				if(msg2 != null) {
					System.out.println(myAgent.getLocalName() + ": " + msg2.getContent());				
					block();
				}
					
			}
		});

     
    }
    
    public void play(int degree) {
        instrument.play(degree + degreeShift, false);
    }
    
    public void stop(int degree) {
        instrument.stop(degree + degreeShift, false);
    }
    
    public void beat(int index) {        
        System.out.println(index);
        beatQueue.insert(System.currentTimeMillis());
        
        computeBPM();
        
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
            melodyBehaviour = new MelodyPlayingBehaviour(this, melodies.get(0), bpm);
            addBehaviour(melodyBehaviour);
        } catch (IOException ex) {
            Logger.getLogger(MusicianAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
