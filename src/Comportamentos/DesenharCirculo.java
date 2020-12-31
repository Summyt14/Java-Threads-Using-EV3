package Comportamentos;

import Mensagem.Mensagem;

import Mensagem.MsgCurvaEsq;
import Mensagem.MsgCurvaDir;
import Mensagem.MsgParar;

public class DesenharCirculo extends Comportamento {

	private int raio = 35;
	private int angulo = 360;
	private boolean isLeftSide;

	public DesenharCirculo(boolean isLeftSide) {
		this.isLeftSide = isLeftSide;
	}

	public Mensagem[] executarComportamento(int distancia) {
		if (distancia > 0)
			this.raio = distancia;
		Mensagem[] msgs = new Mensagem[2];
		if (isLeftSide) {
			MsgCurvaEsq m = new MsgCurvaEsq(raio, angulo);
			msgs[0] = m;
		} else {
			MsgCurvaDir m = new MsgCurvaDir(raio, angulo);
			msgs[0] = m;
		}
		MsgParar p = new MsgParar(false);
		msgs[1] = p;
		return msgs;
	}

	public int getDistancia() {
		return raio * 2;
	}
}
