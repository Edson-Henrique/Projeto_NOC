package projetos.springboot.projeto_noc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @SpringBootApplication
 * Esta anotação marca a minha classe como a principal do programa, e faz dela a porta de entrada para o Spring Boot
 * 
 * No método "main" eu tenho o próprio SpringBootAplication sendo inicializado (.run) e recebendo a própria 
 * classe Aplication como parâmetro. Neste trecho a mágica acontece, e o SpringBootAplication passar a atuar 
 * e começa primeiramete pela classe Application.
 * 
 * */

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    } 
}
