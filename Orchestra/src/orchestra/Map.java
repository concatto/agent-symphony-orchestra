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
    
    private final JPanel panelMusicians = new JPanel(new BorderLayout());
    private final JPanel panelButtons = new JPanel(new FlowLayout());
    
    private final JButton upButton = new JButton("Up");
    private final JButton downButton = new JButton("Down"); 
    
    private final JLabel bpms;
   
    private MapStage stage = new MapStage();
    
    private void initActionListeners() {
        upButton.addActionListener((event) -> {
            this.bpmCount = bpmCount + 5;
            bpms.setText(bpmCount.toString());
        });
        
        downButton.addActionListener((event) -> {
            this.bpmCount = bpmCount - 5;
            bpms.setText(bpmCount.toString());
        });
    }
    
    public Map() {
               
        jade.core.Runtime rt = jade.core.Runtime.instance();     
        rt.setCloseVM(true);
        Profile p = new ProfileImpl();   
        p.setParameter(Profile.MAIN_HOST, "127.0.0.1");       
        p.setParameter(Profile.MAIN_PORT, "1199");      
        AgentContainer ac = rt.createMainContainer(p);
        
        AgentController agentAbelha;
        AgentController agentAbelha2;
        try {
            agentAbelha = ac.createNewAgent("orchestador", "orchestra.Conductor", null);
            agentAbelha.start();
            
            agentAbelha2 = ac.createNewAgent("musico", "orchestra.Conductor", null);
            agentAbelha2.start();
            
        } catch (StaleProxyException ex) {
            System.out.println("Erro");
        }
        
        
        
        try {
            AgentController rma = ac.createNewAgent("rma", "jade.tools.rma.rma", null);
            rma.start();
        } catch(StaleProxyException e) {
            System.out.println("Erro ao criar/startar o agente do Jade Tools: " + e.getMessage());
        }
        
        this.bpmCount = 60;
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
