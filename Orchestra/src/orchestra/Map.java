package orchestra;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import orchestra.MapStage;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;



public class Map extends JFrame{
    
    private Integer bpmCount;
    private Integer oldBpmCount;
    
    private final JPanel panelMusicians = new JPanel(new BorderLayout());
    private final JPanel panelButtons = new JPanel(new FlowLayout());
    
    private final JButton upButton = new JButton("Up");
    private final JButton downButton = new JButton("Down"); 
    
    private final JLabel bpms;
   
    private MapStage stage = new MapStage();
    
    private void initActionListeners() {
        upButton.addActionListener((event) -> {
            this.oldBpmCount = bpmCount;
            this.bpmCount = bpmCount + 20;
            bpms.setText(bpmCount.toString());
        });
        
        downButton.addActionListener((event) -> {
            this.oldBpmCount = bpmCount;
            this.bpmCount = bpmCount - 20;
            bpms.setText(bpmCount.toString());
        });
    }
    
    public int getBpmCount() {
        return bpmCount;
    }
    
    public boolean checkChangeBpm() {
        if (this.oldBpmCount == this.bpmCount) { return false; }
        
        this.oldBpmCount = bpmCount;
        return true;
    }
    
    public Map() {
        
        this.bpmCount = 500;
        this.oldBpmCount = 500;
        bpms = new JLabel(bpmCount.toString());
        
        initActionListeners();
        
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        this.setSize(800, 600);
        this.setResizable(true);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setTitle("Orchestra");
        
        panelButtons.add(upButton);
        panelButtons.add(bpms);
        panelButtons.add(downButton);
        
        
        //mainPanel.add(BorderLayout.NORTH, panelTopButtons);
        //mainPanel.add(BorderLayout.CENTER, panelCodeArea);
        //mainPanel.add(BorderLayout.SOUTH, panelResultCodeArea);

        //add(BorderLayout.CENTER, stage);
        add(BorderLayout.CENTER, stage);
        add(BorderLayout.SOUTH, panelButtons);
       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
