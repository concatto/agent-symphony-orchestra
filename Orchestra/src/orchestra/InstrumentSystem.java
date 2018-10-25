/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author 5928036
 */
public class InstrumentSystem {
    public static SoundController controller;
    private static PriorityQueue<Integer> availableChannels;

    static {        
        try {
            controller = new SoundController();
            availableChannels = new PriorityQueue<>(controller.getAvailableChannels());
            
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(InstrumentSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static MusicalInstrument requestInstrument(InstrumentDescriptor descriptor) {
        int channel = availableChannels.poll();
        System.out.println("Instrument " + descriptor + " assigned to channel " + channel);
        MusicalInstrument instrument = new MusicalInstrument(channel, descriptor, controller);
        
        return instrument;
    }
    
    public static void relinquishInstrument(MusicalInstrument instrument) {
        availableChannels.offer(instrument.getAssignedChannel());
    }
}
