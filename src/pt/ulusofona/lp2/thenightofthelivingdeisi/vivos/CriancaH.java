package pt.ulusofona.lp2.thenightofthelivingdeisi.vivos;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Criatura;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Heaven;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Tabuleiro;
import pt.ulusofona.lp2.thenightofthelivingdeisi.items.Item;

public class CriancaH extends Vivo {
    public CriancaH(int id, String nome, Posicao coords) {
        super(id, nome, coords);
    }

    @Override
    public boolean regrasMove(Posicao posicaoAtual, Posicao posicaoDestino, Tabuleiro tabuleiro) {
        if (tabuleiro.getCurrentTeamId() == 20) {
            int distX = Math.abs(posicaoDestino.getX() - this.getCoords().getX());
            int distY = Math.abs(posicaoDestino.getY() - this.getCoords().getY());


            Item itemNoDestino = tabuleiro.getItemNaPosicao(posicaoDestino.getX(), posicaoDestino.getY());
            if (itemNoDestino != null) {
                if(itemNoDestino.getTipo() == 0 || itemNoDestino.getTipo() == 3){
                    setCoords(posicaoDestino);
                    return true;
                }
                return false;
            }

            Heaven safeHeaven = tabuleiro.getSafeHeavenAtPosition(posicaoDestino.getX(), posicaoDestino.getY());
            if (safeHeaven != null) {
                return true;
            }

            if (acaoMoverEntreCriaturas(posicaoDestino.getX(),posicaoDestino.getY(),tabuleiro)) {

                if (posicaoAtual.getX() == posicaoDestino.getX() && posicaoAtual.getY() == posicaoDestino.getY()) {
                    return false;
                }
                if ((distX == 1 && distY == 0) || (distY == 1 && distX == 0)) {
                    if (getItem() != null){
                        getItem().setCoords(posicaoDestino);
                    }
                    setCoords(posicaoDestino);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getTipo(){
        return 0;
    }

    public String mostraTipo() {
        return "Criança";
    }
}
