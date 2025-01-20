package pt.ulusofona.lp2.thenightofthelivingdeisi;

import pt.ulusofona.lp2.thenightofthelivingdeisi.items.Item;

public abstract class Criatura {
    protected int id;
    protected String nome;
    protected Posicao coords = new Posicao();
    protected Item item;
    protected boolean estaNoHeaven = false;
    protected boolean transformado = false;

    public Criatura(int id, String nome, Posicao coords) {
        this.id = id;
        this.nome = nome;
        this.coords = coords;
    }


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getPosX() {
        return coords.getX();
    }

    public Posicao getCoords() {
        return coords;
    }

    public int getPosY() {
        return coords.getY();
    }

    public boolean podeMover(Posicao posicaoAtual, Posicao posicaoDestino, Tabuleiro tabuleiro) {
        System.out.println(tabuleiro.getCurrentTeamId());
        return regrasMove(posicaoAtual, posicaoDestino, tabuleiro);
    }

    abstract public String getSquareInfo();

    abstract public boolean acaoMoverEntreCriaturas(int xD, int yD, Tabuleiro tabuleiro);

    abstract public int getTeam();

    abstract public String[] getCreatureInfo(int id);

    abstract public String getCreatureInfoAsString(int id);

    abstract public boolean hasEquipment(int creatureId, int equipmentTypeId);

    abstract public boolean regrasMove(Posicao posicaoAtual, Posicao posicaoDestino, Tabuleiro tabuleiro);

    abstract public void executarAcaoAoMover(int x0, int y0, int xD, int yD, Tabuleiro tabuleiro, Criatura criatura);

    abstract public String isString();

    abstract public int getTipo();

    abstract public int getItensDestruidosOuApanhados();

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }


    public void setTransformado(boolean transformado) {
        this.transformado = transformado;
    }

    public void addItensDestruidos() {
    }

    public void addQuantidadeItensApanhados() {
    }

    public void setCoords(Posicao coords) {
        this.coords = coords;
    }

    public void setEstaNoHeaven() {
        this.estaNoHeaven = true;
    }

    public abstract void transformarLobo(Criatura criatura, Tabuleiro tabuleiro);

}
