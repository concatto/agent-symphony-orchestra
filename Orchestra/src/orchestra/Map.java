package orchestra;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import orchestra.MapStage;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.function.Consumer;
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
    
    private final JPanel commandPanel = new JPanel(new FlowLayout());
    private final JPanel panelMusicians = new JPanel(new BorderLayout());
    private final JPanel panelButtons = new JPanel(new FlowLayout());
    
    private final JButton upButton = new JButton("Up");
    private final JButton downButton = new JButton("Down");
    private final JButton tuttiButton = new JButton("Tutti");
    private final JButton espressivoButton = new JButton("Espressivo");
    private final JButton lacrimosoButton = new JButton("Lacrimoso");
    private final JButton grandiosoButton = new JButton("Grandioso");
    private final JButton appassionatoButton = new JButton("Appassionato");
    
    private final JLabel bpms;
   
    public MapStage stage = new MapStage();
    private Consumer<String> commandHandler;
    
    private void initActionListeners() {
        upButton.addActionListener((event) -> {
            this.oldBpmCount = bpmCount;
            this.bpmCount = bpmCount + 100;
            bpms.setText(bpmCount.toString());
        });
        
        downButton.addActionListener((event) -> {
            if (this.bpmCount - 100 > 0) {
                this.oldBpmCount = bpmCount;
                this.bpmCount = bpmCount - 100;
                bpms.setText(bpmCount.toString());
            }
        });
        
        tuttiButton.addActionListener(e -> sendCommand("tutti"));
        espressivoButton.addActionListener(e -> changeSentiment(e, "espressivo"));
        lacrimosoButton.addActionListener(e -> changeSentiment(e, "lacrimoso"));
        grandiosoButton.addActionListener(e -> changeSentiment(e, "grandioso"));
        appassionatoButton.addActionListener(e -> changeSentiment(e, "appassionato"));
    }
    
    public void onCommand(Consumer<String> handler) {
        this.commandHandler = handler;
        
        espressivoButton.doClick();
    }
    
    private void sendCommand(String command) {
        if (commandHandler != null) {
            commandHandler.accept(command);
        }
    }
    
    public int getBpmCount() {
        return bpmCount;
    }
    
    public boolean checkChangeBpm() {
        if (Objects.equals(this.oldBpmCount, this.bpmCount)) { return false; }
        
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

        commandPanel.add(tuttiButton);
        commandPanel.add(espressivoButton);
        commandPanel.add(lacrimosoButton);
        commandPanel.add(grandiosoButton);
        commandPanel.add(appassionatoButton);
        
        //add(BorderLayout.CENTER, stage);
        add(BorderLayout.NORTH, commandPanel);
        add(BorderLayout.CENTER, stage);
        add(BorderLayout.SOUTH, panelButtons);
       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void changeSentiment(ActionEvent e, String sentiment) {
        JButton source = (JButton) e.getSource();
        sendCommand(sentiment);
        
        grandiosoButton.setEnabled(true);
        lacrimosoButton.setEnabled(true);
        espressivoButton.setEnabled(true);
        appassionatoButton.setEnabled(true);
        
        source.setEnabled(false);
    }
}
