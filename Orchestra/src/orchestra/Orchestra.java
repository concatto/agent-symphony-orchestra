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
public class Orchestra {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        jade.Boot.main("-gui -agents heifetz:orchestra.MusicianAgent".split(" "));
        //jade.Boot.main("-gui -agents fernandoViolin:orchestra.ViolinAgent".split(" "));
        //jade.Boot.main("-gui -agents halePiano:orchestra.PianoAgent".split(" "));
        //jade.Boot.main("-gui jaoPercussion:orchestra.ViolinAgent".split(" "));
        
    }
    
}
