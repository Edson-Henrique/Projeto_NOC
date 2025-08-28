package projetos.springboot.projeto_noc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @RestController
 * Esta anotação indica que a classe é um controlador REST, e que recebe uma requisiçãoe em HTTP (como o GET, POST, etc)
 * e retorna um texto
 * 
 * Um controlador intercepta todas as requisições feitas ao sistema
 * 
 * @RequestMapping
 * estou mapeando meu método para uma rota URL chamada value (value = "URL RAIZ /{nome}") e ainda estou passando 
 * por parâmetro, um argumento chamado "name"; a partir de então meu método se tornou um endpoint (um ponto de acesso);
 * Meu endpoint responde a requisições do tipo get e rotorna "Hello + nome!"
 * 
 * @PathVariable 
 * Esta anotação pega as variáveis contidas na URL e as transformam em parâmetros para o método do controlador.
 * 
 *  @ResponseStatus 
 *  Esta anotação indica o status http que a aplicação var retornar. (200 OK)
 * 
 */

@RestController
public class GreetingsController {
   
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET) /*só aceita requisiçõse get*/
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Hell " + name + "!";
    }
    
    @RequestMapping(value = "/mostrartexto/{texto}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String metodoTeste(@PathVariable String texto) {
    	return "Olá" + texto;
    }
    
}
