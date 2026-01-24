import java.awt.BorderLayout;
import javax.swing.JPanel;

public class LaminaPrincipal extends JPanel{
    
    private final LaminaContenedora miLaminaContenedora = new LaminaContenedora();
    private final LaminaBotones miLaminaBotones = new LaminaBotones(miLaminaContenedora);    
    
    public LaminaPrincipal() {
        this.setLayout(new BorderLayout());
        this.add(miLaminaBotones, BorderLayout.NORTH);
        this.add(miLaminaContenedora, BorderLayout.CENTER);
    }
}
