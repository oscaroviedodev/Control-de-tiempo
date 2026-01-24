import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LaminaBotones extends JPanel{
    
    private final JButton botonReloj = new JButton("Reloj");
    private final JButton botonAlarma = new JButton("Alarma");
    private final JButton botonCronometro = new JButton("Cronometro");
    private final JButton botonTemporizador = new JButton("Temporizador");
    
    public LaminaBotones(LaminaContenedora laminaContenedora) {
        this.setLayout(new GridLayout(1, 4));
        this.add(botonReloj);
        this.add(botonAlarma);
        this.add(botonCronometro);
        this.add(botonTemporizador);
        
        botonReloj.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                laminaContenedora.cardLayout.show(laminaContenedora, "reloj");
            }
        });
        
        botonAlarma.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                laminaContenedora.cardLayout.show(laminaContenedora, "alarma");
            }
        });
        
        botonCronometro.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                laminaContenedora.cardLayout.show(laminaContenedora, "cronometro");
            }
        });
        
        botonTemporizador.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                laminaContenedora.cardLayout.show(laminaContenedora, "temporizador");
            }
        });
    }
}
