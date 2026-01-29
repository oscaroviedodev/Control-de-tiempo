import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;

public class LaminaTemporizador extends JPanel{
    
    private final CardLayout cardLayout = new CardLayout();
    private final LaminaUno laminaUno = new LaminaUno();    
    private final LaminaDos laminaDos = new LaminaDos();
    private final Timer timer = new Timer(1000, laminaDos);
    
    public LaminaTemporizador() { 
        this.setLayout(cardLayout);              
        this.add(laminaUno, "laminaUno");
        this.add(laminaDos, "laminaDos");
    } 
    
    //===== Clase LaminaUno =====
    
    private class LaminaUno extends JPanel{
        
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
        private final VerificarSpinner verificarSpinner = new VerificarSpinner();
        
        private final JLabel separador1 = new JLabel(" : ");
        private final JLabel separador2 = new JLabel(" : ");
        
        private final JButton botonIniciar = new JButton("Iniciar");
        
        public LaminaUno() {
            
            // ----- Panel atajos -----            
            
            panelAtajos.add(asignarBoton(botonCinco, 0, 5, 0));
            panelAtajos.add(asignarBoton(botonQuince, 0, 15, 0));
            panelAtajos.add(asignarBoton(botonTreinta, 0, 30, 0));
            panelAtajos.add(asignarBoton(botonHora, 1, 0, 0));
            
            // ----- Panel spinner -----
            
            spinnerHora.addChangeListener(verificarSpinner);
            spinnerMinuto.addChangeListener(verificarSpinner);
            spinnerSegundo.addChangeListener(verificarSpinner);
            
            panelSpinner.add(spinnerHora);
            panelSpinner.add(separador1);
            panelSpinner.add(spinnerMinuto);
            panelSpinner.add(separador2);
            panelSpinner.add(spinnerSegundo);
            
            // ----- Panel botones -----
            
            botonIniciar.setEnabled(false);
            botonIniciar.addActionListener(new ActionListener(){                
                @Override
                public void actionPerformed(ActionEvent e) {
                    LaminaTemporizador.this.cardLayout.show(LaminaTemporizador.this, "laminaDos");
                    
                    // Capturar el tiempo de los spinner y enviarlos a la lamina 2
                    laminaDos.capturaHoraSpinner = (int)spinnerHora.getValue();
                    laminaDos.capturaMinutoSpinner = (int)spinnerMinuto.getValue();
                    laminaDos.capturaSegundoSpinner = (int)spinnerSegundo.getValue();                     
                    
                    // Resetear JSpinners a 0
                    spinnerHora.setValue(0);
                    spinnerMinuto.setValue(0);
                    spinnerSegundo.setValue(0);
                    
                    // Iniciar timer
                    timer.setInitialDelay(0);
                    timer.start();
                }
            });
            panelBotones.add(botonIniciar);
            
            // añadir los paneles a la lamina
            this.setLayout(new BorderLayout());
            this.add(panelAtajos, BorderLayout.NORTH);
            this.add(panelSpinner, BorderLayout.CENTER);
            this.add(panelBotones, BorderLayout.SOUTH);
        }
        
        // metodo para inicializar botones del panel atajos
        private JButton asignarBoton(JButton boton, int hora, int minuto, int segundo) {
            boton.addActionListener((ActionEvent e) -> {
                spinnerHora.setValue(hora);
                spinnerMinuto.setValue(minuto);
                spinnerSegundo.setValue(segundo);               
            });
            return boton;
        }
        
        // clase que verifica si hay numero en el JSpiner para activar o desactivar boton iniciar
        class VerificarSpinner implements ChangeListener {
            @Override
            public void stateChanged(ChangeEvent e) {
                int numeroHora = (int)spinnerHora.getValue();
                int numeroMinuto = (int)spinnerMinuto.getValue();
                int numeroSegundo = (int)spinnerSegundo.getValue();
                
                if (numeroHora == 0 && numeroMinuto == 0 && numeroSegundo == 0) {
                    botonIniciar.setEnabled(false);
                    
                } else {
                    botonIniciar.setEnabled(true);
                }
            }
        }
    }
    
    //===== Clase LaminaLabel =====
    
    private class LaminaDos extends JPanel implements ActionListener{
        
        private final JPanel panelLabel = new JPanel(new GridBagLayout());
        private final JPanel panelBotones = new JPanel();
        
        private final JLabel labelTiempo = new JLabel("00:00:00");
        
        private final JButton botonPausar = new JButton("Pausar");
        private final JButton botonSalir = new JButton("Salir");
        
        private int capturaHoraSpinner = 0;
        private int capturaMinutoSpinner = 0;
        private int capturaSegundoSpinner = 0;
        
        private long capturaMilisegundosInicio = 0;        
        private long milisegundosTotales = 0;
        private long milisegundosFinal = 0;
        private long tiempoTemporizador = 0;
        
        private int horaFormateado = 0;
        private int minutoFormateado = 0;        
        private int segundoFormateado = 0;
        
        public LaminaDos() {

            // ----- panel label -----
            
            panelLabel.add(labelTiempo);
            
            // ----- panel botones -----             
            
            botonPausar.addActionListener((ActionEvent e) -> {
                if (e.getActionCommand().equals("Pausar")) {
                    botonPausar.setText("Reanudar");
                    timer.stop();
                    
                } else if(e.getActionCommand().equals("Reanudar")){
                    botonPausar.setText("Pausar");
                    milisegundosFinal = System.currentTimeMillis() + tiempoTemporizador;
                    timer.start();
                }
            });
            
            // boton salir
            botonSalir.addActionListener((ActionEvent e) -> {
                LaminaTemporizador.this.cardLayout.show(LaminaTemporizador.this, "laminaUno");
                timer.stop();
                labelTiempo.setText("00:00:00");
                capturaHoraSpinner = 0;
                capturaMinutoSpinner = 0;
                capturaSegundoSpinner = 0;
                capturaMilisegundosInicio = 0;
                milisegundosFinal = 0;
                milisegundosTotales = 0;
                tiempoTemporizador = 0;
            });            
            
            panelBotones.add(botonPausar);
            panelBotones.add(botonSalir);            
            
            this.setLayout(new BorderLayout());
            this.add(panelLabel, BorderLayout.CENTER);
            this.add(panelBotones, BorderLayout.SOUTH);
        }
        
        // ----- evento de timer -----
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if (capturaMilisegundosInicio == 0) {                
                capturaHoraSpinner *= 3600000;
                capturaMinutoSpinner *= 60000;
                capturaSegundoSpinner *= 1000;
                capturaMilisegundosInicio = System.currentTimeMillis();
                milisegundosTotales = (capturaHoraSpinner + capturaMinutoSpinner + capturaSegundoSpinner);
                milisegundosFinal = capturaMilisegundosInicio + milisegundosTotales;
                     
            } if (milisegundosFinal >= System.currentTimeMillis()) {
                tiempoTemporizador = milisegundosFinal - System.currentTimeMillis();
                segundoFormateado = (int)(tiempoTemporizador / 1000) % 60;
                minutoFormateado = (int)(tiempoTemporizador / 60000) % 60;
                horaFormateado = (int)(tiempoTemporizador / 3600000) % 60;
                labelTiempo.setText(String.format("%02d:%02d:%02d", horaFormateado, minutoFormateado, segundoFormateado));
            
            } else if (segundoFormateado == 0 && minutoFormateado == 0 && horaFormateado == 0) {
                timer.stop();              
                Toolkit sonido = Toolkit.getDefaultToolkit();
                sonido.beep();
                botonPausar.setEnabled(false);
            }
        }
    }
}
