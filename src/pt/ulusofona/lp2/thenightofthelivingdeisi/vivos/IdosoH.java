package pt.ulusofona.lp2.thenightofthelivingdeisi.vivos;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Criatura;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Heaven;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Tabuleiro;
import pt.ulusofona.lp2.thenightofthelivingdeisi.items.Item;

public class IdosoH extends Vivo {

    public IdosoH(int id, String nome, Posicao coords) {
        super(id, nome, coords);
    }

    @Override
    public boolean regrasMove(Posicao posicaoAtual, Posicao posicaoDestino, Tabuleiro tabuleiro) {
        if (tabuleiro.getCurrentTeamId() == 20) {
            if (!tabuleiro.isDay()) {
                return false;
            }

            int distX = Math.abs(posicaoDestino.getX() - this.getPosX());
            int distY = Math.abs(posicaoDestino.getY() - this.getPosY());

            if (getItem() != null) {
                getItem().setCoords(posicaoAtual);
                tabuleiro.addItem(getItem());
                item = null;
            }

            Heaven safeHeaven = tabuleiro.getSafeHeavenAtPosition(posicaoDestino.getX(), posicaoDestino.getY());
            if (safeHeaven != null) {
                return true;
            }

            if (acaoMoverEntreCriaturas(posicaoDestino.getX(), posicaoDestino.getY(), tabuleiro)) {

                if (posicaoAtual.getX() == posicaoDestino.getX() && posicaoAtual.getY() == posicaoDestino.getY()) {
                    return false;
                }

                if (distX == 1 && distY == 1) {
                    if (getItem() != null) {
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
    public void executarAcaoAoMover(int x0, int y0, int xD, int yD, Tabuleiro tabuleiro, Criatura criatura) {
        Item item = tabuleiro.getItemNaPosicao(xD, yD);
        if (item != null) {
            setItem(item);
            tabuleiro.removerItem(item);
        }
    }


    @Override
    public int getTipo() {
        return 2;
    }

    public String mostraTipo() {
        return "Idoso";
    }

}
