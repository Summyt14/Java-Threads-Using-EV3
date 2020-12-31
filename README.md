# FSO


## Indice
* [Ideias](#ideias)
* [Coisas a fazer](#coisas-a-fazer)
* [Enunciado 2](#enunciado-2)
* [Enunciado 3](#enunciado-3)

## Ideias
...
	
## Coisas a fazer
...

## Enunciado 2

**Objetivos:** Desenvolvimento de aplicações multitarefa em Java. Comunicação e
sincronização entre tarefas Java. Modelo Produtor-Consumidor.

Pretende-se o desenvolvimento de uma aplicação, escrita na linguagem Java, que permite o
controlo automático de um robot desenhador no espaço bidimensional.

O controlo automático do robot desenhador é realizado por três comportamentos
independentes que comandam o robot, o comportamento “**DesenharQuadrado**”, o
comportamento “**DesenharCirculo**” e o comportamento “**EspaçarFormasGeométricas**”.
Estes três comportamentos agregam a classe “**ClienteDoRobot**”. Estes três comportamentos
são tarefas produtoras de comandos que comunicam com uma tarefa “**ServidorDoRobot**”
através de um “**BufferCircular**”. A tarefa “**ServidorDoRobot**” tem como funções consumir
e interpretar os comandos existentes no “**BufferCircular**” e executá-los no robot desenhador.

O robot desenhador deve ser simulado com a classe “**RobotDesenhador**” que deve
implementar métodos públicos semelhantes aos do Robot EV3 (descritos no Anexo 2 das
folhas de FSO) definidos nas secções “Robot EV3 - Comandos de comunicação” e “Robot
Ev3 - Comandos de movimento”. A classe “**RobotDesenhador**” agrega uma GUI (Graphical
User Interface) onde deve afixar todos os comandos mandados executar pelo
“**ServidorDoRobot**” após o canal de comunicação ser aberto. A estado do canal de
comunicação deve ser visível na GUI.

A tarefa “**ServidorDoRobot**” agrega uma GUI onde deve afixar todos os comandos lidos do
“**BufferCircular**” e de todos os comandos mandados executar no robot.

A classe “**BufferCircular**” deve memorizar um máximo de 16 comandos e deve
disponibilizar métodos públicos de leitura e escrita no Buffer. O método de leitura deve ser
bloqueante sempre que uma tarefa consumidora queira ler um comando e não exista 
comandos no Buffer. O método de escrita não deve ser bloqueante, um processo produtor
deve escrever sempre que quiser no Buffer.

A classe “**ClienteDoRobot**” que é conhecida dos três comportamentos deve de ter a mesma
interface pública da classe “**RobotDesenhador**”. O propósito desta classe é converter cada
um dos métodos públicos numa “**Mensagem**” a inserir no “**BufferCircular**”. As várias
mensagens especificadas pelo projetista, devem derivar da classe “**Mensagem**”, sendo
tratadas por polimorfismo no programa. Por exemplo, a mensagem correspondente ao
método `public void CurvarEsquerda(int raio, int angulo)` pode ser:

> int id, int tipo, int raio, int angulo

O comportamento “**DesenharQuadrado**” deve criar uma trajetória semelhante à forma
geométrica de um quadrado, através da combinação de um conjunto de comandos
disponibilizados na classe “**ClienteDoRobot**”.

O comportamento “**DesenharCirculo**” deve criar uma trajetória semelhante à forma
geométrica de um círculo, através da combinação de um conjunto de comandos
disponibilizados na classe “**ClienteDoRobot**”.

O comportamento “**EspaçarFormasGeométricas**” deve criar um movimento no robot, tal
que duas formas geométricas, desenhadas consecutivamente pelo robot, não fiquem
sobrepostas no espaço bidimensional.

Os comportamentos têm de ter em consideração que a **velocidade de movimento do robot**
é de **30cm/s**, significando que desenhar uma forma geométrica demora tempo.

A sincronização entre as diferentes tarefas utiliza os mecanismos de sincronização estudados
nas aulas, que são os semáforos e os monitores.

* [Voltar ao topo](#fso)

## Enunciado 3

**Objetivo:** Manipulação de ficheiros em JAVA utilizando InputStreams e OutputStreams
numa aplicação multiprocesso. Utilização do robot como sistema alvo para a validação
completa do trabalho.

Desenvolva uma *thread* “**GravarFormas**” que é composta por uma GUI com as opções:
* Gravar uma sequência de formas geométricas num ficheiro ou parar a gravação.
* Reproduzir a sequência de formas geométricas gravada num determinado ficheiro.
* Definir o nome do ficheiro.

A opção 1 permite gravar ou parar a gravação num ficheiro de uma sequência de formas
geométricas, para posterior reprodução. Uma sequência de formas geométricas é
composta às formas geradas pelos comportamentos implementados na primeira parte do
trabalho.

A opção 2 permite a reprodução de uma sequência de formas geométricas gravadas num
ficheiro. Durante uma reprodução não é permitido que os outros comportamentos enviem
comandos para o robot.

A opção 3 permite definir o nome do ficheiro para gravação ou reprodução.
A implementação das três opções na GUI do “**GravarFormas**” é livre e deixada à
criatividade de cada grupo.

A implementação da *thread* “**GravarFormas**” obriga a que a manipulação de ficheiros
utilize as classes do JAVA de InputStream e de OutputStream, de entre o conjunto de
classes definidas nas folhas da disciplina.

A validação final das várias partes do trabalho é sobre o Robot EV3, utilizando as
bibliotecas JAVA disponibilizadas na página da disciplina no Moodle.

* [Voltar ao topo](#fso)
