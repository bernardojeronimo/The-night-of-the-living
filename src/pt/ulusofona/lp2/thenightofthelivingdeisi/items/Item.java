package pt.ulusofona.lp2.thenightofthelivingdeisi.items;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;

public abstract class Item {
    protected int id;
    protected int tipo;
    protected Posicao coords = new Posicao();

    public Item(int id, int tipo, Posicao coords) {
        this.id = id;
        this.tipo = tipo;
        this.coords = coords;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public Posicao getCoords() {
        return coords;
    }


    abstract public String mostraTipo();

    public String[] getEquipmentInfo(int id) {
        return new String[]{
                String.valueOf(getId()),
                String.valueOf(getTipo()),
                String.valueOf(getCoords().getX()),
                String.valueOf(getCoords().getY()),
                getImagem()
        };
    }

    private String getImagem() {
        switch (mostraTipo()) {
            case "Escudo de madeira":
                return "escudo.png";
            case "Espada samurai":
                return "espada.png";
            case "Lixívia":
                return "molo.png";
            case "Pistola Walther PPK":
                return "pistola.png";
            default:
                return null;
        }
    }

    public String getSquareInfo() {
        StringBuilder info = new StringBuilder();
        info.append("E:").append(getId());
        return info.toString();
    }

    public void setCoords(Posicao coords) {
        this.coords = coords;
    }

    public boolean acao() {
        System.out.println("Acao: " + mostraTipo());
        return true;
    }

    abstract public int capacidade();

    @Override
    public String toString() {
        return id + " | " + mostraTipo() + " @ (" + coords.getX() + ", " + coords.getY() + ")";
    }

}
