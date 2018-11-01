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
import java.util.Arrays;
import java.util.List;
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
    
    @Override
    protected void setup() {        
        /*addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("halePiano", AID.ISLOCALNAME));
                msg.setContent("Ola");
                send(msg);
                
            }
        });*/
        
        beatQueue = new BoundedQueue<>(4);
        instrument = InstrumentSystem.requestInstrument(InstrumentDescriptor.VIOLIN);
        
        addBehaviour(new CyclicBehaviour() {
			int index = 1;
			
			@Override
			public void action() {
				beat(index++);
				System.out.println("RECEBI A MENSAGEM PONTO COM PONTO BR");
			    if (index > 4) {
			       index = 1;
			    }
			}
		});
        
//		ALTERAR PARA RECEBER O TICKER BEHAVIOR DO REGENTAGENT
//        addBehaviour(new TickerBehaviour(this, 1000) {
//            int index = 1;
//            @Override
//            protected void onTick() {
//                beat(index++);
//                
//                if (index > 4) {
//                    index = 1;
//                }
//            }
//        });
    }
    
    public void play(int tone) {
        if (tone != 0) { // Rest
            instrument.play(tone, false);
        }
    }
    
    public void stop(int tone) {
        if (tone != 0) { // Rest
            instrument.stop(tone, false);
        }
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
        Melody melody = new Melody("teste", Arrays.asList(
                new Note(1, 0.25),
                new Note(2, 0.5),
                new Note(3, 0.25),
                new Note(4, 0.5),
                new Note(5, 0.25),
                new Note(6, 0.5),
                new Note(7, 0.25),
                new Note(8, 0.5),
                new Note(9, 0.25),
                new Note(10, 0.5),
                new Note(0, 0.25)
        ));
        
        melodyBehaviour = new MelodyPlayingBehaviour(this, melody, bpm);
        addBehaviour(melodyBehaviour);
    }
}
