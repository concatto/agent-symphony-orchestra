package orchestra;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapStage extends JPanel {
    Image image;

    public MapStage() {
        try {
            this.image = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource("palco.jpg"), "palco.jpg"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(MapStage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MapStage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void paintComponent( Graphics g ) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, 800, 500, this);
    }
}
