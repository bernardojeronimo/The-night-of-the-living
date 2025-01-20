package pt.ulusofona.lp2.thenightofthelivingdeisi.items;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;

public class EspadaS extends Item {


    public EspadaS(int id, int tipo, Posicao coords) {
        super(id, tipo, coords);
    }

    @Override
    public String mostraTipo() {
        return "Espada samurai";
    }


    @Override
    public int capacidade(){
        return 1;
    }
}
