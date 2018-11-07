package orchestra;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.introspection.AddedBehaviour;
import jade.gui.AgentTree.SuperContainer;

public class ConcertMaster extends MusicianAgent{
    private BoundedQueue<Long> beatQueue;
    private float bpm = Float.NaN;
    private MelodyPlayingBehaviour melodyBehaviour = null;
    private MusicalInstrument instrument;
   
	public ConcertMaster(BoundedQueue<Long> beatQueue, float bpm, MelodyPlayingBehaviour melodyBehavior, MusicalInstrument instrument) {
		this.beatQueue = beatQueue;
		this.bpm = bpm;
		this.melodyBehaviour = melodyBehavior;
		this.instrument = instrument;
	}
	
	@Override
	protected void setup() {
		super.setup();
	}
	
	@Override
	public void play(int tone) {
		super.play(tone);
	}
	
	@Override
	public void stop(int tone) {
		super.stop(tone);
	}
	
	@Override
	public void beat(int index) {
		super.beat(index);
	}
	
	
}
