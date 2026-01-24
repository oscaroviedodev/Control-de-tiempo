import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.time.LocalTime;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class LaminaReloj extends JPanel{
    
    private final Timer timer;
    private final Reloj reloj = new Reloj();
    private final JLabel labelReloj = new JLabel();    
    
    public LaminaReloj() {
        this.setLayout(new GridBagLayout());
        timer = new Timer(1000, reloj);
        timer.setInitialDelay(0);
        timer.start();
        this.add(labelReloj);
    }
    
    class Reloj implements ActionListener{        
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalTime horaEnVivo = LocalTime.now();   
            int hora = horaEnVivo.getHour();
            int minuto = horaEnVivo.getMinute();
            int segundo = horaEnVivo.getSecond();
            String horaFormateada = String.format("%02d:%02d:%02d", hora, minuto, segundo);
            labelReloj.setText(horaFormateada);
        }
    }
    
}
