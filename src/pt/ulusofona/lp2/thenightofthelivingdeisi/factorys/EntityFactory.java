package pt.ulusofona.lp2.thenightofthelivingdeisi.factorys;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Criatura;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;
import pt.ulusofona.lp2.thenightofthelivingdeisi.vivos.*;
import pt.ulusofona.lp2.thenightofthelivingdeisi.zombie.*;


public class EntityFactory {

    public static Criatura createEntity(int id, int equipa, int tipo, String nome, Posicao posicao) {

        switch (tipo) {
            case 0:
                if (equipa == 10) {
                    return new CriancaZ(id, nome, posicao);
                }
                if (equipa == 20) {
                    return new CriancaH(id, nome, posicao);
                }

            case 1:
                if (equipa == 10) {
                    return new AdultoZ(id, nome, posicao);
                }
                if (equipa == 20) {
                    return new AdultoH(id, nome, posicao);
                }

            case 2:
                if (equipa == 10) {
                    return new IdosoZ(id, nome, posicao);
                }
                if (equipa == 20) {
                    return new IdosoH(id, nome, posicao);
                }

            case 3:
                if (equipa == 20) {
                    return new Cao(id, nome, posicao);
                } else {
                    return null;
                }

            case 4:
                if (equipa == 10) {
                    return new Vampiro(id, nome, posicao);
                } else {
                    return null;
                }
            case 5:
                if (equipa == 10) {
                    return new Lobisomem(id, nome, posicao);
                }
                if (equipa == 20) {
                    return new LoboHuman(id, nome, posicao);
                }
            default:
                return null;
        }
    }


}
