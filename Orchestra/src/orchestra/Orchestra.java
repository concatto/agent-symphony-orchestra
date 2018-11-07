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

/**
 *
 * @author 5928036
 */
public class Orchestra {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String agents = "" +
                "heifetz:orchestra.MusicianAgent;" +
                "heifetz2:orchestra.MusicianAgent;" +
                //"heifetz3:orchestra.MusicianAgent(melodies2.txt);" +
                "heifetz4:orchestra.MusicianAgent(melodies2.txt);" +
                "heifetz6:orchestra.MusicianAgent(melodies3.txt)";
        
        String[] jadeArgs = {
            "-gui",
            "-agents",
            agents
        };
        
        jade.Boot.main(jadeArgs);
        //jade.Boot.main("-gui -agents fernandoViolin:orchestra.ViolinAgent".split(" "));
        //jade.Boot.main("-gui -agents halePiano:orchestra.PianoAgent".split(" "));
        //jade.Boot.main("-gui jaoPercussion:orchestra.ViolinAgent".split(" "));
        
    }
    
}
