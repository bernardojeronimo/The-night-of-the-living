package pt.ulusofona.lp2.thenightofthelivingdeisi.vivos;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Criatura;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Heaven;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Tabuleiro;
import pt.ulusofona.lp2.thenightofthelivingdeisi.items.Item;

public class Cao extends Vivo {
    public Cao(int id, String nome, Posicao coords) {
        super(id, nome, coords);
    }

    @Override
    public boolean regrasMove(Posicao posicaoAtual, Posicao posicaoDestino, Tabuleiro tabuleiro) {
        if (tabuleiro.getCurrentTeamId() == 20) {
            int distX = Math.abs(posicaoDestino.getX() - this.getCoords().getX());
            int distY = Math.abs(posicaoDestino.getY() - this.getCoords().getY());

            if (posicaoAtual.getX() == posicaoDestino.getX() && posicaoAtual.getY() == posicaoDestino.getY()) {
                return true;
            }

            Heaven safeHeaven = tabuleiro.getSafeHeavenAtPosition(posicaoDestino.getX(), posicaoDestino.getY());
            if (safeHeaven != null) {
                return true;
            }

            Item itemNoDestino = tabuleiro.getItemNaPosicao(posicaoDestino.getX(), posicaoDestino.getY());
            if (itemNoDestino != null) {
                return false;
            }

            if (acaoMoverEntreCriaturas(posicaoDestino.getX(),posicaoDestino.getY(),tabuleiro)) {
                if (((distX == 2 || distX == 1) && distY == 0) || ((distY == 2 || distY == 1) && distX == 0)) {
                    setCoords(posicaoDestino);
                    return true;
                }
            }

        }
        return false;
    }

    public String mostraTipo() {
        return "Cão";
    }

    @Override
    public int getTipo(){
        return 3;
    }

    @Override
    public String toString() {
        if(estaNoHeaven){
            return id + " | " + mostraTipo() + " | " + nome + " @ Safe Haven";
        }
        return id + " | " + mostraTipo() + " | " + nome + " @ (" + coords.getX() + ", " + coords.getY() + ")";
    }
}
