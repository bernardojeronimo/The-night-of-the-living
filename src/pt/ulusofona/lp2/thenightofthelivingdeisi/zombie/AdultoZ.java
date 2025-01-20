package pt.ulusofona.lp2.thenightofthelivingdeisi.zombie;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Criatura;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Heaven;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Tabuleiro;

public class AdultoZ extends Zombie {
    public AdultoZ(int id, String nome, Posicao coords) {
        super(id, nome, coords);
    }

    @Override
    public boolean regrasMove(Posicao posicaoAtual, Posicao posicaoDestino, Tabuleiro tabuleiro) {
        if (tabuleiro.getCurrentTeamId() == 10) {
            Criatura criatura = tabuleiro.getCriaturaNaPosicao(posicaoDestino.getX(),posicaoDestino.getY());
            int distX = Math.abs(posicaoDestino.getX() - this.getCoords().getX());
            int distY = Math.abs(posicaoDestino.getY() - this.getCoords().getY());

            Heaven safeHeaven = tabuleiro.getSafeHeavenAtPosition(posicaoDestino.getX(), posicaoDestino.getY());
            if (safeHeaven != null) {
                return false;
            }

                if (posicaoAtual.getX() == posicaoDestino.getX() && posicaoAtual.getY() == posicaoDestino.getY()) {
                    return false;
                }


                if (distX == distY && distX <= 2) {
                    if(zombieAtaques(criatura,posicaoAtual,tabuleiro)){
                        return true;
                    }
                    setCoords(posicaoDestino);
                    return true;
                }


                if ((distX == 0 && distY <= 2) || (distY == 0 && distX <= 2)) {
                    if (acaoMoverEntreCriaturas(posicaoDestino.getX(),posicaoDestino.getY(),tabuleiro)) {
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

    @Override
    public int getTipo(){
        return 1;
    }

    public String mostraTipo() {
        return "Adulto";
    }
}
