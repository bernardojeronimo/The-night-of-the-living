package pt.ulusofona.lp2.thenightofthelivingdeisi;

import pt.ulusofona.lp2.thenightofthelivingdeisi.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    private int x;
    private int y;
    private ArrayList<Criatura> criaturas = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Heaven> safeZone = new ArrayList<>();
    private int quantidadeDejogadasSemAtaques = 0;
    private int jogada;
    private int turno = 0;

    private int qEnt;

    private int qIt;
    private int qPortas;

    public Tabuleiro(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tabuleiro() {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] getSize() {
        return new int[]{getX(), getY()};
    }

    public int getJogada() {
        return this.jogada;
    }

    public int getTurno() {
        return turno;
    }

    public void addQuantidadeDejogadasSemAtaques() {
        quantidadeDejogadasSemAtaques++;
    }

    public int getQuantidadeDejogadasSemAtaques() {
        return quantidadeDejogadasSemAtaques;
    }

    public void resetQuantidadeDejogadasSemAtaques() {
        quantidadeDejogadasSemAtaques = -1;
    }

    public int getqEntidades() {
        return qEnt;
    }

    public void setqEntidades(int qEnt) {
        this.qEnt = qEnt;
    }

    public int getqItems() {
        return qIt;
    }

    public void setqItems(int qIt) {
        this.qIt = qIt;
    }

    public int getqPortas() {
        return qPortas;
    }

    public void setqPortas(int i) {
        this.qPortas = i;
    }


    public void setJogada(int jogada) {
        this.jogada = jogada;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public ArrayList<Criatura> getCriaturas() {
        return criaturas;
    }

    public ArrayList<Heaven> getSafeZone() {
        return safeZone;
    }


    public void addCriaturas(Criatura perso) {
        criaturas.add(perso);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addPortas(Heaven porta) {
        safeZone.add(porta);
    }


    public int getCurrentTeamId() {
        if (getJogada() == 20) {
            setJogada(10);
        } else {
            setJogada(20);
        }
        return jogada;
    }


    public boolean isDay() {
        return ((turno / 2) % 2 == 0);
    }


    public String getSquareInfo(int x, int y) {
        if (x < 0 || x > getX() || y < 0 || y > getY()) {
            return null;
        }

        String info = "";

        for (Heaven door : getSafeZone()) {
            if (door.getPosicao().getX() == x && door.getPosicao().getY() == y) {
                info = door.getSquareInfo();
            }
        }

        for (Criatura criatura : getCriaturas()) {
            if (criatura.getCoords() != null) {
                if (criatura.getPosX() == x && criatura.getPosY() == y) {
                    info = criatura.getSquareInfo();
                }
            }
        }


        for (Item item : getItems()) {
            if (item.getCoords().getX() == x && item.getCoords().getY() == y) {
                info = item.getSquareInfo();
            }
        }

        return info.toString();
    }

    public Item getItemNaPosicao(int x, int y) {
        for (Item item : items) {
            if (item.getCoords().getX() == x && item.getCoords().getY() == y) {
                return item;
            }
        }
        return null;
    }

    public Criatura getCriaturaNaPosicao(int x, int y) {
        for (Criatura criatura : criaturas) {
            if (criatura.getPosX() == x && criatura.getPosY() == y) {
                return criatura;
            }
        }
        return null;
    }

    public Heaven getSafeHeavenAtPosition(int x, int y) {
        for (Heaven heaven : safeZone) {
            if (heaven.getPosicao().getX() == x && heaven.getPosicao().getY() == y) {
                return heaven;
            }
        }
        return null;
    }

    public void addCriaturaToSafeHeaven(Criatura criatura, Heaven safeHeaven) {
        safeHeaven.addVivos(criatura);
        System.out.println("Criatura " + criatura.getId() + " adicionada ao Safe Heaven!");
    }

    public List<Integer> getIdsSafeHeaven() {
        List<Integer> ids = new ArrayList<>();
        for (Heaven heaven : safeZone) {
            for (Criatura criatura : heaven.safeVivos()) {
                ids.add(criatura.getId());
            }
        }
        return ids;
    }

    public boolean gameOver() {
        if (getQuantidadeDejogadasSemAtaques() == 8) {
            return true;
        }

        int pTeam = 0;
        boolean teamPlus = false;

        for (Criatura criatura : getCriaturas()) {
            if (criatura != null) {
                int teamAtual = criatura.getTeam();
                if (pTeam == 0) {
                    pTeam = teamAtual;
                } else if (pTeam != teamAtual) {
                    teamPlus = true;
                    break;
                }
            }
        }

        return !teamPlus;
    }


    public void removerItem(Item item) {
        items.remove(item);
    }

    public void removerCriatura(Criatura criatura) {
        criaturas.remove(criatura);
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}

