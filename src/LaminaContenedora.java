import java.awt.CardLayout;
import javax.swing.JPanel;

public class LaminaContenedora extends JPanel{
    
    protected final CardLayout cardLayout = new CardLayout();
    private final LaminaReloj miLaminaReloj = new LaminaReloj();
    private final LaminaAlarma miLaminaAlarma = new LaminaAlarma();
    private final LaminaCronometro miLaminaCronometro = new LaminaCronometro();
    private final LaminaTemporizador miLaminaTemporizador = new LaminaTemporizador();
    
    public LaminaContenedora() {
        this.setLayout(cardLayout);
        this.add(miLaminaReloj, "reloj");
        this.add(miLaminaAlarma, "alarma");
        this.add(miLaminaCronometro, "cronometro");
        this.add(miLaminaTemporizador, "temporizador");
    }
}
