package pt.ulusofona.lp2.thenightofthelivingdeisi;

import pt.ulusofona.lp2.thenightofthelivingdeisi.items.Item;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class GameManager {
    private File file;

    private Tabuleiro tabuleiro = new Tabuleiro();

    public GameManager(File file) {
        this.file = file;
    }

    public GameManager() {
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void loadGame(File file) throws InvalidFileException, FileNotFoundException {
        LerFicheiro ficheiro = new LerFicheiro(file);
        tabuleiro.setTurno(0);
        ficheiro.parseFiles();
        this.tabuleiro = ficheiro.getTabuleiro();
    }


    public int[] getWorldSize() {
        return tabuleiro.getSize();
    }

    public int getInitialTeamId() {
        return tabuleiro.getJogada();
    }

    public int getCurrentTeamId() {
        System.out.println(tabuleiro.getCurrentTeamId());
        destransformarETransformar(tabuleiro);
        return tabuleiro.getCurrentTeamId();
    }

    public boolean isDay() {
        return tabuleiro.isDay();
    }

    public String getSquareInfo(int x, int y) {
        return tabuleiro.getSquareInfo(x, y);
    }

    private void destransformarETransformar(Tabuleiro tabuleiro) {
        for (Criatura criatura : tabuleiro.getCriaturas()) {
            if (criatura.getTipo() == 5) {
                criatura.transformarLobo(criatura, tabuleiro);
            }
        }
    }

    public String[] getCreatureInfo(int id) {
        for (Heaven heaven : tabuleiro.getSafeZone()) {
            for (Criatura criatura : heaven.safeVivos()) {
                if (criatura.getId() == id) {
                    return criatura.getCreatureInfo(id);
                }
            }
        }
        for (Criatura criatura : tabuleiro.getCriaturas()) {
            if (criatura.getId() == id) {
                return criatura.getCreatureInfo(id);
            }
        }
        return null;
    }

    public String getCreatureInfoAsString(int id) {
        for (Heaven heaven : tabuleiro.getSafeZone()) {
            for (Criatura criatura : heaven.safeVivos()) {
                if (criatura.getId() == id) {
                    return criatura.getCreatureInfoAsString(id);
                }
            }
        }
        for (Criatura criatura : tabuleiro.getCriaturas()) {
            if (criatura.getId() == id) {
                return criatura.getCreatureInfoAsString(id);
            }
        }
        return null;
    }

    public String[] getEquipmentInfo(int id) {
        for (Item item : tabuleiro.getItems()) {
            if (item.getId() == id) {
                return item.getEquipmentInfo(id);
            }
        }
        return null;
    }

    public String getEquipmentInfoAsString(int id) {
        for (Item item : tabuleiro.getItems()) {
            if (item.getId() == id) {
                return item.toString();
            }
        }
        return null;
    }

    public boolean hasEquipment(int creatureId, int equipmentTypeId) {
        for (Criatura criatura : tabuleiro.getCriaturas()) {
            if (criatura.getId() == creatureId) {
                return criatura.hasEquipment(creatureId, equipmentTypeId);
            }
        }
        return false;
    }


    public boolean move(int xO, int yO, int xD, int yD) {
        Criatura criatura = tabuleiro.getCriaturaNaPosicao(xO, yO);

        System.out.println("Move attempt: from (" + xO + "," + yO + ") to (" + xD + "," + yD + ")");

        if (criatura == null || !criatura.podeMover(new Posicao(xO, yO), new Posicao(xD, yD), tabuleiro)) {
            return false;
        }

        criatura.executarAcaoAoMover(xO, yO, xD, yD, tabuleiro, criatura);
        tabuleiro.getCurrentTeamId();
        tabuleiro.setTurno(tabuleiro.getTurno() + 1);
        tabuleiro.addQuantidadeDejogadasSemAtaques();
        System.out.println("Turno:" + tabuleiro.getTurno());
        return true;
    }


    public boolean gameIsOver() {
        return tabuleiro.gameOver();
    }

    public ArrayList<String> getSurvivors() {
        ArrayList<String> survivors = new ArrayList<>();
        ArrayList<Criatura> vivos = tabuleiro.getCriaturas();

        survivors.add("Nr. de turnos terminados:");
        survivors.add(tabuleiro.getTurno() + "");
        survivors.add("");
        survivors.add("OS VIVOS");

        for (Heaven safeHaven : tabuleiro.getSafeZone()) {
            vivos.addAll(safeHaven.safeVivos());
        }
        vivos.sort(Comparator.comparing(Criatura::getId));

        for (Criatura criatura : tabuleiro.getCriaturas()) {
            if (criatura.getSquareInfo().contains("H:")) {
                survivors.add(criatura.isString());
            }
        }

        survivors.add("");
        survivors.add("OS OUTROS");
        for (Criatura criatura : tabuleiro.getCriaturas()) {
            if (criatura.getSquareInfo().contains("Z:")) {
                survivors.add(criatura.isString());
            }
        }

        survivors.add("-----");

        return survivors;
    }


    public JPanel getCreditsPanel() {
        /*Creditos creditos = new Creditos();
        return creditos.getCreditsPanel();*/
        return new JPanel();
    }

    public HashMap<String, String> customizeBoard() {
        return new HashMap<String, String>();
    }

    public void saveGame(File file) throws IOException {
        if (file == null || tabuleiro == null) {
            throw new IllegalArgumentException("Arquivo ou tabuleiro inválido.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(tabuleiro.getX() + " " + tabuleiro.getY());
            writer.newLine();

            writer.write(String.valueOf(tabuleiro.getJogada()));
            writer.newLine();

            writer.write(String.valueOf(tabuleiro.getqEntidades()));
            writer.newLine();

            for (Criatura criatura : tabuleiro.getCriaturas()) {
                writer.write(criatura.getId() + " : " +
                        criatura.getTeam() + " : " +
                        criatura.getTipo() + " : " +
                        criatura.getNome() + " : " +
                        criatura.getPosX() + " : " +
                        criatura.getPosY());
                writer.newLine();
            }

            writer.write(String.valueOf(tabuleiro.getqItems()));
            writer.newLine();

            for (Item item : tabuleiro.getItems()) {
                writer.write(item.getId() + " : " +
                        item.getTipo() + " : " +
                        item.getCoords().getX() + " : " +
                        item.getCoords().getY());
                writer.newLine();
            }

            writer.write(String.valueOf(tabuleiro.getqPortas()));
            writer.newLine();

            for (Heaven porta : tabuleiro.getSafeZone()) {
                writer.write(porta.getPosicao().getX() + " : " + porta.getPosicao().getY());
                writer.newLine();
            }
        }
    }

    public List<Integer> getIdsInSafeHaven() {
        return tabuleiro.getIdsSafeHeaven();
    }
}