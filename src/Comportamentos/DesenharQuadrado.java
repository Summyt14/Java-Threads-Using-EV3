package Comportamentos;

import Mensagem.MsgParar;
import Mensagem.MsgReta;
import Mensagem.Mensagem;
import Mensagem.MsgCurvaDir;
import Mensagem.MsgCurvaEsq;

public class DesenharQuadrado extends Comportamento {

	private int angulo = 90;
	private int raio = 0;
	private int dist = 5;
	private boolean isLeftSide;

	public DesenharQuadrado(boolean isLeftSide) {
		this.isLeftSide = isLeftSide;
	}

	@Override
	public Mensagem[] executarComportamento(int distancia) {
		if(distancia > 0)
			this.dist = distancia;
		Mensagem[] msgs = new Mensagem[9];
		MsgReta m = new MsgReta(dist);
		msgs[0] = m;
		System.out.println(m);
		if (isLeftSide) {
			MsgCurvaEsq c = new MsgCurvaEsq(raio, angulo);
			msgs[1] = c;
			m = new MsgReta(dist);
			msgs[2] = m;
			c = new MsgCurvaEsq(raio, angulo);
			msgs[3] = c;
			m = new MsgReta(dist);
			msgs[4] = m;
			c = new MsgCurvaEsq(raio, angulo);
			msgs[5] = c;
			m = new MsgReta(dist);
			msgs[6] = m;
			c = new MsgCurvaEsq(raio, angulo);
			msgs[7] = c;
		} else {
			MsgCurvaDir c = new MsgCurvaDir(raio, angulo);
			msgs[1] = c;
			m = new MsgReta(dist);
			msgs[2] = m;
			c = new MsgCurvaDir(raio, angulo);
			msgs[3] = c;
			m = new MsgReta(dist);
			msgs[4] = m;
			c = new MsgCurvaDir(raio, angulo);
			msgs[5] = c;
			m = new MsgReta(dist);
			msgs[6] = m;
			c = new MsgCurvaDir(raio, angulo);
			msgs[7] = c;
		}
		MsgParar p = new MsgParar(false);
		msgs[8] = p;
		return msgs;
	}

	public int getDistancia() {
		return dist;
	}
}
