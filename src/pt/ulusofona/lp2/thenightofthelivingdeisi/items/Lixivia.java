package pt.ulusofona.lp2.thenightofthelivingdeisi.items;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;

public class Lixivia extends Item {

    private float litros = 1.0F;
    private int uso = 0;


    public Lixivia(int id, int tipo, Posicao coords) {
        super(id, tipo, coords);
    }

    @Override
    public String mostraTipo() {
        return "Lixívia";
    }

    @Override
    public boolean acao() {
        if (uso < 3) {
            litros = (float) (Math.round((litros - 0.3) * 10.0) / 10.0);
            uso++;
            return true;
        } else if (uso == 3) {
            litros = 0.0F;
            uso++;
            return true;
        }
        return false;
    }
    public float getLitros() {
        return litros;
    }
    @Override
    public int capacidade(){
        return (int) litros;
    }

    @Override
    public String toString() {
        return id + " | " + mostraTipo() + " @ (" + coords.getX() + ", " + coords.getY() + ") | " + litros + " litros";
    }
}
