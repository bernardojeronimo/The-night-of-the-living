package pt.ulusofona.lp2.thenightofthelivingdeisi.factorys;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;
import pt.ulusofona.lp2.thenightofthelivingdeisi.items.*;


public class ItemsFactory {

    public static Item createItem(int id, int tipo, Posicao posicao) {

        return switch (tipo) {
            case 0 -> new EscudoM(id, tipo, posicao);
            case 1 -> new EspadaS(id, tipo, posicao);
            case 2 -> new Pistola(id, tipo, posicao);
            case 3 -> new Lixivia(id, tipo, posicao);
            default -> null;
        };
    }
}
