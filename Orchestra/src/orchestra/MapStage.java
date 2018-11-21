package orchestra;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapStage extends JPanel {
    private Image image;
    private List<MusicianPanel> agentsPanel = new LinkedList<>();
    
    
    public MapStage() {
        
        agentsPanel.add(new MusicianPanel(100, 370, 50, 370, "violino.png", "violino2.png"));
        agentsPanel.add(new MusicianPanel(300, 370, 250, 370, "cello.png", "cello2.png"));
        agentsPanel.add(new MusicianPanel(500, 370, 450, 370, "flauta.png", "flauta2.png"));
        agentsPanel.add(new MusicianPanel(320, 280, 0,0, "maestro1.png", "maestro2.png"));
        
        try {
            this.image = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource("palco.jpg"), "palco.jpg"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(MapStage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MapStage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Vai precisar adicionar a tonica ai tambem pra fazer o calculo
    public void changeNoteImage(String note, int agenteIndex) {
        agentsPanel.get(agenteIndex).switchNoteImage(note);
        switch (agenteIndex) {
            case 0:
                repaint(50, 350, 75, 75);
                break;
            case 1:
                repaint(230, 350, 75, 75);
                break;
            case 2:
                repaint(430, 350, 75, 75);
                break;
        }
        
    }
    
    public void changeImage(int agenteIndex) {
        agentsPanel.get(agenteIndex).switchImage();
        //repaint(310, 270, 110, 110);
    }

    public void paint( Graphics g ) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, 800, 500, this);
        
        for(MusicianPanel m : agentsPanel) {
            g.drawImage(m.currentImage, m.x, m.y, 100, 100, this);
            g.drawImage(m.currentNote, m.xnote, m.ynote, 50, 50, this);
        }
        
        //g.drawImage(currentImage, x, y, 100, 100, this);
        //g.drawImage(currentImage, x*2, y*2, 100, 100, this);
    }
}
