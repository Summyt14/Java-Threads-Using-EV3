package Comportamentos;

import Mensagem.Mensagem;
import Mensagem.MsgReta;
import Mensagem.MsgParar;
import Mensagem.MsgEspacar;


public class EspacarFormasGeometricas extends Comportamento{

    private int distancia;

    public Mensagem[] executarComportamento(int distancia) {
        Mensagem[] msgs = new Mensagem[2];
        MsgEspacar m = new MsgEspacar(distancia);
        msgs[0] = m;
        MsgParar p = new MsgParar(false);
        msgs[1] = p;
        return msgs;
    }

    public void setDistancia(int dist){
        this.distancia = dist;
    }

    public int getDistancia(){
        return 0;
    }
}
