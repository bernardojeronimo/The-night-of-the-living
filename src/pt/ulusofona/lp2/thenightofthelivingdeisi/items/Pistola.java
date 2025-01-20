package pt.ulusofona.lp2.thenightofthelivingdeisi.items;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;

public class Pistola extends Item {
    private int uso = 0;
    private int balas = 3;


    public Pistola(int id, int tipo, Posicao coords) {
        super(id, tipo, coords);
    }

    @Override
    public String mostraTipo() {
        return "Pistola Walther PPK";
    }

    @Override
    public boolean acao() {
        if (uso < 3) {
            balas--;
            uso++;
            return true;
        } else if (uso == 3) {
            balas = 0;
            uso++;
        }
        return false;
    }

    @Override
    public int capacidade(){
        return balas;
    }

    @Override
    public String toString() {
        return id + " | " + mostraTipo() + " @ (" + coords.getX() + ", " + coords.getY() + ") | " + balas + " balas";
    }
}
