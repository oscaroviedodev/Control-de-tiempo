import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class LaminaTemporizador extends JPanel{
    
    private final CardLayout cardLayout = new CardLayout();
    
    public LaminaTemporizador() { 
        this.setLayout(cardLayout);
        
        this.add(new LaminaSpinner(), "laminaSpinner");
        this.add(new LaminaLabel(), "laminaLabel");
    } 
    
    
    private class LaminaSpinner extends JPanel{
        
        private final JPanel panelAtajos = new JPanel();
        private final JPanel panelSpinner = new JPanel(new GridBagLayout());
        private final JPanel panelBotones = new JPanel();
        private final JButton botonCinco = new JButton("5");
        private final JButton botonIniciar = new JButton("Iniciar");
        private final JSpinner spinnerHora = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
        private final JSpinner spinnerMinuto = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        private final JSpinner spinnerSegundo = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        private final JLabel separador1 = new JLabel(" : ");
        private final JLabel separador2 = new JLabel(" : ");

        
        public LaminaSpinner() {
            
            // Panel atajos
            botonCinco.addActionListener((ActionEvent e) -> {
                spinnerMinuto.setValue(Integer.valueOf(botonCinco.getActionCommand()));
            });
            panelAtajos.add(botonCinco);
            
            // Panel spinner
            panelSpinner.add(spinnerHora);
            panelSpinner.add(separador1);
            panelSpinner.add(spinnerMinuto);
            panelSpinner.add(separador2);
            panelSpinner.add(spinnerSegundo);
            
            // Panel botones
            botonIniciar.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    LaminaTemporizador.this.cardLayout.show(LaminaTemporizador.this, "laminaLabel");                    
                }
            });
            panelBotones.add(botonIniciar);
            
            this.setLayout(new BorderLayout());
            this.add(panelAtajos, BorderLayout.NORTH);
            this.add(panelSpinner, BorderLayout.CENTER);
            this.add(panelBotones, BorderLayout.SOUTH);
        }
    }
    
    
    private class LaminaLabel extends JPanel {
        
        private final JPanel panelLabel = new JPanel(new GridBagLayout());
        private final JPanel panelBotones = new JPanel();
        private final JLabel labelTiempo = new JLabel("00:00:00");
        private final JButton botonPausar = new JButton("Pausar");
        private final JButton botonDetener = new JButton("Salir");
        
        public LaminaLabel() {
            
            // panel label
            panelLabel.add(labelTiempo);
            
            // panel botones            
            botonDetener.addActionListener((ActionEvent e) -> {
                LaminaTemporizador.this.cardLayout.show(LaminaTemporizador.this, "laminaSpinner");
            });
            
            panelBotones.add(botonPausar);
            panelBotones.add(botonDetener);            
            
            this.setLayout(new BorderLayout());
            this.add(panelLabel, BorderLayout.CENTER);
            this.add(panelBotones, BorderLayout.SOUTH);
        }
    }

}
