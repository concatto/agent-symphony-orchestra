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
    private int baseOctave = 4;
    private DiatonicScale tuning = new DiatonicScale("C", ScaleType.MAJOR);

    public MusicalInstrument(int assignedChannel, InstrumentDescriptor descriptor, SoundController controller) {
        this.assignedChannel = assignedChannel;
        this.descriptor = descriptor;
        this.controller = controller;
        
        controller.changeInstrument(descriptor.ordinal(), assignedChannel);
    }
    
    public void tune(DiatonicScale tuning) {
        //controller.allOff(assignedChannel);
        this.tuning = tuning;
    }

    public void setBaseOctave(int baseOctave) {
        this.baseOctave = baseOctave;
    }
    
    public int getAssignedChannel() {
        return assignedChannel;
    }
    
    private int calculateMidiCode(int degree, Accident accident) {
        int tonic = MidiTranslator.translate(tuning.getTonic(), baseOctave);
        return tonic + tuning.semitonesFromTonic(degree, accident);
    }
    
    public int play(int degree, Accident accident) {
        int code = calculateMidiCode(degree, accident);
        
        controller.on(code, assignedChannel);
        return code;
    }
    
    public void stop(int degree, Accident accident) {
        rawStop(calculateMidiCode(degree, accident));
    }
    
    public void rawStop(int code) {
        controller.off(code, assignedChannel);
    }
}
