package orchestra;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MusicianPanel extends JPanel{

    private Image image1;
    private Image image2;
    public Image currentImage;
    
    public int x, y;
    
    public MusicianPanel(int x, int y) {
    
        this.x = x;
        this.y = y;
        
        try {
            this.image1 = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource("violin1.png"), "violin1.png"));
            this.image2 = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource("violin2.png"), "violin2.png"));
            currentImage = image2;
        } catch (MalformedURLException ex) {
            Logger.getLogger(MapStage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MapStage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void switchImage() {
        this.currentImage = this.currentImage.equals(image1) ? image2 : image1;
    }

    
    
}
