package Comportamentos;
import Mensagem.Mensagem;

public abstract class Comportamento extends Thread {

	public static int velocidade = 30;

	public Mensagem[] executarComportamento(){
		return executarComportamento(0);
	}
	public abstract Mensagem[] executarComportamento(int distancia);

	public abstract int getDistancia();

}
