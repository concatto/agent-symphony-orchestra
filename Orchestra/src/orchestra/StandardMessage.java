/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import jade.util.leap.Serializable;

/**
 *
 * @author 5985854
 */
public class StandardMessage implements Serializable{
    
    //private int actualTom;
    private String melodyName; 
    //private float bpm;

    public StandardMessage(String melodyName) {
        this.melodyName = melodyName;
        //this.bpm = bpm;
    }    
}
