import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class LaminaCronometro extends JPanel{
    
    private final JLabel labelTiempo = new JLabel("00:00:00.00");    
    private final JButton botonIniciarPausar = new JButton("Iniciar");
    private final JButton botonReiniciar = new JButton("Detener"); 
    private final JPanel panelBotones = new JPanel();    
    private final JPanel panelTiempo = new JPanel(new GridBagLayout());
    
    private final Timer timer = new Timer(10, new LogicaCronometro());
    
    private long inicio = System.currentTimeMillis();
    private long ahora = 0;
    private long acumulado = 0;
    
    public LaminaCronometro() {        
        this.setLayout(new BorderLayout());
        
        // Lamina tiempo
        panelTiempo.add(labelTiempo);
        this.add(panelTiempo, BorderLayout.CENTER);
        botonIniciarPausar.addActionListener(new MarchaCronometro());
                
        // Lamina botones
        panelBotones.add(botonIniciarPausar);
        panelBotones.add(botonReiniciar);     
        this.add(panelBotones, BorderLayout.SOUTH);
        botonReiniciar.addActionListener(new ReiniciarCronometro());        
    }

    private class MarchaCronometro implements ActionListener{   
        
        @Override
        public void actionPerformed(ActionEvent e) {            
            switch (e.getActionCommand()) {                
                case "Iniciar":
                    timer.start();
                    inicio = System.currentTimeMillis() - acumulado;
                    botonIniciarPausar.setText("Pausar");
                    break;
                    
                case "Pausar":
                    timer.stop();
                    botonIniciarPausar.setText("Reanudar");                   
                    break;
                    
                case "Reanudar":
                    timer.start();
                    inicio = System.currentTimeMillis() - acumulado;
                    botonIniciarPausar.setText("Pausar");
                    break;
            }            
        }
    }
    
    private class ReiniciarCronometro implements ActionListener{  
        
        @Override
        public void actionPerformed(ActionEvent e) {
            labelTiempo.setText("00:00:00.00");
            inicio = 0;
            ahora = 0;
            acumulado = 0;
            botonIniciarPausar.setText("Iniciar");
            timer.stop();
        }
    }

    private class LogicaCronometro implements ActionListener{  
        
        @Override
        public void actionPerformed(ActionEvent e) {
            ahora = System.currentTimeMillis();
            acumulado = ahora - inicio;
            
            int milisegundo = (int)(acumulado % 1000) / 10;
            int segundo = (int)(acumulado/1000) % 60;
            int minuto = (int)(acumulado/60000) % 60;
            int hora = (int)(acumulado/3600000) % 24;
            
            String horaForamateada = String.format("%02d:%02d:%02d.%02d", hora, minuto, segundo, milisegundo);            
            labelTiempo.setText(horaForamateada);            
        }    
    }    
}
