package pt.ulusofona.lp2.thenightofthelivingdeisi;

import java.util.ArrayList;

public class Heaven {
    private Posicao posicao;
    private ArrayList<Criatura> vivos = new ArrayList<>();
    ;

    public Heaven(Posicao posicao) {
        this.posicao = posicao;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public String getSquareInfo() {
        return "SH";
    }

    public void addVivos(Criatura criatura) {
        vivos.add(criatura);
    }

    public ArrayList<Criatura> safeVivos() {
        return vivos;
    }

    @Override
    public String toString() {
        return "Porta para Safe Heaven";
    }
}