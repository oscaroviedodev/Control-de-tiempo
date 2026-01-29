import javax.swing.JFrame;

public class Marco extends JFrame{
    
    private final LaminaPrincipal laminaPrincipal = new LaminaPrincipal();
    
    public Marco() {
        this.setSize(300, 250);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        
        this.add(laminaPrincipal);
        
    }
}
