package pt.ulusofona.lp2.thenightofthelivingdeisi.items;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;

public class EscudoM extends Item {

    public EscudoM(int id, int tipo, Posicao coords) {
        super(id, tipo, coords);
    }

    @Override
    public String mostraTipo() {
        return "Escudo de madeira";
    }

    @Override
    public int capacidade(){
        return 1;
    }
}
