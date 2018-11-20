package orchestra;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class SoundController {

    private int channel;

    private Synthesizer synth;
    private MidiChannel channels[];
    private Instrument[] instruments;
    
    public SoundController(int channel) throws MidiUnavailableException {
        this.channel = channel;
        synth = MidiSystem.getSynthesizer();
        synth.open();
        channels = synth.getChannels();

        try {
            URL regular = SoundController.class.getClassLoader().getResource("genusrmusescore.sf2");
            URL drums = SoundController.class.getClassLoader().getResource("FluidR3.SF2");
            Instrument drumkit = MidiSystem.getSoundbank(drums).getInstruments()[10];
            synth.loadInstrument(drumkit);
            channels[9].programChange(drumkit.getPatch().getProgram());

            instruments = MidiSystem.getSoundbank(regular).getInstruments();
        } catch (InvalidMidiDataException | IOException e) {
            instruments = synth.getDefaultSoundbank().getInstruments();
            e.printStackTrace();
        }

//		instruments = synth.getDefaultSoundbank().getInstruments();
    }

    public SoundController() throws MidiUnavailableException {
        this(0);
    }

    public void on(int note) {
        on(note, channel);
    }

    public void off(int note) {
        off(note, channel);
    }

    public void changeInstrument(int instrument) {
        changeInstrument(instrument, channel);
    }

    public void on(int note, int channel) {
        //System.out.println("Pitch = " + note);
        channels[channel].noteOn(note, 127);
    }

    public void off(int note, int channel) {
        channels[channel].noteOff(note);
    }
    
    public void allOff(int channel) {
        channels[channel].allNotesOff();
    }

    public void changeInstrument(int instrument, int channel) {
        int currentInstrument = channels[channel].getProgram();
        if (currentInstrument != instrument) {
            synth.loadInstrument(instruments[instrument]);
            channels[channel].programChange(instrument);
        }
    }

    public int getDefaultChannel() {
        return channel;
    }

    public void setDefaultChannel(int defaultChannel) {
        this.channel = defaultChannel;
    }
    
    public List<Integer> getAvailableChannels() {
        List<Integer> indices = new ArrayList<>();
        
        for (int i = 0; i < channels.length; i++) {
            if (i != 9 && channels[i] != null) {
                indices.add(i);
            }
        }
        
        return indices;
    }
}
