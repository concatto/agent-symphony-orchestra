/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import java.util.Arrays;

/**
 *
 * @author concatto
 */
public class MusicianAgent extends Agent {
    private float bpm = Float.NaN;
    
    @Override
    protected void setup() {
        Melody melody = new Melody("teste", Arrays.asList(
                new Note(100, 1),
                new Note(255, 1),
                new Note(120, 2),
                new Note(50, 0.25),
                new Note(30, 0.75)
        ));
        
        addBehaviour(new MelodyPlayerBehaviour(melody, 60));
    }
    
    private void playMelody(Melody melody) {
                
    }
}
