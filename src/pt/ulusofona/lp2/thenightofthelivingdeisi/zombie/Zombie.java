package pt.ulusofona.lp2.thenightofthelivingdeisi.zombie;

import pt.ulusofona.lp2.thenightofthelivingdeisi.Criatura;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Posicao;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Tabuleiro;
import pt.ulusofona.lp2.thenightofthelivingdeisi.factorys.EntityFactory;
import pt.ulusofona.lp2.thenightofthelivingdeisi.items.Item;


public abstract class Zombie extends Criatura {
    protected int itensDestruidos = 0;

    public Zombie(int id, String nome, Posicao coords) {
        super(id, nome, coords);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Posicao getCoords() {
        return coords;
    }

    @Override
    public String getSquareInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Z:").append(id);
        return info.toString();
    }

    public String[] getCreatureInfo(int id) {
        if (transformado) {
            return new String[]{
                    String.valueOf(getId()),
                    mostraTipo(),
                    "Zombie (Transformado)",
                    getNome(),
                    String.valueOf(getCoords().getX()),
                    String.valueOf(getCoords().getY()),
                    getImagem()
            };
        }
        return new String[]{
                String.valueOf(getId()),
                mostraTipo(),
                "Zombie",
                getNome(),
                String.valueOf(getCoords().getX()),
                String.valueOf(getCoords().getY()),
                getImagem()
        };
    }

    @Override
    public void addItensDestruidos() {
        itensDestruidos++;
    }


    private String getImagem() {
        switch (mostraTipo()) {
            case "Criança":
                return "CriancaZ.png";
            case "Adulto":
                return "AdultoZ.png";
            case "Idoso":
                return "idosoZ.png";
            case "Vampiro":
                return "vampiro.png";
            case "Lobisomem":
                return "lobisomen.png";
            default:
                return null;
        }
    }

    @Override
    public int getItensDestruidosOuApanhados() {
        return itensDestruidos;
    }

    @Override
    public int getTeam() {
        return 10;
    }

    abstract public String mostraTipo();

    public String getCreatureInfoAsString(int id) {
        if (getId() == id) {
            return toString();
        }
        return null;
    }

    @Override
    public boolean acaoMoverEntreCriaturas(int xD, int yD, Tabuleiro tabuleiro) {
        Criatura criaturaD = tabuleiro.getCriaturaNaPosicao(xD, yD);
        if (criaturaD != null) {
            if (criaturaD.getTeam() == getTeam() || criaturaD.getTipo() == 3) {
                return false;
            }
            if (tabuleiro.getCriaturaNaPosicao(xD, yD).getItem() != null) {
                return true;
            }
            if (criaturaD.getItem() == null) {
                System.out.println(criaturaD.getTipo());
                transformar(criaturaD, tabuleiro);
            }
        }
        return true;
    }

    public void transformar(Criatura criatura, Tabuleiro tabuleiro) {
        System.out.println(criatura.getTipo());
        tabuleiro.resetQuantidadeDejogadasSemAtaques();
        tabuleiro.removerCriatura(criatura);
        Criatura zombieTranstornado = EntityFactory.createEntity(criatura.getId(), 10, criatura.getTipo(), criatura.getNome(), criatura.getCoords());
        for (int i = 0; criatura.getItensDestruidosOuApanhados() > i; i++) {
            zombieTranstornado.addItensDestruidos();
        }
        tabuleiro.addCriaturas(zombieTranstornado);
        zombieTranstornado.setTransformado(true);
    }

    @Override
    public void transformarLobo(Criatura criatura, Tabuleiro tabuleiro) {
        System.out.println(criatura.getTipo());
        if (tabuleiro.isDay()) {
            tabuleiro.removerCriatura(criatura);
            Criatura zombieTranstornado = EntityFactory.createEntity(criatura.getId(), 10, criatura.getTipo(), criatura.getNome(), criatura.getCoords());
            for (int i = 0; criatura.getItensDestruidosOuApanhados() > i; i++) {
                zombieTranstornado.addItensDestruidos();
            }
            tabuleiro.addCriaturas(zombieTranstornado);
            zombieTranstornado.setTransformado(true);
        }
    }

    @Override
    public void setTransformado(boolean transformado) {
        this.transformado = transformado;
    }

    @Override
    public void executarAcaoAoMover(int x0, int y0, int xD, int yD, Tabuleiro tabuleiro, Criatura criatura) {
        Item item = tabuleiro.getItemNaPosicao(xD, yD);
        if (item != null) {
            addItensDestruidos();
            tabuleiro.removerItem(item);
        }
    }

    public String isString() {
        return getId() + " (antigamente conhecido como " + getNome() + ")";
    }

    public boolean hasEquipment(int creatureId, int equipmentTypeId) {
        if (getId() == creatureId) {
            return false;
        }
        return false;
    }

    public boolean zombieAtaques(Criatura criatura, Posicao posicaoAtual, Tabuleiro tabuleiro) {
        if (criatura != null) {
            Item item = criatura.getItem();
            setCoords(posicaoAtual);
            if (item != null) {
                if (!criatura.getItem().acao()) {
                    transformar(criatura, tabuleiro);
                    transformado = true;
                    return true;
                }
            }
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        if (transformado) {
            return id + " | " + mostraTipo() + " | Zombie (Transformado) | " + nome + " | -" + itensDestruidos + " @ (" + coords.getX() + ", " + coords.getY() + ")";
        }
        return id + " | " + mostraTipo() + " | Zombie | " + nome + " | -" + itensDestruidos + " @ (" + coords.getX() + ", " + coords.getY() + ")";
    }
}
