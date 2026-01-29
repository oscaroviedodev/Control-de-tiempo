import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;

public class LaminaTemporizador extends JPanel{
    
    private final CardLayout cardLayout = new CardLayout();
    private final LaminaLabel laminaLabel = new LaminaLabel();
    private final Timer timer = new Timer(1000, laminaLabel);
    private final JButton botonIniciar = new JButton("Iniciar");
    
    public LaminaTemporizador() { 
        this.setLayout(cardLayout);              
        this.add(new LaminaSpinner(), "laminaSpinner");
        this.add(laminaLabel, "laminaLabel");
    } 
    
    
    private class LaminaSpinner extends JPanel{
        
        private final JPanel panelAtajos = new JPanel();
        private final JPanel panelSpinner = new JPanel(new GridBagLayout());
        private final JPanel panelBotones = new JPanel();
        private final JButton botonCinco = new JButton("5 m");
        private final JButton botonQuince = new JButton("15 m");
        private final JButton botonTreinta = new JButton("30 m");
        private final JButton botonHora = new JButton("1 h");
        private final JSpinner spinnerHora = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
        private final JSpinner spinnerMinuto = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        private final JSpinner spinnerSegundo = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        private final JLabel separador1 = new JLabel(" : ");
        private final JLabel separador2 = new JLabel(" : ");
        
        public LaminaSpinner() {
            
            // ----- Panel atajos -----
            botonCinco.addActionListener((ActionEvent e) -> {
                spinnerMinuto.setValue(5);
                spinnerHora.setValue(0);
                spinnerSegundo.setValue(0);

            });
            panelAtajos.add(botonCinco);
            
            botonQuince.addActionListener((ActionEvent e) -> {
                spinnerMinuto.setValue(15);
                spinnerHora.setValue(0);
                spinnerSegundo.setValue(0);
            });
            panelAtajos.add(botonQuince);
            
            botonTreinta.addActionListener((ActionEvent e) -> {
                spinnerMinuto.setValue(30);
                spinnerHora.setValue(0);
                spinnerSegundo.setValue(0);
            });
            panelAtajos.add(botonTreinta);
            
            botonHora.addActionListener((ActionEvent e) -> {
                spinnerHora.setValue(1);
                spinnerMinuto.setValue(0);
                spinnerSegundo.setValue(0);
            });
            panelAtajos.add(botonHora);            
            
            // ----- Panel spinner -----
            panelSpinner.add(spinnerHora);
            panelSpinner.add(separador1);
            panelSpinner.add(spinnerMinuto);
            panelSpinner.add(separador2);
            panelSpinner.add(spinnerSegundo);
            
            // ----- Panel botones -----
            botonIniciar.addActionListener(new ActionListener(){                
                @Override
                public void actionPerformed(ActionEvent e) {
                    LaminaTemporizador.this.cardLayout.show(LaminaTemporizador.this, "laminaLabel");
                    
                    // Capturar tiempo del JSpinner
                    laminaLabel.horaJspinner = (int)spinnerHora.getValue();
                    laminaLabel.minutoJspinner = (int)spinnerMinuto.getValue();
                    laminaLabel.segundoJspinner = (int)spinnerSegundo.getValue();                     
                    
                    // Resetear JSpinner
                    spinnerHora.setValue(0);
                    spinnerMinuto.setValue(0);
                    spinnerSegundo.setValue(0);
                    
                    // Iniciar timer
                    timer.setInitialDelay(0);
                    timer.start();
                }
            });
            panelBotones.add(botonIniciar);
            
            this.setLayout(new BorderLayout());
            this.add(panelAtajos, BorderLayout.NORTH);
            this.add(panelSpinner, BorderLayout.CENTER);
            this.add(panelBotones, BorderLayout.SOUTH);
        }
    }
    
    
    private class LaminaLabel extends JPanel implements ActionListener{
        
        private final JPanel panelLabel = new JPanel(new GridBagLayout());
        private final JPanel panelBotones = new JPanel();
        private final JLabel labelTiempo = new JLabel("00:00:00");
        private final JButton botonPausar = new JButton("Pausar");
        private final JButton botonDetener = new JButton("Salir");
        private int horaJspinner = 0;
        private int minutoJspinner = 0;
        private int segundoJspinner = 0;
        private long milisegundosInicio = 0;        
        private long milisegundosTotales = 0;
        private long milisegundosFinal = 0;
        long tiempo = 0;
        int segundo = 0;
        int minuto = 0;
        int hora = 0;
        
        public LaminaLabel() {

            // ----- panel label -----
            panelLabel.add(labelTiempo);
            
            // ----- panel botones -----             
            // boton pausar - reanudar
            botonPausar.addActionListener((ActionEvent e) -> {
                if (e.getActionCommand().equals("Pausar")) {
                    botonPausar.setText("Reanudar");
                    timer.stop();
                    
                } else if(e.getActionCommand().equals("Reanudar")){
                    botonPausar.setText("Pausar");
                    milisegundosFinal = System.currentTimeMillis() + tiempo;
                    timer.start();
                }
            });
            
            // boton detener
            botonDetener.addActionListener((ActionEvent e) -> {
                LaminaTemporizador.this.cardLayout.show(LaminaTemporizador.this, "laminaSpinner");
                timer.stop();
                horaJspinner = 0;
                minutoJspinner = 0;
                segundoJspinner = 0;
                milisegundosInicio = 0;
                milisegundosFinal = 0;
                milisegundosTotales = 0;
                labelTiempo.setText("00:00:00");
            });            
            
            panelBotones.add(botonPausar);
            panelBotones.add(botonDetener);            
            
            this.setLayout(new BorderLayout());
            this.add(panelLabel, BorderLayout.CENTER);
            this.add(panelBotones, BorderLayout.SOUTH);
        }
        
        // ----- evento de timer -----
        @Override
        public void actionPerformed(ActionEvent e) {
            
            botonPausar.setEnabled(true);
            
            if (milisegundosInicio == 0) {
                milisegundosInicio = System.currentTimeMillis();
                milisegundosTotales = ((horaJspinner * 3600000) + (minutoJspinner * 60000) + (segundoJspinner * 1000));
                milisegundosFinal = milisegundosInicio + milisegundosTotales;
                     
            } if (milisegundosFinal >= System.currentTimeMillis()) {
                tiempo = milisegundosFinal - System.currentTimeMillis();
                segundo = (int)(tiempo / 1000) % 60;
                minuto = (int)(tiempo / 60000) % 60;
                hora = (int)(tiempo / 3600000) % 60;
                labelTiempo.setText(String.format("%02d:%02d:%02d", hora, minuto, segundo));
                System.out.println(tiempo);
            
            } else if (segundo == 0 && minuto == 0 && hora == 0) {
                botonPausar.setEnabled(false);
                Toolkit sonido = Toolkit.getDefaultToolkit();
                sonido.beep();
                timer.stop();                
            }
        }
    }
}
