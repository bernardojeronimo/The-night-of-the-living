package pt.ulusofona.lp2.thenightofthelivingdeisi.vivos;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Criatura;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Heaven;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Tabuleiro;
import pt.ulusofona.lp2.thenightofthelivingdeisi.factorys.EntityFactory;
import pt.ulusofona.lp2.thenightofthelivingdeisi.items.Item;

public abstract class Vivo extends Criatura {
    protected int quantidadeItensApanhandos = 0;

    public Vivo(int id, String nome, Posicao coords) {
        super(id, nome, coords);
    }

    public int getId() {
        return this.id;
    }


    public String getNome() {
        return nome;
    }

    public Posicao getCoords() {
        return coords;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        quantidadeItensApanhandos++;
    }


    abstract public String mostraTipo();

    @Override
    public String getSquareInfo() {
        StringBuilder info = new StringBuilder();
        info.append("H:").append(id);
        return info.toString();
    }


    public String[] getCreatureInfo(int id) {
        if (estaNoHeaven) {
            return new String[]{
                    String.valueOf(getId()),
                    mostraTipo(),
                    "Humano",
                    getNome(),
                    null,
                    null,
                    getImagem()
            };
        }
        return new String[]{
                String.valueOf(getId()),
                mostraTipo(),
                "Humano",
                getNome(),
                String.valueOf(getCoords().getX()),
                String.valueOf(getCoords().getY()),
                getImagem()
        };
    }

    private String getImagem() {
        switch (mostraTipo()) {
            case "Criança":
                return "CriancaH.png";
            case "Adulto":
                return "AdultoH.png";
            case "Idoso":
                return "Idoso.png";
            case "Cão":
                return "cao.png";
            case "Desconhecido":
                return "humanoL.png";
            default:
                return null;
        }
    }

    @Override
    public int getTeam() {
        return 20;
    }

    public String getCreatureInfoAsString(int id) {
        if (getId() == id) {
            return toString();
        }
        return null;
    }

    public boolean hasEquipment(int creatureId, int equipmentTypeId) {
        return getItem() != null && getItem().getTipo() == equipmentTypeId;
    }

    @Override
    public boolean acaoMoverEntreCriaturas(int xD, int yD, Tabuleiro tabuleiro) {
        Criatura criaturaD = tabuleiro.getCriaturaNaPosicao(xD, yD);
        if (criaturaD != null) {
            if (criaturaD.getTeam() == getTeam()) {
                return false;
            }
            if (getItem() != null) {
                if (getItem().getTipo() == 1 || getItem().getTipo() == 2) {
                    if (getItem().acao()) {
                        tabuleiro.resetQuantidadeDejogadasSemAtaques();
                        tabuleiro.removerCriatura(criaturaD);
                        return true;
                    }
                    return false;
                }
                if (getItem().getTipo() == 3 || getItem().getTipo() == 4) {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public int getItensDestruidosOuApanhados() {
        return quantidadeItensApanhandos;
    }


    @Override
    public void executarAcaoAoMover(int x0, int y0, int xD, int yD, Tabuleiro tabuleiro, Criatura criatura) {
        Heaven porta = tabuleiro.getSafeHeavenAtPosition(xD, yD);
        Item item = tabuleiro.getItemNaPosicao(xD, yD);
        if (porta != null) {
            tabuleiro.addCriaturaToSafeHeaven(criatura, porta);
            criatura.setEstaNoHeaven();
            tabuleiro.removerCriatura(criatura);
        }
        if (item != null) {
            if (getItem() != null) {
                getItem().setCoords(new Posicao(x0, y0));
                tabuleiro.addItem(getItem());
            }
            setItem(item);
            tabuleiro.removerItem(item);
        }
    }

    public void addQuantidadeItensApanhados() {
        quantidadeItensApanhandos++;
    }

    @Override
    public void transformarLobo(Criatura criatura, Tabuleiro tabuleiro) {
        System.out.println(criatura.getTipo());
        if (tabuleiro.isDay()) {
            tabuleiro.removerCriatura(criatura);
            Criatura loboTranstornado = EntityFactory.createEntity(criatura.getId(), 20, criatura.getTipo(), criatura.getNome(), criatura.getCoords());
            for (int i = 0; criatura.getItensDestruidosOuApanhados() > i; i++) {
                loboTranstornado.addQuantidadeItensApanhados();
            }
            tabuleiro.addCriaturas(loboTranstornado);
            loboTranstornado.setTransformado(false);
        }
    }


    @Override
    public String isString() {
        return getId() + " " + getNome();
    }


    @Override
    public String toString() {
        if (estaNoHeaven) {
            return id + " | " + mostraTipo() + " | Humano | " + nome + " | +" + quantidadeItensApanhandos + " @ Safe Haven";
        }
        if (getItem() != null) {
            return id + " | " + mostraTipo() + " | Humano | " + nome + " | +" + quantidadeItensApanhandos + " @ (" + coords.getX() + ", " + coords.getY() + ") | " + getItem().toString();
        }
        return id + " | " + mostraTipo() + " | Humano | " + nome + " | +" + quantidadeItensApanhandos + " @ (" + coords.getX() + ", " + coords.getY() + ")";
    }
}
