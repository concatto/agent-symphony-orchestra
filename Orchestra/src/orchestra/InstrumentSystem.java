/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author 5928036
 */
public class InstrumentSystem {
    public static SoundController controller;

    static {
        try {
            controller = new SoundController();
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(InstrumentSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
