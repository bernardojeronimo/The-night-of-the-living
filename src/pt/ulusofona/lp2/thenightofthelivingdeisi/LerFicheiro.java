package pt.ulusofona.lp2.thenightofthelivingdeisi;

import pt.ulusofona.lp2.thenightofthelivingdeisi.factorys.EntityFactory;
import pt.ulusofona.lp2.thenightofthelivingdeisi.factorys.ItemsFactory;
import pt.ulusofona.lp2.thenightofthelivingdeisi.items.Item;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class LerFicheiro {

    private File file;
    private Tabuleiro tabuleiro;

    public LerFicheiro(File file) {
        this.file = file;
    }

    public LerFicheiro() {
    }

    public void parseFiles() throws InvalidFileException, FileNotFoundException {
        BufferedReader reader;
        try {
            if (file == null) {
                throw new FileNotFoundException("Arquivo não fornecido");
            }
            reader = new BufferedReader(new FileReader(file));
            String line;
            int lineNumber = 0;
            int count = 0;

            // Flags para controle da leitura
            boolean lendoEntidades = true;
            boolean lendoItems = false;
            boolean lendoPortas = false;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] parts;

                // Linha 1: Dimensões do tabuleiro
                if (lineNumber == 1) {
                    parts = line.split(" ");
                    if (parts.length != 2) {
                        throw new InvalidFileException("Formato inválido na linha", lineNumber);
                    }
                    tabuleiro = new Tabuleiro(parseInt(parts[0]), parseInt(parts[1]));
                    continue;
                }

                // Linha 2: Jogada inicial
                if (lineNumber == 2) {
                    if (line.isEmpty()) {
                        throw new InvalidFileException("Linha está vazia", lineNumber);
                    }
                    tabuleiro.setJogada(parseInt(line));
                    continue;
                }

                // Linha 3: Quantidade de entidades
                if (lineNumber == 3) {
                    if (line.isEmpty()) {
                        throw new InvalidFileException("Linha está vazia", lineNumber);
                    }
                    tabuleiro.setqEntidades(parseInt(line));
                    continue;
                }

                // Processar entidades
                if (lendoEntidades && count < tabuleiro.getqEntidades()) {
                    parts = line.split(" : ");
                    if (parts.length != 6) {
                        throw new InvalidFileException("Formato inválido para entidade", lineNumber);
                    }
                    addCriaturas(parseInt(parts[0]), parseInt(parts[1]),
                            parseInt(parts[2]), parts[3], new Posicao(parseInt(parts[4]), parseInt(parts[5])), lineNumber);
                    count++;
                    continue;
                }

                // Quando todas as entidades forem lidas, começa a leitura dos itens
                if (lendoEntidades && count == tabuleiro.getqEntidades()) {
                    lendoEntidades = false;
                    lendoItems = true;
                    count = 0;
                    if (line.isEmpty()) {
                        throw new InvalidFileException("Linha de quantidade de itens está vazia", lineNumber);
                    }
                    tabuleiro.setqItems(parseInt(line));
                    continue;
                }

                // Processar itens
                if (lendoItems && count < tabuleiro.getqItems()) {
                    parts = line.split(" : ");
                    if (parts.length != 4) {
                        throw new InvalidFileException("Formato inválido para item", lineNumber);
                    }
                    addItems(parseInt(parts[0]), parseInt(parts[1]), new Posicao(parseInt(parts[2]), parseInt(parts[3])), lineNumber);
                    count++;
                    continue;
                }

                // Quando todos os itens forem lidos, começa a leitura das portas
                if (lendoItems && count == tabuleiro.getqItems()) {
                    lendoItems = false;
                    lendoPortas = true;
                    count = 0;
                    if (line.isEmpty()) {
                        throw new InvalidFileException("Linha de quantidade de portas está vazia", lineNumber);
                    }
                    tabuleiro.setqPortas(parseInt(line));
                    continue;
                }

                // Processar portas
                if (lendoPortas && count < tabuleiro.getqPortas()) {
                    parts = line.split(" : ");
                    if (parts.length != 2) {
                        throw new InvalidFileException("Formato inválido para portas", lineNumber);
                    }
                    int posX = parseInt(parts[0]);
                    int posY = parseInt(parts[1]);
                    tabuleiro.addPortas(new Heaven(new Posicao(posX, posY)));
                    count++;
                }
            }
            reader.close();

            if (this.tabuleiro == null) {
                throw new InvalidFileException("Tabuleiro não foi inicializado", lineNumber);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo não encontrado.");
            throw e;
        } catch (IOException e) {
            System.out.println("Erro: Problema ao ler o arquivo.");
        }
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public int getJogada() {
        return tabuleiro.getJogada();
    }

    public ArrayList<Criatura> getCriaturas() {
        return tabuleiro.getCriaturas();
    }

    public void addCriaturas(int id, int equipa, int tipo, String nome, Posicao posicao, int lineNumber) throws InvalidFileException {
        Criatura criatura = EntityFactory.createEntity(id, equipa, tipo, nome, posicao);
        if (criatura != null) {
            tabuleiro.addCriaturas(criatura);
        } else {
            throw new InvalidFileException("Formato inválido para criatura", lineNumber);
        }
    }

    public void addItems(int id, int tipo, Posicao posicao, int lineNumber) throws InvalidFileException {
        Item item = ItemsFactory.createItem(id, tipo, posicao);
        if (item != null) {
            tabuleiro.addItem(item);
        } else {
            throw new InvalidFileException("Formato inválido para item", lineNumber);
        }
    }
}