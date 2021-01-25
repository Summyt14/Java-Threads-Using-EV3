
public class EspacarFormasGeometricas extends Comportamento {

	private int distancia;

	public EspacarFormasGeometricas(int distancia, ClienteDoRobot clienteDoRobot, IGUI gui) {
		super(false, clienteDoRobot, gui);
		this.distancia = distancia;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int dist) {
		this.distancia = dist;
	}

	public void desenho() {
		getCliente().espacar(distancia);
		setEspera(distancia);
		getCliente().parar(false);
		setEspera(10);
		if (getProxComportamento() != null)
			getProxComportamento().ativar(null);
	}
}
