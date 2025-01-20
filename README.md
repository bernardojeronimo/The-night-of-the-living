![](UML.png?raw=true "Diagrama UML")

Justificação do Diagrama de Classes Baseado no Código Disponibilizado
Hierarquia e Organização
A modelação segue uma hierarquia clara, onde a classe abstrata Criatura atua como superclasse para todas as entidades
vivas e zumbis. Esta escolha permite consolidar atributos comuns, como id, nome, coords, e métodos essenciais como
getId(), getNome(), e getCoords(). A herança facilita a reutilização do código e a extensão de funcionalidades
específicas em subclasses, como Vivo e Zombie, que representam equipas opostas.

Além disso, Vivo é estendida por subclasses como AdultoH, CriancaH, IdosoH, e Cao. Por outro lado, Zombie é estendida
por CriancaZ, IdosoZ e Vampiro. Estas subclasses refletem diferentes tipos de entidades com comportamentos e
características específicos, definidos pelos métodos sobrescritos, como regrasMove() e mostraTipo().

Definição de Atributos e Métodos
Cada classe foi desenhada para cumprir responsabilidades específicas:

Criatura contém atributos e métodos genéricos usados por todas as entidades, como podeMover() e executarAcaoAoMover().
Vivo e Zombie implementam comportamentos distintos relacionados às suas equipas, como os métodos
acaoMoverEntreCriaturas() e zombieAtaques() (no caso dos zombies).
As subclasses de Vivo, como AdultoH e Cao, têm métodos customizados, por exemplo, AdultoH suporta movimentos diagonais,
enquanto Cao tem restrições baseadas em itens no destino.
Similarmente, subclasses de Zombie como CriancaZ e Vampiro diferenciam-se pela lógica de movimento e interações.
Relacionamentos e Modularidade

A superclasse Criatura abstrai atributos e funcionalidades comuns, garantindo uma arquitetura mais modular e
reutilizável.
As associações entre as entidades e o Tabuleiro (não visível no diagrama, mas evidente no código) indicam a dependência
das classes em relação ao estado e interações do jogo.
O uso de métodos abstratos como regrasMove() permite às subclasses implementar a lógica de movimentação conforme as
regras específicas de cada tipo de criatura, como Vampiro não se movimentando durante o dia.
Decisões Justificadas
Uso de Herança: Minimiza a duplicação de código e organiza as entidades em grupos lógicos (vivos e zumbis).
Polimorfismo: Métodos como regrasMove() e mostraTipo() garantem flexibilidade ao tratar entidades de forma uniforme no
jogo.
Clareza: As subclasses têm implementações específicas, tornando o comportamento de cada tipo de criatura previsível e
alinhado às regras do jogo.

https://youtu.be/NnwNSjVAWCU