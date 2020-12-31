import com.sun.security.ntlm.Client;

public class EspacarFormasGeometricas extends Comportamento{

    private int distancia;
    private GUI gui;

    public EspacarFormasGeometricas(int distancia, ClienteDoRobot clienteDoRobot, GUI gui){
        super(false, clienteDoRobot, gui);
        this.distancia = distancia;
        this.gui = gui;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int dist){
        this.distancia = dist;
    }

    public void desenho() {
        getCliente().espacar(distancia);
        System.out.println("Vou espaçar " + distancia);
        setEspera(distancia);
        getCliente().parar(false);
        setEspera(10);
        gui.setAcabouEspacar(true);
    }
}
