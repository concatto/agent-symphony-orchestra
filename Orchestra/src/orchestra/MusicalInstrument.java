/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

/**
 *
 * @author 5928036
 */
public class MusicalInstrument {
    private int assignedChannel;
    private InstrumentDescriptor descriptor;
    private SoundController controller;

    public MusicalInstrument(int assignedChannel, InstrumentDescriptor descriptor, SoundController controller) {
        this.assignedChannel = assignedChannel;
        this.descriptor = descriptor;
        this.controller = controller;
        
        controller.changeInstrument(descriptor.ordinal(), assignedChannel);
    }
    
    public int getAssignedChannel() {
        return assignedChannel;
    }
    
    public void play() {
        
    }
    
    public void stop() {
        
    }
}
