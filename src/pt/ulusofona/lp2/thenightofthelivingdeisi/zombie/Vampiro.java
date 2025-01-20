package pt.ulusofona.lp2.thenightofthelivingdeisi.zombie;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Criatura;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Heaven;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Tabuleiro;

public class Vampiro extends Zombie {

    public Vampiro(int id, String nome, Posicao coords) {
        super(id, nome, coords);
    }


    @Override
    public boolean regrasMove(Posicao posicaoAtual, Posicao posicaoDestino, Tabuleiro tabuleiro) {
        if (tabuleiro.getCurrentTeamId() == 10) {
            if (tabuleiro.isDay()) {
                return false;
            }
            Criatura criatura = tabuleiro.getCriaturaNaPosicao(posicaoDestino.getX(), posicaoDestino.getY());
            int distX = Math.abs(posicaoDestino.getX() - this.getCoords().getX());
            int distY = Math.abs(posicaoDestino.getY() - this.getCoords().getY());

            Heaven safeHeaven = tabuleiro.getSafeHeavenAtPosition(posicaoDestino.getX(), posicaoDestino.getY());
            if (safeHeaven != null) {
                return false;
            }

            if (posicaoAtual.getX() == posicaoDestino.getX() && posicaoAtual.getY() == posicaoDestino.getY()) {
                return false;
            }


            if (distX <= 1 && distY <= 1) {
                if (acaoMoverEntreCriaturas(posicaoDestino.getX(), posicaoDestino.getY(), tabuleiro)) {
                    if (zombieAtaques(criatura, posicaoAtual, tabuleiro)) {
                        return true;
                    }
                    setCoords(posicaoDestino);
                    return true;
                }
            }
        }
        return false;
    }

    public String mostraTipo() {
        return "Vampiro";
    }

    @Override
    public int getTipo() {
        return 4;
    }

    @Override
    public String toString() {
        return id + " | " + mostraTipo() + " | " + nome + " | -" + itensDestruidos + " @ (" + coords.getX() + ", " + coords.getY() + ")";
    }
}
