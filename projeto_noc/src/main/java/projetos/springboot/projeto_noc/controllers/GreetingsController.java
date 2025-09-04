package projetos.springboot.projeto_noc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import projetos.springboot.projeto_noc.model.UsuarioModel;
import projetos.springboot.projeto_noc.repository.UsuarioRepository;

/**
 * 
 * @RestController
 * Esta anotação indica que a classe é um controlador REST, e que recebe uma requisições em HTTP (como o GET, POST, etc)
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
 *  ResponseEntity é uma classe do Spring Boot que controla o corpo da resposta (não requisiçaõ) e o status http
 * 
 */

@RestController
public class GreetingsController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;//criei para testar minha persistência
    
   //endpoint para listar os dados (GET - faz buscas)
   @GetMapping(value = "listar") /*faz a mesma coisa do @RequestMapping, porém já indica que o método irá responder a requisições HTTP GET*/
   @ResponseBody //retorna os dados no corpo da resposta (sem ser na URL), (o corpo é como se fosse uma caixinha com os dados dentro) e geralmente é em JSON
   public ResponseEntity<List<UsuarioModel>> listar() {//método retorna uma lista
    	
    	List<UsuarioModel> usuarios = usuarioRepository.findAll();//executa e consulta no banco de dados
    	
    	return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);//retorna a lista em json
    	//usuarios são o corpo da resposta (os dados); HttpStatus é o status HTTP, que 200 (ok)
    	
    }
   
   @PostMapping(value = "salvar")
   @ResponseBody//retorna os dados no corpo da resposta (sem ser na URL), (o corpo é como se fosse uma caixinha com os dados dentro) e geralmente é em JSON
   public ResponseEntity<UsuarioModel> salvar(@RequestBody UsuarioModel usuarioModel) {//@RequestBody recebe o corpo da resposta (dados geralmente em JSON) e os transforma em um objeto real e passa para UsuarioModel
	   
	   UsuarioModel usuario = usuarioRepository.save(usuarioModel);//salva meus dados no banco
	   
	   return new ResponseEntity<UsuarioModel>(usuario, HttpStatus.CREATED);//retorna meus dados salvos
	 //usuarios são o corpo da resposta; HttpStatus é o status HTTP, que 201 (created)
	   
   }
   
   @DeleteMapping(value = "deletar")
   @ResponseBody//retorna o corpo da resposta, (o corpo é como se fosse uma caixinha com os dados dentro) e geralmente é em JSON
   public ResponseEntity<String> deletar(@RequestParam Long id) {//@RequestParam pega parâmetros que vem da URL ou no corpo de requisição, desde que sejam formulários ou dados simples 
	   
	   usuarioRepository.deleteById(id);//deleta meu usuário no banco, através do ID
	   
	   return new ResponseEntity<String>("Usuário deletado com sucesso!", HttpStatus.OK);//retorna um texto, mais o status HTTP 200 (ok)
     
	}
   
   @GetMapping(value = "buscarPeloId")
   @ResponseBody//retorna o corpo da resposta, (o corpo é como se fosse uma caixinha com os dados dentro) e geralmente é em JSON
   public ResponseEntity<UsuarioModel> buscarPeloId(@RequestParam(name = "id") Long id) {//@RequestParam pega parâmetros que vem da URL ou no corpo de requisição, desde que sejam formulários ou dados simples
	   //(name = "id") é para reforçar a informação
	   
	   UsuarioModel usuarioModel = usuarioRepository.findById(id).get();//busca usuário pelo ID
	   
	   return new ResponseEntity<UsuarioModel>(usuarioModel, HttpStatus.OK);//retorna um json, mais o status HTTP 200 (ok)
     
	} 
   
   @PutMapping(value = "atualizar")
   @ResponseBody//retorna o corpo da resposta, (o corpo é como se fosse uma caixinha com os dados dentro) e geralmente é em JSON
   public ResponseEntity<?> atualizar(@RequestBody UsuarioModel usuarioModel) {//a ? me permite retornar qualquer coisa (por isso retornei uma String e depois um objeto)
	   
	   if(usuarioModel.getId() == null) {
		   return new ResponseEntity<String>("O id não foi informado", HttpStatus.BAD_REQUEST);//http status 400
	   }
	   
	   UsuarioModel usuario = usuarioRepository.saveAndFlush(usuarioModel);//saveAndFlush atualizar e já salva 
	   
	   return new ResponseEntity<UsuarioModel>(usuario, HttpStatus.OK);//retorna meus dados salvos
	 //usuarios são o corpo da resposta; HttpStatus é o status HTTP, que 200 (ok)
	   
   }
   
  
   @GetMapping(value = "buscarPeloNome")
   @ResponseBody//retorna o corpo da resposta, (o corpo é como se fosse uma caixinha com os dados dentro) e geralmente é em JSON
   public ResponseEntity<List<UsuarioModel>> buscarPeloNome(@RequestParam(name = "nome") String nome) {//@RequestBody recebe um json -  @RequestParam recebe um parâmetro
	   //(name = "id") é para reforçar a informação
	   
	    List<UsuarioModel> usuarioModel = usuarioRepository.buscarPorNome(nome.trim().toUpperCase());//busca usuário pelo nome - trim() retira o espaço
	   
	   return new ResponseEntity<List<UsuarioModel>>(usuarioModel, HttpStatus.OK);//retorna um json, mais o status HTTP 200 (ok)
     
	} 
	
  
}