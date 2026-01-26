import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LaminaCronometro extends JPanel{
    
    private final JButton botonIniciar = new JButton("Iniciar"); // Iniciara y pausara
    private final JButton botonLimpiar = new JButton("Limpiar"); 
    private final JPanel panelBotones = new JPanel();
    
    private final JLabel labelTiempo = new JLabel("00:00:00");
    private final JPanel panelTiempo = new JPanel(new GridBagLayout());
    
    public LaminaCronometro() {
        this.setLayout(new BorderLayout());
        
        panelBotones.add(botonIniciar);
        panelBotones.add(botonLimpiar);        
        this.add(panelBotones, BorderLayout.SOUTH);
        
        panelTiempo.add(labelTiempo);
        this.add(panelTiempo, BorderLayout.CENTER);
    }    
        
}
