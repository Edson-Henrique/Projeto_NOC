package projetos.springboot.projeto_noc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import projetos.springboot.projeto_noc.model.UsuarioModel;
import projetos.springboot.projeto_noc.repository.UsuarioRepository;

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
 *  @Autowired serve para injetar dependências
 * 
 */

@RestController
public class GreetingsController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;//criei para testar minha persistência

   
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET) /*só aceita requisiçõse get*/
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
    	
    	UsuarioModel usuarioModel = new UsuarioModel();
    	usuarioModel.setNome(name);
    	usuarioRepository.save(usuarioModel);//mandei salvar no banco o nome que peguei na URL
    	
        return "Hello " + name + "!";
        
        
    }
    
    @RequestMapping(value = "/mostrartexto/{texto}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String metodoTeste(@PathVariable String texto) {
    	return "Olá" + texto;
    	
    }
    
   //endpoint para listar os dados (GET - faz buscas)
   @GetMapping(value = "listar") /*faz a mesma coisa do @RequestMapping, porém já indica que o método irá responder a requisições HTTP GET*/
   @ResponseBody //retorna o corpo da resposta (o corpo é como se fosse uma caixinha com os dados dentro)
   public ResponseEntity<List<UsuarioModel>> listarUsuario() {//método retorna uma lista
    	
    	List<UsuarioModel> usuarios = usuarioRepository.findAll();//executa e consulta no banco de dados
    	
    	return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);//retorna a lista em json
    	//usuarios são o corpo da resposta (os dados); HttpStatus é o status HTTP, que 200 (ok)
    	
    }
   
   @PostMapping(value = "salvar")
   @ResponseBody//retorna o corpo da resposta (o corpo é como se fosse uma caixinha com os dados dentro)
   public ResponseEntity<UsuarioModel> salvarUsuario(@RequestBody UsuarioModel usuarioModel) {//@ResponseBody recebe o corpo da resposta (dados) para salvar
	   
	   UsuarioModel usuario = usuarioRepository.save(usuarioModel);//salva meus dados no banco
	   
	   return new ResponseEntity<UsuarioModel>(usuario, HttpStatus.CREATED);//retorna meus dados salvos
	 //usuarios são o corpo da resposta; HttpStatus é o status HTTP, que 201 (created)
	   
   }
   
    
}
