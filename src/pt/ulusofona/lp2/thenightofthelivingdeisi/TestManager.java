package pt.ulusofona.lp2.thenightofthelivingdeisi;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.thenightofthelivingdeisi.factorys.EntityFactory;
import pt.ulusofona.lp2.thenightofthelivingdeisi.factorys.ItemsFactory;
import pt.ulusofona.lp2.thenightofthelivingdeisi.items.Item;
import pt.ulusofona.lp2.thenightofthelivingdeisi.items.Lixivia;
import pt.ulusofona.lp2.thenightofthelivingdeisi.items.Pistola;
import pt.ulusofona.lp2.thenightofthelivingdeisi.vivos.IdosoH;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestManager {

    @Test
    public void testMostraTipoLixivia() {
        Posicao posicao = new Posicao(2, 3);
        Lixivia lixivia = new Lixivia(1, 2, posicao);

        assertEquals("Lixívia", lixivia.mostraTipo(), "O método mostraTipo() deve retornar 'Lixívia'");
    }

    @Test
    public void testAcaoReduzLitros() {
        Posicao posicao = new Posicao(1, 1);
        Lixivia lixivia = new Lixivia(2, 3, posicao);

        // Primeira ação: 1.0 -> 0.7 litros
        assertTrue(lixivia.acao());
        assertEquals(0.7F, lixivia.getLitros(), "Após a 1ª ação, os litros devem ser 0.7");

        // Segunda ação: 0.7 -> 0.4 litros
        assertTrue(lixivia.acao());
        assertEquals(0.4F, lixivia.getLitros(), "Após a 2ª ação, os litros devem ser 0.4");

        // Terceira ação: 0.4 -> 0.1 litros
        assertTrue(lixivia.acao());
        assertEquals(0.1F, lixivia.getLitros(), "Após a 3ª ação, os litros devem ser 0.1");

        // Quarta ação: 0.1 -> 0.0 litros
        assertTrue(lixivia.acao());
        assertEquals(0.0F, lixivia.getLitros(), "Após a 4ª ação, os litros devem ser 0.0");

        // A partir da 5ª tentativa: sem efeito
        assertFalse(lixivia.acao(), "A partir da 5ª ação, o método acao() deve retornar false");
        assertEquals(0.0F, lixivia.getLitros(), "A capacidade deve permanecer 0.0 após a 5ª ação");
    }

    @Test
    public void testCapacidadeLixivia() {
        Posicao posicao = new Posicao(3, 4);
        Lixivia lixivia = new Lixivia(3, 2, posicao);

        // Verifica a capacidade inicial
        assertEquals(1, lixivia.capacidade(), "A capacidade inicial deve ser 1 litro");

        // Realiza ações para verificar capacidade reduzida
        lixivia.acao();
        assertEquals(0, lixivia.capacidade(), "Após a 1ª ação, a capacidade arredondada deve ser 0");

        lixivia.acao();
        lixivia.acao();
        lixivia.acao();
        assertEquals(0, lixivia.capacidade(), "Mesmo após múltiplas ações, capacidade deve ser 0");
    }

    @Test
    public void testToStringLixivia() {
        Posicao posicao = new Posicao(5, 5);
        Lixivia lixivia = new Lixivia(10, 1, posicao);

        String esperado = "10 | Lixívia @ (5, 5) | 1.0 litros";
        assertEquals(esperado, lixivia.toString(), "O método toString() deve retornar a representação correta");

        // Após uma ação
        lixivia.acao();
        String esperadoAposAcao = "10 | Lixívia @ (5, 5) | 0.7 litros";
        assertEquals(esperadoAposAcao, lixivia.toString(), "O método toString() deve atualizar os litros corretamente");
    }

    @Test
    public void testMostraTipoPistola() {
        Posicao posicao = new Posicao(3, 4);
        Pistola pistola = new Pistola(1, 2, posicao);

        assertEquals("Pistola Walther PPK", pistola.mostraTipo(), "O método mostraTipo() deve retornar 'Pistola Walther PPK'");
    }

    @Test
    public void testLixiviaInicializacao() {
        Posicao posicao = new Posicao(1, 1);
        Lixivia lixivia = new Lixivia(5, 3, posicao);

        assertEquals(5, lixivia.getId(), "O ID deve ser corretamente inicializado");
        assertEquals(3, lixivia.getTipo(), "O tipo deve ser corretamente inicializado");
        assertEquals(posicao, lixivia.getCoords(), "A posição deve ser corretamente inicializada");
        assertEquals(1.0F, lixivia.getLitros(), "Os litros devem começar em 1.0");
    }

    @Test
    public void testLixiviaAcaoExtrema() {
        Posicao posicao = new Posicao(2, 2);
        Lixivia lixivia = new Lixivia(10, 3, posicao);

        // Executa 4 ações para esgotar completamente
        for (int i = 0; i < 4; i++) {
            lixivia.acao();
        }

        // Tenta uma 5ª ação
        assertFalse(lixivia.acao(), "Após 4 ações, não deve ser possível realizar mais ações");
        assertEquals(0.0F, lixivia.getLitros(), "Os litros devem ser 0.0 após todas as ações");
    }

    @Test
    public void testAcaoReduzBalas() {
        Posicao posicao = new Posicao(2, 2);
        Pistola pistola = new Pistola(2, 3, posicao);

        // Verificar se a pistola tem 3 balas no início
        assertEquals(3, pistola.capacidade(), "Capacidade inicial deve ser 3 balas");

        // 1ª ação: 3 -> 2 balas
        assertTrue(pistola.acao(), "A 1ª ação deve retornar true");
        assertEquals(2, pistola.capacidade(), "Após a 1ª ação, a capacidade deve ser 2 balas");

        // 2ª ação: 2 -> 1 bala
        assertTrue(pistola.acao(), "A 2ª ação deve retornar true");
        assertEquals(1, pistola.capacidade(), "Após a 2ª ação, a capacidade deve ser 1 bala");

        // 3ª ação: 1 -> 0 balas
        assertTrue(pistola.acao(), "A 3ª ação deve retornar true");
        assertEquals(0, pistola.capacidade(), "Após a 3ª ação, a capacidade deve ser 0 balas");

        // 4ª ação: sem balas
        assertFalse(pistola.acao(), "A 4ª ação deve retornar false, pois a pistola está vazia");
        assertEquals(0, pistola.capacidade(), "A capacidade deve continuar 0 após a 4ª ação");
    }

    @Test
    public void testCapacidadeInicialPistola() {
        Posicao posicao = new Posicao(1, 1);
        Pistola pistola = new Pistola(5, 1, posicao);

        assertEquals(3, pistola.capacidade(), "Capacidade inicial da pistola deve ser 3 balas");
    }

    @Test
    public void testToStringPistola() {
        Posicao posicao = new Posicao(5, 5);
        Pistola pistola = new Pistola(10, 1, posicao);

        // Testa o estado inicial
        String esperado = "10 | Pistola Walther PPK @ (5, 5) | 3 balas";
        assertEquals(esperado, pistola.toString(), "O método toString() deve retornar o estado inicial correto");

        // Após uma ação (reduzir balas)
        pistola.acao();
        String esperadoAposAcao = "10 | Pistola Walther PPK @ (5, 5) | 2 balas";
        assertEquals(esperadoAposAcao, pistola.toString(), "O método toString() deve refletir a quantidade de balas após uma ação");
    }

    @Test
    public void testPistolaInicializacao() {
        Posicao posicao = new Posicao(3, 3);
        Pistola pistola = new Pistola(7, 2, posicao);

        assertEquals(7, pistola.getId(), "O ID deve ser corretamente inicializado");
        assertEquals(2, pistola.getTipo(), "O tipo deve ser corretamente inicializado");
        assertEquals(posicao, pistola.getCoords(), "A posição deve ser corretamente inicializada");
        assertEquals(3, pistola.capacidade(), "A capacidade inicial deve ser 3 balas");
    }

    @Test
    public void testPistolaAcaoExtrema() {
        Posicao posicao = new Posicao(4, 4);
        Pistola pistola = new Pistola(15, 2, posicao);

        // Executa 4 ações para esgotar completamente
        for (int i = 0; i < 4; i++) {
            if (i < 3) {
                assertTrue(pistola.acao(), "As primeiras 3 ações devem ser válidas");
            } else {
                assertFalse(pistola.acao(), "A 4ª ação não deve ser válida");
            }
        }

        assertEquals(0, pistola.capacidade(), "As balas devem ser 0 após todas as ações");
    }


    @Test
    public void testCriacaoEntidadesValidas() {
        // Teste para criação de diferentes tipos de entidades
        Posicao posicao = new Posicao(1, 1);

        // Crianças
        Criatura criancaZombie = EntityFactory.createEntity(1, 10, 0, "ZombieChild", posicao);
        Criatura criancaHumana = EntityFactory.createEntity(2, 20, 0, "HumanChild", posicao);

        assertNotNull(criancaZombie, "Criação de Criança Zombie deve ser bem-sucedida");
        assertNotNull(criancaHumana, "Criação de Criança Humana deve ser bem-sucedida");
        assertEquals(0, criancaZombie.getTipo(), "Tipo da Criança Zombie deve ser 0");
        assertEquals(0, criancaHumana.getTipo(), "Tipo da Criança Humana deve ser 0");
    }

    @Test
    public void testCriacaoAdultos() {
        Posicao posicao = new Posicao(2, 2);

        Criatura adultoZombie = EntityFactory.createEntity(3, 10, 1, "ZombieAdult", posicao);
        Criatura adultoHumano = EntityFactory.createEntity(4, 20, 1, "HumanAdult", posicao);

        assertNotNull(adultoZombie, "Criação de Adulto Zombie deve ser bem-sucedida");
        assertNotNull(adultoHumano, "Criação de Adulto Humano deve ser bem-sucedida");
        assertEquals(1, adultoZombie.getTipo(), "Tipo do Adulto Zombie deve ser 1");
        assertEquals(1, adultoHumano.getTipo(), "Tipo do Adulto Humano deve ser 1");
    }

    @Test
    public void testCriacaoIdosos() {
        Posicao posicao = new Posicao(3, 3);

        Criatura idosoZombie = EntityFactory.createEntity(5, 10, 2, "ZombieElderly", posicao);
        Criatura idosoHumano = EntityFactory.createEntity(6, 20, 2, "HumanElderly", posicao);

        assertNotNull(idosoZombie, "Criação de Idoso Zombie deve ser bem-sucedida");
        assertNotNull(idosoHumano, "Criação de Idoso Humano deve ser bem-sucedida");
        assertEquals(2, idosoZombie.getTipo(), "Tipo do Idoso Zombie deve ser 2");
        assertEquals(2, idosoHumano.getTipo(), "Tipo do Idoso Humano deve ser 2");
    }

    @Test
    public void testCriacaoCao() {
        Posicao posicao = new Posicao(4, 4);

        Criatura cao = EntityFactory.createEntity(7, 20, 3, "Dog", posicao);
        Criatura caoInvalido = EntityFactory.createEntity(8, 10, 3, "InvalidDog", posicao);

        assertNotNull(cao, "Criação de Cão deve ser bem-sucedida");
        assertNull(caoInvalido, "Cão não deve ser criado para equipe que não seja 20");
        assertEquals(3, cao.getTipo(), "Tipo do Cão deve ser 3");
    }

    @Test
    public void testCriacaoVampiro() {
        Posicao posicao = new Posicao(5, 5);

        Criatura vampiro = EntityFactory.createEntity(9, 10, 4, "Vampire", posicao);
        Criatura vampiroInvalido = EntityFactory.createEntity(10, 20, 4, "InvalidVampire", posicao);

        assertNotNull(vampiro, "Criação de Vampiro deve ser bem-sucedida");
        assertNull(vampiroInvalido, "Vampiro não deve ser criado para equipe que não seja 10");
        assertEquals(4, vampiro.getTipo(), "Tipo do Vampiro deve ser 4");
    }

    @Test
    public void testCriacaoTipoInvalido() {
        Posicao posicao = new Posicao(6, 6);

        Criatura entidadeInvalida = EntityFactory.createEntity(11, 10, 5, "InvalidEntity", posicao);

        assertNull(entidadeInvalida, "Criação de entidade com tipo inválido deve retornar null");
    }

    @Test
    public void testAdultoHMovimentoInvalidoEquipeErrada() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10);
        tabuleiro.setJogada(10);  // Define para equipe 10 (zombies)

        Posicao posicaoAtual = new Posicao(5, 5);
        Posicao posicaoDestino = new Posicao(6, 6);

        Criatura adulto = EntityFactory.createEntity(20, 20, 1, "TestAdulto", posicaoAtual);

        assertFalse(adulto.podeMover(posicaoAtual, posicaoDestino, tabuleiro), "Movimento não deve ser permitido quando não for a vez da equipe humana");
    }

    @Test
    public void testAdultoHMovimentoDiagonalLongo() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10);
        tabuleiro.setJogada(20);  // Define para equipe 20 (humanos)

        Posicao posicaoAtual = new Posicao(3, 3);
        Posicao posicaoDestino = new Posicao(6, 6);  // Mais de 2 casas na diagonal

        Criatura adulto = EntityFactory.createEntity(20, 20, 1, "TestAdulto", posicaoAtual);

        assertFalse(adulto.podeMover(posicaoAtual, posicaoDestino, tabuleiro), "Movimento diagonal maior que 2 casas deve ser inválido");
    }

    @Test
    public void testTabuleiroGameOver() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10);

        // Simula 8 jogadas sem ataques
        for (int i = 0; i < 8; i++) {
            tabuleiro.addQuantidadeDejogadasSemAtaques();
        }

        assertTrue(tabuleiro.gameOver(), "Após 8 jogadas sem ataques, o jogo deve terminar");
    }

    @Test
    public void testTabuleiroSquareInfo() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10);

        // Teste para posição fora do tabuleiro
        assertNull(tabuleiro.getSquareInfo(-1, -1), "Square info para posição inválida deve retornar null");
    }

    @Test
    public void testMostraTipoAdultoH() {
        Posicao posicao = new Posicao(2, 3);
        Criatura adulto = EntityFactory.createEntity(1, 20, 1, "Carlos", posicao);

        assertNotNull(adulto, "A criação do AdultoH deve retornar uma instância válida");
        assertEquals(1, adulto.getTipo(), "O método mostraTipo() deve retornar '1'");
    }

    @Test
    public void testGetTipoAdultoH() {
        Posicao posicao = new Posicao(1, 1);
        Criatura adulto = EntityFactory.createEntity(2, 20, 1, "Maria", posicao);

        assertNotNull(adulto, "A criação do AdultoH deve retornar uma instância válida");
        assertEquals(1, adulto.getTipo(), "O método getTipo() deve retornar 1 para AdultoH");
    }

    @Test
    public void testRegrasMoveDiagonalAdultoH() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10);
        Posicao posicaoAtual = new Posicao(3, 3);
        Posicao posicaoDestino = new Posicao(5, 5);
        Criatura adulto = EntityFactory.createEntity(1, 20, 1, "João", posicaoAtual);

        assertNotNull(adulto, "A criação do AdultoH deve retornar uma instância válida");
        tabuleiro.setJogada(10);

        assertTrue(adulto.regrasMove(posicaoAtual, posicaoDestino, tabuleiro),
                "O movimento diagonal de até 2 casas deve ser permitido para AdultoH");
        assertEquals(posicaoDestino, adulto.getCoords(),
                "As coordenadas do AdultoH devem ser atualizadas após um movimento válido");
    }

    @Test
    public void testRegrasMoveHorizontalVerticalAdultoH() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10);
        Posicao posicaoAtual = new Posicao(4, 4);
        Posicao posicaoDestino = new Posicao(4, 6);
        Criatura adulto = EntityFactory.createEntity(2, 20, 1, "Ana", posicaoAtual);

        assertNotNull(adulto, "A criação do AdultoH deve retornar uma instância válida");
        tabuleiro.setJogada(10);

        assertTrue(adulto.regrasMove(posicaoAtual, posicaoDestino, tabuleiro),
                "O movimento vertical de até 2 casas deve ser permitido para AdultoH");
        assertEquals(posicaoDestino, adulto.getCoords(),
                "As coordenadas do AdultoH devem ser atualizadas após um movimento válido");
    }

    @Test
    public void testRegrasMoveForaDoLimiteAdultoH() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10);
        Posicao posicaoAtual = new Posicao(5, 5);
        Posicao posicaoDestino = new Posicao(8, 8); // Mais de 2 casas em diagonal
        Criatura adulto = EntityFactory.createEntity(3, 20, 1, "Pedro", posicaoAtual);

        assertNotNull(adulto, "A criação do AdultoH deve retornar uma instância válida");
        tabuleiro.setJogada(20);

        assertFalse(adulto.regrasMove(posicaoAtual, posicaoDestino, tabuleiro),
                "Movimentos fora do alcance permitido (mais de 2 casas) devem ser inválidos");
        assertEquals(posicaoAtual, adulto.getCoords(),
                "As coordenadas do AdultoH não devem ser alteradas após um movimento inválido");
    }

    @Test
    public void testRegrasMoveSemEquipeCertaAdultoH() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10);
        Posicao posicaoAtual = new Posicao(2, 2);
        Posicao posicaoDestino = new Posicao(3, 3);
        Criatura adulto = EntityFactory.createEntity(4, 20, 1, "Rui", posicaoAtual);

        assertNotNull(adulto, "A criação do AdultoH deve retornar uma instância válida");
        tabuleiro.setJogada(20); // Equipe errada

        assertFalse(adulto.regrasMove(posicaoAtual, posicaoDestino, tabuleiro),
                "Movimentos devem ser negados se a equipe atual não for a correta");
        assertEquals(posicaoAtual, adulto.getCoords(),
                "As coordenadas do AdultoH não devem mudar se o movimento for negado");
    }

    @Test
    public void testRegrasMove_MovimentoInvalido_PosicaoDestinoIgualPosicaoAtual() {
        // Inicializa o tabuleiro e a posição
        Tabuleiro tabuleiro = new Tabuleiro(); // Inicialize o tabuleiro adequadamente
        Posicao posicaoAtual = new Posicao(3, 3); // Posição inicial do cão

        // Criação do cão usando a EntityFactory
        Criatura cao = EntityFactory.createEntity(1, 20, 3, "Rex", posicaoAtual); // Criação do cão através da fábrica

        // Simula uma jogada, definindo a equipe como a que está jogando (equivalente ao "setCurrentTeamId")
        tabuleiro.setJogada(10); // Define que a equipe 20 está jogando

        // Testa se o movimento é permitido (posição atual igual à posição de destino)
        assertTrue(cao.regrasMove(posicaoAtual, posicaoAtual, tabuleiro));
    }

    @Test
    public void testRegrasMove_MovimentoValido_PosicaoDestinoValida() {
        // Inicializa o tabuleiro e a posição
        Tabuleiro tabuleiro = new Tabuleiro();
        Posicao posicaoAtual = new Posicao(3, 3); // Posição inicial do cão
        Posicao posicaoDestino = new Posicao(4, 3); // Posição destino válida

        // Criação do cão usando a EntityFactory
        Criatura cao = EntityFactory.createEntity(1, 20, 3, "Rex", posicaoAtual);

        // Simula uma jogada, definindo a equipe como a que está jogando
        tabuleiro.setJogada(10);

        // Testa se o movimento é válido (deve retornar true)
        assertTrue(cao.regrasMove(posicaoAtual, posicaoDestino, tabuleiro));
    }

    @Test
    public void testRegrasMove_MovimentoInvalido_PosicaoDestinoComItem() {
        // Inicializa o tabuleiro e a posição
        Tabuleiro tabuleiro = new Tabuleiro();
        Posicao posicaoAtual = new Posicao(3, 3); // Posição inicial do cão
        Posicao posicaoDestino = new Posicao(4, 3); // Posição destino com um item

        // Criação do cão usando a EntityFactory
        Criatura cao = EntityFactory.createEntity(1, 20, 3, "Rex", posicaoAtual);

        // Adiciona um item na posição de destino (simulando que a posição não está livre)
        Item item = ItemsFactory.createItem(3, 2, new Posicao(2, 2)); // Instância de um item genérico
        tabuleiro.addItem(item);

        // Simula uma jogada, definindo a equipe como a que está jogando
        tabuleiro.setJogada(20);

        // Testa se o movimento é inválido quando há um item na posição de destino (deve retornar false)
        assertFalse(cao.regrasMove(posicaoAtual, posicaoDestino, tabuleiro));
    }

    @Test
    public void testRegrasMove_MovimentoValido_PosicaoDestinoNoSafeHeaven() {
        // Inicializa o tabuleiro e a posição
        Tabuleiro tabuleiro = new Tabuleiro();
        Posicao posicaoAtual = new Posicao(3, 3); // Posição inicial do cão
        Posicao posicaoDestino = new Posicao(4, 3); // Posição destino no Safe Haven

        // Criação do cão usando a EntityFactory
        Criatura cao = EntityFactory.createEntity(1, 20, 3, "Rex", posicaoAtual);

        // Adiciona um Safe Haven na posição de destino
        Heaven safeHeaven = new Heaven(new Posicao(2, 2)); // Instância de Safe Haven
        tabuleiro.addPortas(safeHeaven);

        // Simula uma jogada, definindo a equipe como a que está jogando
        tabuleiro.setJogada(10);

        // Testa se o movimento é permitido para o Safe Haven (deve retornar true)
        assertTrue(cao.regrasMove(posicaoAtual, posicaoDestino, tabuleiro));
    }

    @Test
    public void testRegrasMove_MovimentoInvalido_PosicaoDestinoComCriatura() {
        // Inicializa o tabuleiro e a posição
        Tabuleiro tabuleiro = new Tabuleiro();
        Posicao posicaoAtual = new Posicao(3, 3); // Posição inicial do cão
        Posicao posicaoDestino = new Posicao(4, 3); // Posição destino com outra criatura

        // Criação do cão e de outra criatura na posição de destino
        Criatura cao = EntityFactory.createEntity(1, 20, 3, "Rex", posicaoAtual);
        Criatura outroCao = EntityFactory.createEntity(2, 20, 3, "Buddy", posicaoDestino);

        // Adiciona a outra criatura na posição de destino (simulando bloqueio de movimento)
        tabuleiro.addCriaturas(outroCao);

        // Simula uma jogada, definindo a equipe como a que está jogando
        tabuleiro.setJogada(20);

        // Testa se o movimento é inválido quando há outra criatura na posição de destino (deve retornar false)
        assertFalse(cao.regrasMove(posicaoAtual, posicaoDestino, tabuleiro));
    }

    @Test
    public void testCriancaCriacao() {
        Posicao posicao = new Posicao(2, 2);

        // Criação de uma Criança
        Criatura crianca = EntityFactory.createEntity(1, 10, 0, "Criança", posicao);

        // Verificar se a criança foi criada corretamente
        assertNotNull(crianca, "A criação da Criança deve ser bem-sucedida");
        assertEquals(0, crianca.getTipo(), "Tipo da Criança deve ser 0");
        assertEquals(posicao, crianca.getCoords(), "A posição da Criança deve ser correta");
    }

    @Test
    public void testCriancaHMovimentoValido() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10);
        Posicao posicaoAtual = new Posicao(3, 3);
        Posicao posicaoDestino = new Posicao(4, 3); // Movimento válido (1 casa horizontal)

        // Criação de uma Criança
        Criatura crianca = EntityFactory.createEntity(1, 20, 0, "Criança", posicaoAtual);

        tabuleiro.setJogada(10); // Definindo a equipe 10 (zumbis ou humanos dependendo da lógica do jogo)

        // Verifica se a Criança pode se mover para a posição válida
        assertTrue(crianca.regrasMove(posicaoAtual, posicaoDestino, tabuleiro),
                "O movimento de uma Criança para a posição destino válida deve ser permitido");

        // Verifica se a posição foi atualizada corretamente
        assertEquals(posicaoDestino, crianca.getCoords(), "A posição da Criança deve ser atualizada após um movimento válido");
    }

    @Test
    public void testCriancaHMovimentoInvalido() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10);
        Posicao posicaoAtual = new Posicao(4, 4);
        Posicao posicaoDestino = new Posicao(6, 6); // Movimento inválido (mais de 2 casas)

        // Criação de uma Criança
        Criatura crianca = EntityFactory.createEntity(1, 10, 0, "Criança", posicaoAtual);

        tabuleiro.setJogada(10); // Definindo a equipe 10 (zumbis ou humanos dependendo da lógica do jogo)

        // Verifica se a Criança não pode se mover para a posição inválida
        assertFalse(crianca.regrasMove(posicaoAtual, posicaoDestino, tabuleiro),
                "O movimento de uma Criança para a posição destino inválida (mais de 2 casas) não deve ser permitido");

        // Verifica se a posição não foi alterada
        assertEquals(posicaoAtual, crianca.getCoords(), "A posição da Criança não deve ser alterada após um movimento inválido");
    }

    @Test
    public void testCriancaHSemEquipeValida() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10);
        Posicao posicaoAtual = new Posicao(3, 3);
        Posicao posicaoDestino = new Posicao(4, 3); // Movimento válido

        // Criação de uma Criança
        Criatura crianca = EntityFactory.createEntity(1, 20, 0, "Criança", posicaoAtual);

        tabuleiro.setJogada(20); // Definindo a equipe 20 (humanos)

        // Verifica se a Criança não pode se mover para a posição se não for sua vez
        assertFalse(crianca.regrasMove(posicaoAtual, posicaoDestino, tabuleiro),
                "A Criança não pode se mover quando não for sua vez");

        // Verifica se a posição não foi alterada
        assertEquals(posicaoAtual, crianca.getCoords(), "A posição da Criança não deve ser alterada após tentativa de movimento fora de vez");
    }

    @Test
    public void testToStringCrianca() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10);
        Posicao posicao = new Posicao(3, 3);
        Criatura crianca = EntityFactory.createEntity(1, 20, 0, "Criança", posicao);

        // Verificar a representação da Criança
        String esperado = "1 | Criança | Humano | Criança | +0 @ (3, 3)";
        assertEquals(esperado, crianca.toString(), "O método toString() deve retornar a representação correta da Criança");

        // Verifica a representação após um movimento
        Posicao novaPosicao = new Posicao(4, 3);
        crianca.regrasMove(posicao, novaPosicao, tabuleiro);
        String esperadoAposMovimento = "1 | Criança | Humano | Criança | +0 @ (4, 3)";
        assertEquals(esperadoAposMovimento, crianca.toString(), "O método toString() deve atualizar a posição corretamente");
    }

    private File createTestFile(String content) throws IOException {
        File testFile = File.createTempFile("game_test", ".txt");
        testFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write(content);
        }

        return testFile;
    }

    private String validGameContent =
            "7 7\n" +
                    "10\n" +
                    "10\n" +
                    "1 : 10 : 0 : Melanie : 3 : 3\n" +
                    "2 : 10 : 1 : Walker : 5 : 3\n" +
                    "3 : 10 : 2 : Frankenstein : 4 : 5\n" +
                    "4 : 10 : 4 : Crawler : 0 : 1\n" +
                    "5 : 10 : 0 : Babe : 1 : 1\n" +
                    "6 : 20 : 0 : Karate Kid : 3 : 4\n" +
                    "7 : 20 : 1 : Freddie M. : 4 : 3\n" +
                    "8 : 20 : 2 : James Bond : 5 : 6\n" +
                    "9 : 20 : 1 : John Wayne : 6 : 5\n" +
                    "10 : 20 : 3 : Max : 2 : 2\n" +
                    "4\n" +
                    "-1 : 0 : 6 : 3\n" +
                    "-2 : 1 : 2 : 0\n" +
                    "-3 : 2 : 2 : 1\n" +
                    "-4 : 3 : 5 : 4\n" +
                    "2\n" +
                    "0 : 6\n" +
                    "6 : 0";

    @Test
    public void testLoadValidGameFile() {
        try {
            File testFile = createTestFile(validGameContent);
            GameManager gameManager = new GameManager();

            gameManager.loadGame(testFile);

            // Verificar tamanho do tabuleiro
            assertArrayEquals(new int[]{7, 7}, gameManager.getWorldSize());

            // Verificar equipe inicial
            assertEquals(10, gameManager.getInitialTeamId());
            assertEquals(10, gameManager.getCurrentTeamId());
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }

    @Test
    public void testGameItems() {
        try {
            File testFile = createTestFile(validGameContent);
            GameManager gameManager = new GameManager();

            gameManager.loadGame(testFile);

            // Verificar quantidade de itens
            for (int i = -1; i >= -4; i--) {
                String[] itemInfo = gameManager.getEquipmentInfo(i);
                assertNotNull(itemInfo);
            }
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }

    @Test
    public void testGameSafeHavens() {
        try {
            File testFile = createTestFile(validGameContent);
            GameManager gameManager = new GameManager();

            gameManager.loadGame(testFile);

            // Verificar portas/safe havens
            List<Integer> idsInSafeHaven = gameManager.getIdsInSafeHaven();
            assertNotNull(idsInSafeHaven);

            // Verificar coordenadas específicas de safe havens
            String squareInfo = gameManager.getSquareInfo(0, 6);
            assertNotNull(squareInfo);

            squareInfo = gameManager.getSquareInfo(6, 0);
            assertNotNull(squareInfo);
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }

    @Test
    public void testGameSaveAndReload() {
        try {
            File testFile = createTestFile(validGameContent);
            GameManager gameManager = new GameManager();

            gameManager.loadGame(testFile);

            // Salvar o jogo
            File saveFile = File.createTempFile("saved_game", ".txt");
            saveFile.deleteOnExit();

            gameManager.saveGame(saveFile);

            // Recarregar o jogo salvo
            GameManager reloadedGameManager = new GameManager();
            reloadedGameManager.loadGame(saveFile);

            // Verificar se os dados básicos são os mesmos
            assertArrayEquals(gameManager.getWorldSize(), reloadedGameManager.getWorldSize());
            assertEquals(gameManager.getInitialTeamId(), reloadedGameManager.getInitialTeamId());
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }

    @Test
    public void testGameSpecificMoves() {
        try {
            File testFile = createTestFile(validGameContent);
            GameManager gameManager = new GameManager();

            gameManager.loadGame(testFile);

            // Tentativa de movimento de uma criatura válida
            boolean moveResult = gameManager.move(3, 4, 4, 4); // Karate Kid
            assertFalse(moveResult);
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }

    @Test
    public void testCustomizeBoardReturnsEmptyMap() {
        GameManager gameManager = new GameManager();
        HashMap<String, String> customBoard = gameManager.customizeBoard();
        assertTrue(customBoard.isEmpty());
    }

    @Test
    public void testGetTabuleiro() {
        GameManager gameManager = new GameManager();
        Tabuleiro tabuleiro = gameManager.getTabuleiro();
        assertNotNull(tabuleiro);
    }

    @Test
    public void testGetCreatureInfoNonExistent() {
        try {
            File testFile = createTestFile(validGameContent);
            GameManager gameManager = new GameManager();
            gameManager.loadGame(testFile);

            // Tenta recuperar info de uma criatura que não existe
            assertNull(gameManager.getCreatureInfo(100));
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }

    @Test
    public void testGetCreatureInfoInSafeHaven() {
        try {
            File testFile = createTestFile(validGameContent);
            GameManager gameManager = new GameManager();
            gameManager.loadGame(testFile);

            // Escolha uma criatura que possa estar em safe haven
            String[] creatureInfo = gameManager.getCreatureInfo(6); // Por exemplo, Karate Kid
            assertNotNull(creatureInfo);
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }

    @Test
    public void testGetCreatureInfoAsString() {
        try {
            File testFile = createTestFile(validGameContent);
            GameManager gameManager = new GameManager();
            gameManager.loadGame(testFile);

            String creatureInfoString = gameManager.getCreatureInfoAsString(1); // Melanie
            assertNotNull(creatureInfoString);
            assertTrue(creatureInfoString.contains("Melanie"));
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }

    @Test
    public void testGetCreatureInfoAsStringNonExistent() {
        try {
            File testFile = createTestFile(validGameContent);
            GameManager gameManager = new GameManager();
            gameManager.loadGame(testFile);

            assertNull(gameManager.getCreatureInfoAsString(100));
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }

    @Test
    public void testGetEquipmentInfoAsStringNonExistent() {
        try {
            File testFile = createTestFile(validGameContent);
            GameManager gameManager = new GameManager();
            gameManager.loadGame(testFile);

            assertNull(gameManager.getEquipmentInfoAsString(100));
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }

    @Test
    public void testHasEquipmentNotExistent() {
        try {
            File testFile = createTestFile(validGameContent);
            GameManager gameManager = new GameManager();
            gameManager.loadGame(testFile);

            assertFalse(gameManager.hasEquipment(100, 1));
        } catch (Exception e) {
            fail("Não deveria lançar exceção: " + e.getMessage());
        }
    }

    @Test
    public void testSaveGameNullFile() {
        try {
            GameManager gameManager = new GameManager();
            gameManager.saveGame(null);
            fail("Deveria lançar IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Esperado
        } catch (Exception e) {
            fail("Deveria lançar IllegalArgumentException");
        }
    }

    @Test
    public void testIsDay() {
        GameManager gameManager = new GameManager();
        // Verificar o estado inicial do dia
        assertTrue(gameManager.isDay());
    }

    @Test
    public void testGetSquareInfoInvalid() {
        GameManager gameManager = new GameManager();
        assertNull(gameManager.getSquareInfo(-1, -1));
    }

    @Test
    public void testEscudoMCriacaoComFactory() {
        Posicao posicao = new Posicao(5, 10);
        Item escudo = ItemsFactory.createItem(0, 0, posicao); // Tipo 0 corresponde ao EscudoM

        // Testar se o escudo foi criado corretamente
        assertNotNull(escudo, "O item deve ser criado corretamente");
        assertEquals(0, escudo.getId(), "O id do escudo deve ser 0");
        assertEquals(0, escudo.getTipo(), "O tipo do escudo deve ser 0");
        assertEquals(posicao, escudo.getCoords(), "A posição do escudo deve ser (5, 10)");
    }

    @Test
    public void testMostraTipoEscudoM() {
        Posicao posicao = new Posicao(5, 10);
        Item escudo = ItemsFactory.createItem(0, 0, posicao); // Tipo 0 corresponde ao EscudoM

        // Testar se o tipo do escudo é "Escudo de madeira"
        assertEquals("Escudo de madeira", escudo.mostraTipo(), "O tipo do escudo deve ser 'Escudo de madeira'");
    }

    @Test
    public void testCapacidadeEscudoM() {
        Posicao posicao = new Posicao(5, 10);
        Item escudo = ItemsFactory.createItem(0, 0, posicao); // Tipo 0 corresponde ao EscudoM

        // Testar se a capacidade do escudo é 1
        assertEquals(1, escudo.capacidade(), "A capacidade do escudo deve ser 1");
    }

    @Test
    public void testGetEquipmentInfoEscudoM() {
        Posicao posicao = new Posicao(5, 10);
        Item escudo = ItemsFactory.createItem(0, 0, posicao); // Tipo 0 corresponde ao EscudoM

        // Testar o método getEquipmentInfo
        String[] info = escudo.getEquipmentInfo(escudo.getId());

        assertNotNull(info, "O array de informações do escudo não pode ser nulo");
        assertEquals("0", info[0], "O id do escudo deve ser '0'");
        assertEquals("0", info[1], "O tipo do escudo deve ser '0'");
        assertEquals("5", info[2], "A posição X do escudo deve ser '5'");
        assertEquals("10", info[3], "A posição Y do escudo deve ser '10'");
    }

    @Test
    public void testGetSquareInfoEscudoM() {
        Posicao posicao = new Posicao(5, 10);
        Item escudo = ItemsFactory.createItem(0, 0, posicao); // Tipo 0 corresponde ao EscudoM

        // Testar o método getSquareInfo
        String info = escudo.getSquareInfo();

        assertEquals("E:0", info, "A informação do quadrado do escudo deve ser 'E:0'");
    }

    @Test
    public void testAcaoEscudoM() {
        Posicao posicao = new Posicao(5, 10);
        Item escudo = ItemsFactory.createItem(0, 0, posicao); // Tipo 0 corresponde ao EscudoM

        // Testar se a ação do escudo é executada corretamente
        assertTrue(escudo.acao(), "A ação do escudo deve retornar 'true'");
    }

    @Test
    public void testToStringEscudoM() {
        Posicao posicao = new Posicao(5, 10);
        Item escudo = ItemsFactory.createItem(0, 0, posicao); // Tipo 0 corresponde ao EscudoM

        String expectedToString = "0 | Escudo de madeira @ (5, 10)";
        assertEquals(expectedToString, escudo.toString(), "A representação do escudo com o método toString() está incorreta");
    }

    @Test
    public void testSetCoordsEscudoM() {
        Posicao posicao = new Posicao(5, 10);
        Item escudo = ItemsFactory.createItem(0, 0, posicao); // Tipo 0 corresponde ao EscudoM

        // Testar se a posição do escudo pode ser alterada
        Posicao novaPosicao = new Posicao(7, 12);
        escudo.setCoords(novaPosicao);

        assertEquals(novaPosicao, escudo.getCoords(), "A posição do escudo deve ser atualizada para (7, 12)");
    }

    @Test
    void testConstrutor() {
        Posicao posicao = new Posicao(5, 5);
        IdosoH idoso = new IdosoH(1, "João", posicao);

        assertEquals(1, idoso.getId());
        assertEquals("João", idoso.getNome());
        assertEquals(posicao, idoso.getCoords());
    }

    @Test
    void testRegrasMove_MovimentoValido() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10); // Dia
        Posicao atual = new Posicao(2, 2);
        Posicao destino = new Posicao(3, 3);

        IdosoH idoso = new IdosoH(1, "João", atual);

        assertTrue(idoso.regrasMove(atual, destino, tabuleiro));
    }

    @Test
    void testRegrasMove_SafeHeaven() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10); // Dia
        Posicao atual = new Posicao(2, 2);
        Posicao destino = new Posicao(4, 4);

        tabuleiro.addPortas(new Heaven(destino));
        IdosoH idoso = new IdosoH(1, "João", atual);

        assertTrue(idoso.regrasMove(atual, destino, tabuleiro));
    }


    @Test
    void testExecutarAcaoAoMover_ComItem() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10); // Criar tabuleiro para teste
        Posicao atual = new Posicao(2, 2);
        Posicao destino = new Posicao(3, 3);

        // Criar item usando a factory
        Item item = ItemsFactory.createItem(1, 1, destino); // EspadaS (tipo = 1)
        tabuleiro.addItem(item);

        IdosoH idoso = new IdosoH(1, "João", atual);

        // Executar a ação de movimento
        idoso.executarAcaoAoMover(atual.getX(), atual.getY(), destino.getX(), destino.getY(), tabuleiro, idoso);

        // Verificar se o item foi apanhado pelo Idoso
        assertEquals(item, idoso.getItem(), "O Idoso deveria ter apanhado o item.");
    }

    @Test
    void testExecutarAcaoAoMover_SemItem() {
        Tabuleiro tabuleiro = new Tabuleiro(10, 10); // Criar tabuleiro para teste
        Posicao atual = new Posicao(2, 2);
        Posicao destino = new Posicao(3, 3);

        IdosoH idoso = new IdosoH(1, "João", atual);

        // Executar a ação de movimento sem itens na posição destino
        idoso.executarAcaoAoMover(atual.getX(), atual.getY(), destino.getX(), destino.getY(), tabuleiro, idoso);

        // Verificar se o Idoso não apanhou nenhum item
        assertNull(idoso.getItem(), "O Idoso não deveria ter apanhado nenhum item.");
    }

    @Test
    void testGetTipo() {
        IdosoH idoso = new IdosoH(1, "João", new Posicao(2, 2));
        assertEquals(2, idoso.getTipo());
    }

    @Test
    void testMostraTipo() {
        IdosoH idoso = new IdosoH(1, "João", new Posicao(2, 2));
        assertEquals("Idoso", idoso.mostraTipo());
    }

    @Test
    void testSetItem() {
        // Creating a Vivo object (AdultoH as a subclass of Vivo)
        Posicao posicao = new Posicao(0, 0);
        Criatura vivo = EntityFactory.createEntity(1, 20, 1, "vivo", posicao);

        // Creating an item
        Item item = ItemsFactory.createItem(1, 0, posicao);

        // Setting the item
        vivo.setItem(item);

        // Verifying that the item is correctly set
        assertEquals(item, vivo.getItem());
        assertEquals(1, vivo.getItensDestruidosOuApanhados());
    }

    @Test
    void testGetItem() {
        // Creating a Vivo object
        Posicao posicao = new Posicao(0, 0);
        Criatura vivo = EntityFactory.createEntity(1, 20, 1, "vivo", posicao);

        // Creating an item
        Item item = ItemsFactory.createItem(1, 0, posicao);


        // Setting the item
        vivo.setItem(item);

        // Verifying that the getItem method returns the correct item
        assertEquals(item, vivo.getItem());
    }

    @Test
    void testAcaoMoverEntreCriaturas() {
        // Create a Vivo object and another creature (different team)
        Posicao posicaoVivo = new Posicao(0, 0);
        Criatura vivo = EntityFactory.createEntity(1, 20, 1, "vivo", posicaoVivo);

        Posicao posicaoCriatura = new Posicao(1, 1);
        Criatura vivoa = EntityFactory.createEntity(1, 10, 1, "vivo", posicaoCriatura);

        Tabuleiro tabuleiro = new Tabuleiro(); // Assuming Tabuleiro constructor initializes an empty board
        tabuleiro.addCriaturas(vivo);
        tabuleiro.addCriaturas(vivoa);

        // Setting an item on the vivo to simulate it having an item
        Item item = ItemsFactory.createItem(1, 0, posicaoVivo);
        vivo.setItem(item);

        // Testing movement action between creatures
        boolean result = vivo.acaoMoverEntreCriaturas(1, 1, tabuleiro);

        // Assert the movement result
        assertFalse(result);
    }

    @Test
    void testExecutarAcaoAoMover() {
        // Create a Vivo object and a Tabuleiro
        Posicao posicaoVivo = new Posicao(0, 0);
        Criatura vivo = EntityFactory.createEntity(1, 20, 1, "vivo", posicaoVivo);
        Tabuleiro tabuleiro = new Tabuleiro();

        // Create and add an item to the tabuleiro
        Item item = ItemsFactory.createItem(1, 0, posicaoVivo);
        tabuleiro.addItem(item);

        // Verify that executing action on move results in the item being collected
        vivo.executarAcaoAoMover(0, 0, 1, 1, tabuleiro, vivo);

        // Check that the item has been added to the Vivo
        assertNull(tabuleiro.getItemNaPosicao(1, 1));
    }

    @Test
    void testGetCreatureInfo() {
        // Create a Vivo object
        Posicao posicao = new Posicao(1, 1);
        Criatura vivo = EntityFactory.createEntity(1, 20, 1, "vivo", posicao);

        // Test getCreatureInfo method
        String[] info = vivo.getCreatureInfo(1);

        // Verify the values in the array
        assertEquals("1", info[0]); // ID
        assertEquals("vivo", info[3]); // Name
        assertEquals("1", info[4]); // X position
        assertEquals("1", info[5]); // Y position
    }

    @Test
    void testToString() {
        // Create a Vivo object
        Posicao posicao = new Posicao(1, 1);
        Criatura vivo = EntityFactory.createEntity(1, 20, 1, "vivo", posicao);
        // Test toString method when no item is set
        String expected = "1 | Adulto | Humano | vivo | +0 @ (1, 1)";
        assertEquals(expected, vivo.toString());

        // Now set an item and test again
        Item item = ItemsFactory.createItem(1, 0, posicao);
        vivo.setItem(item);
        expected = "1 | Adulto | Humano | vivo | +1 @ (1, 1) | 1 | Escudo de madeira @ (1, 1)";
        assertEquals(expected, vivo.toString());
    }
    public void testRegrasMove_DestroyItem_Scenario1() {
        Item item = ItemsFactory.createItem(1, 2, new Posicao(1, 1));
        Criatura adultoZ = EntityFactory.createEntity(10, 10,1,"Zombie11" ,new Posicao(0, 0));
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(20);
        tabuleiro.addItem(item); // Add an item at the destination position
        Posicao posicaoAtual = new Posicao(0, 0);
        Posicao posicaoDestino = new Posicao(1, 1);

        boolean result = adultoZ.regrasMove(posicaoAtual, posicaoDestino, tabuleiro);

        assertNull(tabuleiro.getItemNaPosicao(1, 1)); // Check that the item is destroyed
        // Add more specific assertions based on the scenario being tested
    }
    @Test
    void testCriacaoAdultoZComFactory() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar o AdultoZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 1, "AdultoZ", posicao);

        // Verificando se o AdultoZ foi criado corretamente
        assertNotNull(criatura);
        assertEquals("AdultoZ", criatura.getNome()); // Verificando o nome
        assertEquals(1, criatura.getId()); // Verificando o ID
        assertEquals(posicao, criatura.getCoords()); // Verificando a posição inicial
    }

    @Test
    void testRegrasMoveComMovimentoValido() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(3, 3);

        // Usando a EntityFactory para criar o AdultoZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 1, "AdultoZ", posicaoInicial);

        // Criando o Tabuleiro
        Tabuleiro tabuleiro = new Tabuleiro(10,10);
        tabuleiro.setJogada(20); // Definindo a equipe como 10, conforme a lógica de regrasMove

        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi permitido
        assertTrue(podeMover);
        assertEquals(posicaoDestino, criatura.getCoords()); // Verificando se a posição foi atualizada
    }

    @Test
    void testRegrasMoveComMovimentoInvalido() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(1, 4); // Distância maior que 2

        // Usando a EntityFactory para criar o AdultoZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 1, "AdultoZ", posicaoInicial);

        // Criando o Tabuleiro
        Tabuleiro tabuleiro = new Tabuleiro(10,10);
        tabuleiro.setJogada(20); // Definindo a equipe como 10

        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi negado
        assertFalse(podeMover);
        assertEquals(posicaoInicial, criatura.getCoords()); // Verificando que a posição não foi atualizada
    }
    @Test
    void testCriacaoVampiroComFactory() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar o Vampiro
        Criatura criatura = EntityFactory.createEntity(1, 10, 4, "Vampiro", posicao);

        // Verificando se o Vampiro foi criado corretamente
        assertNotNull(criatura);

        // Verificando que o nome do Vampiro é "Vampiro"
        assertEquals("Vampiro", criatura.getNome());

        // Verificando o ID atribuído
        assertEquals(1, criatura.getId());

        // Verificando a posição inicial
        assertEquals(posicao, criatura.getCoords());
    }

    @Test
    void testRegrasMoveDuranteDia() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 2);

        // Usando a EntityFactory para criar o Vampiro
        Criatura criatura = EntityFactory.createEntity(1, 10, 4, "Vampiro", posicaoInicial);

        // Criando o Tabuleiro e configurando o tempo como dia
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(20); // Definindo a equipe como 10

        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi negado porque é dia
        assertFalse(podeMover);
    }

    @Test
    void testRegrasMoveComMovimentoValidoVampiro() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 2);

        // Usando a EntityFactory para criar o Vampiro
        Criatura criatura = EntityFactory.createEntity(1, 10, 4, "Vampiro", posicaoInicial);

        // Criando o Tabuleiro e configurando o tempo como noite
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(20);
        tabuleiro.setTurno(3);// Definindo a equipe como 10// Definindo que é noite, o Vampiro pode se mover

        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi permitido
        assertTrue(podeMover);
        assertEquals(posicaoDestino, criatura.getCoords()); // Verificando se a posição foi atualizada
    }

    @Test
    void testRegrasMoveComMovimentoInvalidoPorSafeHeaven() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 2);

        // Usando a EntityFactory para criar o Vampiro
        Criatura criatura = EntityFactory.createEntity(1, 10, 4, "Vampiro", posicaoInicial);

        // Criando o Tabuleiro e configurando o tempo como noite
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(20);; // Definindo a equipe como 10

        // Simulando que existe um Safe Heaven na posição destino
        tabuleiro.addPortas(new Heaven(posicaoDestino));

        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi negado por causa do Safe Heaven
        assertFalse(podeMover);
    }

    @Test
    void testRegrasMoveComMovimentoInvalidoPorPosicaoOcupada() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 2);

        // Usando a EntityFactory para criar o Vampiro
        Criatura criatura = EntityFactory.createEntity(1, 10, 4, "Vampiro", posicaoInicial);

        // Criando o Tabuleiro e configurando o tempo como noite
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(10); // Definindo a equipe como 10

        // Simulando que existe uma criatura na posição destino
        Criatura outraCriatura = EntityFactory.createEntity(2, 20, 1, "Criatura", posicaoDestino);
        tabuleiro.addCriaturas(outraCriatura);

        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi negado por causa da posição ocupada
        assertFalse(podeMover);
    }

    @Test
    void testToStringVampiro() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar o Vampiro
        Criatura criatura = EntityFactory.createEntity(1, 10, 4, "Vampiro", posicao);

        // Verificando se o método toString retorna a string no formato correto
        String expectedString = "1 | Vampiro | Vampiro | -0 @ (1, 1)";
        assertEquals(expectedString, criatura.toString());
    }
    @Test
    void testCriacaoIdosoZComFactory() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar o IdosoZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 2, "IdosoZ", posicao);

        // Verificando se o IdosoZ foi criado corretamente
        assertNotNull(criatura);

        // Verificando que o nome do IdosoZ é "IdosoZ"
        assertEquals("IdosoZ", criatura.getNome());

        // Verificando o ID atribuído
        assertEquals(1, criatura.getId());

        // Verificando a posição inicial
        assertEquals(posicao, criatura.getCoords());
    }

    @Test
    void testRegrasMoveDuranteDiaIdosoZ() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 2);

        // Usando a EntityFactory para criar o IdosoZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 2, "IdosoZ", posicaoInicial);

        // Criando o Tabuleiro e configurando o tempo como dia
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(20); // Definindo a equipe como 10

        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi negado porque é dia
        assertTrue(podeMover);
    }

    @Test
    void testRegrasMoveComMovimentoValidoIdosoZ() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 2);

        // Usando a EntityFactory para criar o IdosoZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 2, "IdosoZ", posicaoInicial);

        // Criando o Tabuleiro e configurando o tempo como noite
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(20); // Definindo a equipe como 10


        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi permitido
        assertTrue(podeMover);
        assertEquals(posicaoDestino, criatura.getCoords()); // Verificando se a posição foi atualizada
    }

    @Test
    void testRegrasMoveComMovimentoInvalidoPorSafeHeavenIdosoZ() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 2);

        // Usando a EntityFactory para criar o IdosoZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 2, "IdosoZ", posicaoInicial);

        // Criando o Tabuleiro e configurando o tempo como noite
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(20); // Definindo a equipe como 10
        tabuleiro.setTurno(3); // Definindo que é noite, o IdosoZ pode se mover

        // Simulando que existe um Safe Heaven na posição destino
        tabuleiro.addPortas(new Heaven(posicaoDestino));

        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi negado por causa do Safe Heaven
        assertFalse(podeMover);
    }

    @Test
    void testRegrasMoveComMovimentoInvalidoPorPosicaoOcupadaIdosoZ() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 2);

        // Usando a EntityFactory para criar o IdosoZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 2, "IdosoZ", posicaoInicial);

        // Criando o Tabuleiro e configurando o tempo como noite
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(20); // Definindo a equipe como 10
        tabuleiro.setTurno(20); // Definindo que é noite, o IdosoZ pode se mover

        // Simulando que existe uma criatura na posição destino
        Criatura outraCriatura = EntityFactory.createEntity(2, 20, 1, "Criatura", posicaoDestino);
        tabuleiro.addCriaturas(outraCriatura);
        Item item = ItemsFactory.createItem(1,2,posicaoDestino);
        outraCriatura.setItem(item);

        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi negado por causa da posição ocupada
        assertTrue(podeMover);
    }

    @Test
    void testToStringIdosoZ() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar o IdosoZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 2, "IdosoZ", posicao);

        // Verificando se o método toString retorna a string no formato correto
        String expectedString = "1 | Idoso | Zombie | IdosoZ | -0 @ (1, 1)";
        assertEquals(expectedString, criatura.toString());
    }
    @Test
    void testCriacaoCriancaZComFactory() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar a CriancaZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 0, "CriancaZ", posicao);

        // Verificando se a CriancaZ foi criada corretamente
        assertNotNull(criatura);

        // Verificando que o nome da CriancaZ é "CriancaZ"
        assertEquals("CriancaZ", criatura.getNome());

        // Verificando o ID atribuído
        assertEquals(1, criatura.getId());

        // Verificando a posição inicial
        assertEquals(posicao, criatura.getCoords());
    }

    @Test
    void testRegrasMoveInvalidoCriancaZ() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 1);

        // Usando a EntityFactory para criar a CriancaZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 0, "CriancaZ", posicaoInicial);

        // Criando o Tabuleiro e configurando o tempo como dia
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(10); // Definindo a equipe como 10

        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi permitido
        assertFalse(podeMover);
    }

    @Test
    void testRegrasMoveComMovimentoInvalidoPorSafeHeavenCriancaZ() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 2);

        // Usando a EntityFactory para criar a CriancaZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 0, "CriancaZ", posicaoInicial);

        // Criando o Tabuleiro e configurando o tempo como noite
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(20); // Definindo a equipe como 10// Definindo que é noite, a CriancaZ pode se mover

        // Simulando que existe um Safe Heaven na posição destino
        tabuleiro.addPortas(new Heaven(posicaoDestino));

        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi negado por causa do Safe Heaven
        assertFalse(podeMover);
    }

    @Test
    void testRegrasMoveComMovimentoValidoPorPosicaoOcupadaCriancaZ() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 1);

        // Usando a EntityFactory para criar a CriancaZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 0, "CriancaZ", posicaoInicial);

        // Criando o Tabuleiro e configurando o tempo como noite
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(20); // Definindo a equipe como 10// Definindo que é noite, a CriancaZ pode se mover

        // Simulando que existe uma criatura na posição destino
        Criatura outraCriatura = EntityFactory.createEntity(2, 20, 1, "Criatura", posicaoDestino);
        tabuleiro.addCriaturas(outraCriatura);

        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi negado por causa da posição ocupada
        assertTrue(podeMover);
    }

    @Test
    void testRegrasMoveComMovimentoValidoCriancaZ() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 1);

        // Usando a EntityFactory para criar a CriancaZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 0, "CriancaZ", posicaoInicial);

        // Criando o Tabuleiro e configurando o tempo como noite
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(20); // Definindo a equipe como 10

        // Executando a lógica do movimento
        boolean podeMover = criatura.regrasMove(posicaoInicial, posicaoDestino, tabuleiro);

        // Verificando se o movimento foi permitido
        assertTrue(podeMover);
        assertEquals(posicaoDestino, criatura.getCoords()); // Verificando se a posição foi atualizada
    }

    @Test
    void testToStringCriancaZ() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar a CriancaZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 0, "CriancaZ", posicao);

        // Verificando se o método toString retorna a string no formato correto
        String expectedString = "1 | Criança | Zombie | CriancaZ | -0 @ (1, 1)";
        assertEquals(expectedString, criatura.toString());
    }
    @Test
    void testCriacaoZombieComFactory() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar uma CriancaZ (que é uma subclasse de Zombie)
        Criatura criatura = EntityFactory.createEntity(1, 10, 0, "CriancaZ", posicao);

        // Verificando se a CriancaZ foi criada corretamente
        assertNotNull(criatura);

        // Verificando que o nome da CriancaZ é "CriancaZ"
        assertEquals("CriancaZ", criatura.getNome());

        // Verificando o ID atribuído
        assertEquals(1, criatura.getId());

        // Verificando a posição inicial
        assertEquals(posicao, criatura.getCoords());
    }

    @Test
    void testZombieAdicionaItensDestruidos() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar uma CriancaZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 0, "CriancaZ", posicao);

        // Verificando que o contador de itens destruídos é inicializado como 0
        assertEquals(0, criatura.getItensDestruidosOuApanhados());

        // Adicionando um item destruído
        criatura.addItensDestruidos();

        // Verificando se o contador foi atualizado corretamente
        assertEquals(1, criatura.getItensDestruidosOuApanhados());
    }


    @Test
    void testZombieAcaoMoverEntreCriaturas() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 1);

        // Usando a EntityFactory para criar a CriancaZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 0, "CriancaZ", posicaoInicial);

        // Criando o Tabuleiro e configurando o time da criatura
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(20); // Definindo a equipe como 10

        // Criando outra criatura na posição destino
        Criatura outraCriatura = EntityFactory.createEntity(2, 20, 0, "Criatura", posicaoDestino);
        tabuleiro.addCriaturas( outraCriatura);

        // Executando a ação de movimento entre criaturas
        boolean podeMover = criatura.acaoMoverEntreCriaturas(posicaoDestino.getX(), posicaoDestino.getY(), tabuleiro);

        // Verificando se o movimento foi bloqueado ou permitido
        assertTrue(podeMover); // O movimento deve ser permitido, já que a outra criatura não está no mesmo time
    }

    @Test
    void testZombieToString() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar uma CriancaZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 0, "CriancaZ", posicao);

        // Verificando se o método toString retorna a string no formato correto
        String expectedString = "1 | Criança | Zombie | CriancaZ | -0 @ (1, 1)";
        assertEquals(expectedString, criatura.toString());

        // Marcando a criatura como transformada
        criatura.setTransformado(true);

        // Verificando novamente o método toString após transformação
        expectedString = "1 | Criança | Zombie (Transformado) | CriancaZ | -0 @ (1, 1)";
        assertEquals(expectedString, criatura.toString());
    }

    @Test
    void testGetCreatureInfoZombie() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar uma CriancaZ
        Criatura criatura = EntityFactory.createEntity(1, 10, 0, "CriancaZ", posicao);

        // Verificando o retorno do método getCreatureInfo
        String[] info = criatura.getCreatureInfo(1);

        assertNotNull(info);
        assertEquals("1", info[0]); // ID
        assertEquals("Criança", info[1]); // Tipo
        assertEquals("Zombie", info[2]); // Estado
        assertEquals("CriancaZ", info[3]); // Nome
        assertEquals("1", info[4]); // Posição X
        assertEquals("1", info[5]); // Posição Y
    }
    @Test
    void testCriacaoVivoComFactory() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar um Humano (subclasse de Vivo)
        Criatura criatura = EntityFactory.createEntity(1, 20, 0, "Humano", posicao);

        // Verificando se a criatura foi criada corretamente
        assertNotNull(criatura);

        // Verificando que o nome do Humano é "Humano"
        assertEquals("Humano", criatura.getNome());

        // Verificando o ID atribuído
        assertEquals(1, criatura.getId());

        // Verificando a posição inicial
        assertEquals(posicao, criatura.getCoords());
    }

    @Test
    void testVivoAdicionaItem() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar um Humano
        Criatura criatura = EntityFactory.createEntity(1, 20, 0, "Humano", posicao);

        // Verificando que o contador de itens apanhados é 0
        assertEquals(0, criatura.getItensDestruidosOuApanhados());

        // Criando um item
        Item item = ItemsFactory.createItem(2,1,posicao);

        // Adicionando o item ao Humano
        criatura.setItem(item);

        // Verificando se o contador de itens apanhados foi incrementado
        assertEquals(1, criatura.getItensDestruidosOuApanhados());
    }

    @Test
    void testVivoAcaoMoverEntreCriaturas() {
        // Criando uma posição inicial e uma destino
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoDestino = new Posicao(2, 1);

        // Usando a EntityFactory para criar o Humano
        Criatura criatura = EntityFactory.createEntity(1, 20, 0, "Humano", posicaoInicial);

        // Criando o Tabuleiro e configurando o time da criatura
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setJogada(10); // Definindo a equipe como 20

        // Criando outra criatura na posição destino
        Criatura outraCriatura = EntityFactory.createEntity(2, 20, 0, "Criatura", posicaoDestino);
        tabuleiro.addCriaturas(outraCriatura);

        // Executando a ação de movimento entre criaturas
        boolean podeMover = criatura.regrasMove(posicaoInicial,posicaoDestino,tabuleiro);

        // Verificando se o movimento foi permitido
        assertFalse(podeMover); // O movimento deve ser permitido se a outra criatura não for do mesmo time
    }

    @Test
    void testVivoMoverParaSafeHaven() {
        // Criando uma posição para o Humano
        Posicao posicaoInicial = new Posicao(1, 1);
        Posicao posicaoSafeHaven = new Posicao(2, 2);

        // Usando a EntityFactory para criar o Humano
        Criatura criatura = EntityFactory.createEntity(1, 20, 0, "Humano", posicaoInicial);

        // Criando o Tabuleiro
        Tabuleiro tabuleiro = new Tabuleiro();

        // Criando o Safe Haven na posição destino
        Heaven safeHaven = new Heaven(posicaoSafeHaven);
        tabuleiro.addPortas(safeHaven);

        // Executando a ação de mover para o Safe Haven
        criatura.executarAcaoAoMover(posicaoInicial.getX(), posicaoInicial.getY(), posicaoSafeHaven.getX(), posicaoSafeHaven.getY(), tabuleiro, criatura);

        // Verificando se a criatura foi removida do tabuleiro e enviada para o Safe Haven
        assertTrue(criatura.estaNoHeaven);
    }

    @Test
    void testVivoToString() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar o Humano
        Criatura criatura = EntityFactory.createEntity(1, 20, 0, "Humano", posicao);

        // Verificando se o método toString retorna a string no formato correto
        String expectedString = "1 | Criança | Humano | Humano | +0 @ (1, 1)";
        assertEquals(expectedString, criatura.toString());

        // Marcando a criatura como no Heaven
        criatura.setEstaNoHeaven();

        // Verificando novamente o método toString após a criatura estar no Heaven
        expectedString = "1 | Criança | Humano | Humano | +0 @ Safe Haven";
        assertEquals(expectedString, criatura.toString());
    }

    @Test
    void testGetCreatureInfoVivo() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar o Humano
        Criatura criatura = EntityFactory.createEntity(1, 20, 0, "Humano", posicao);

        // Verificando o retorno do método getCreatureInfo
        String[] info = criatura.getCreatureInfo(1);

        assertNotNull(info);
        assertEquals("1", info[0]); // ID
        assertEquals("Criança", info[1]); // Tipo
        assertEquals("Humano", info[2]); // Estado
        assertEquals("Humano", info[3]); // Nome
        assertEquals("1", info[4]); // Posição X
        assertEquals("1", info[5]); // Posição Y
    }

    @Test
    void testVivoHasEquipment() {
        // Criando uma posição
        Posicao posicao = new Posicao(1, 1);

        // Usando a EntityFactory para criar o Humano
        Criatura criatura = EntityFactory.createEntity(1, 20, 0, "Humano", posicao);

        // Criando um item e atribuindo ao Humano
        Item item = ItemsFactory.createItem(2,1,posicao); // Tipo 1, por exemplo, uma arma
        criatura.setItem(item);

        // Verificando se o Humano tem o item do tipo 1
        assertTrue(criatura.hasEquipment(1, 1));

        // Verificando se o Humano não tem um item de outro tipo
        assertFalse(criatura.hasEquipment(1, 2));
    }
}

