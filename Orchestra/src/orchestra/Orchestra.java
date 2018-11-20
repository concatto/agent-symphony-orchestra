/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;


/**
 *
 * @author 5928036
 */
public class Orchestra {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        jade.core.Runtime rt = jade.core.Runtime.instance();     
        rt.setCloseVM(true);
        Profile p = new ProfileImpl();   
        p.setParameter(Profile.MAIN_HOST, "127.0.0.1");       
        p.setParameter(Profile.MAIN_PORT, "1199");      
        AgentContainer ac = rt.createMainContainer(p);
        
        AgentController conductor;
        AgentController violin;
        AgentController bass;
        AgentController clarinet;
        AgentController flute;
        AgentController agentMap;
        AgentController spalla;
        
        /*
        melodyFile = arguments[0].toString();
        instrumentName = arguments[1].toString();
        startingOctave = Integer.parseInt(arguments[2].toString());
        musicalRole = arguments[3].toString();
        section = arguments[4].toString();
        */
        Object[] objViolin = new Object[]{"treble.txt", "FAST_STRINGS", "5", "treble", "violins"};
        Object[] objClarinet = new Object[]{"treble.txt", "CLARINET", "5", "treble", "winds"};
        Object[] objFlute = new Object[]{"treble.txt", "FLUTE", "5", "treble", "winds"};
        Object[] objBass = new Object[]{"bass.txt", "SLOW_STRINGS", "4", "bass", "continuo"};
        
        try {
            
            conductor = ac.createNewAgent("conductor", "orchestra.Conductor", null);
            conductor.start();
            
            agentMap = ac.createNewAgent("agentMap", "orchestra.AgentMapController", null);
            agentMap.start();
            
            spalla = ac.createNewAgent("spalla", "orchestra.ConcertMaster", objViolin);
            spalla.start();
            
            //bass = ac.createNewAgent("heifetz", "orchestra.MusicianAgent", objBass);
            bass = ac.createNewAgent("cello", "orchestra.MusicianAgent", objBass);
            bass.start();
            
            violin = ac.createNewAgent("violin", "orchestra.MusicianAgent", objViolin);
            violin.start();
////            
            
            clarinet = ac.createNewAgent("wind", "orchestra.SectionLeader", objClarinet);
            clarinet.start();
            
            flute = ac.createNewAgent("flute", "orchestra.MusicianAgent", objFlute);
            flute.start();
            
            
        } catch (StaleProxyException ex) {
            System.out.println("Erro");
        }
        
        
        
        try {
            AgentController rma = ac.createNewAgent("rma", "jade.tools.rma.rma", null);
            rma.start();
        } catch(StaleProxyException e) {
            System.out.println("Erro ao criar/startar o agente do Jade Tools: " + e.getMessage());
        }
        
    	/*String agents = "" +
                //"spalla:orchestra.ConcertMaster;" +
                "heifetz:orchestra.MusicianAgent(bass.txt,CELLO,4);" +
                "heifetz2:orchestra.MusicianAgent(vivaldi.txt,VIOLIN,4);" +
                "heifetz3:orchestra.MusicianAgent(vivaldi.txt,CLARINET,5);" +
                "direttore:orchestra.Conductor";
                //"heifetz3:orchestra.MusicianAgent(melodies2.txt);" +
                //"heifetz4:orchestra.MusicianAgent(melodies2.txt);" +
                //"heifetz6:orchestra.MusicianAgent(melodies3.txt)";
        
        String[] jadeArgs = {
            "-gui",
            "-agents",
            agents
        };
        
        jade.Boot.main(jadeArgs);
        //jade.Boot.main("-gui -agents heifetz:orchestra.MusicianAgent".split(" "));
        //jade.Boot.main("-gui -agents direttore:orchestra.Conductor".split(" "));
        //jade.Boot.main("-gui -agents spalla:orchestra.MusicianAgent".split(" "));*/
    }
    
}
