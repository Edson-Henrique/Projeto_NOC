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
 * Meu endpoint responde a requisições do tipo get e rotorna "Hello + nome!" - isso daqui é de um método padrão do spring boot;
 * porém já foi excluído.
 * 
 * @PathVariable 
 * Esta anotação pega as variáveis contidas na URL e as transformam em parâmetros para o método do controlador.
 * 
 * @ResponseStatus 
 * Esta anotação indica o status http que a aplicação var retornar. 
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
    
   //endpoint para listar usuários (GET - faz buscas)
   @GetMapping(value = "listar") /*faz a mesma coisa do @RequestMapping, porém já indica que o método irá responder a requisições HTTP GET*/
   @ResponseBody //retorna os dados no corpo da resposta (sem ser na URL) (o corpo é como se fosse uma caixinha com os dados dentro) e geralmente é em JSON
   public ResponseEntity<List<UsuarioModel>> listar() {//método retorna uma lista
    	
    	List<UsuarioModel> usuarios = usuarioRepository.findAll();//executa e consulta no banco de dados
    	//1: O método findAll() é chamado.
    	//2: O Spring Data JPA gera queries SQL automaticamente. - Spring Data JPA(Java Persistense API) forcene interfaces e métodos prontos para realizar operaçõse com o banco de dados 
    	//3: O banco retorna os registros.
    	//4: Cada registro é convertido em um objeto UsuarioModel.
    	//5: A lista de objetos é atribuída à variável usuarios.
    	
    	return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);//retorna a lista de objetos (isto é um fato), porém o @ResponseBody traduz para json antes de enviar no corpo da resposta
    	//lista de usuarios são o corpo da resposta (os dados); HttpStatus é o status HTTP, que é 200 (ok)
    	
    }
   
   //endpoint para salvar usuários (POST - cria novos recursos e os dados vão no corpo da requisição)
   @PostMapping(value = "salvar")
   @ResponseBody //retorna os dados no corpo da resposta (sem ser na URL) (o corpo é como se fosse uma caixinha com os dados dentro) e geralmente é em JSON
   public ResponseEntity<UsuarioModel> salvar(@RequestBody UsuarioModel usuarioModel) {//@RequestBody recebe os dados no corpo da resposta (dados geralmente vêm em JSON) e os transforma em um objeto real e passa para UsuarioModel
	   
	   UsuarioModel usuario = usuarioRepository.save(usuarioModel);//salva meus dados no banco
	   
	   return new ResponseEntity<UsuarioModel>(usuario, HttpStatus.CREATED);//retorna um objeto (isto é um fato), porém o @ResponseBody traduz para json antes de enviar no corpo da resposta
	 //usuario é  o corpo da resposta; HttpStatus é o status HTTP, que é 201 (created)
	   
   }
   
   //endpoint para deletar usuários (DELETE - remove um recurso existente)
   @DeleteMapping(value = "deletar")
   @ResponseBody//retorna o corpo da resposta, (o corpo é como se fosse uma caixinha com os dados dentro) e geralmente é em JSON
   public ResponseEntity<String> deletar(@RequestParam Long id) {//@RequestParam recebe parâmetros que vêm da URL, ou de formulários no corpo de requisição
	   
	   usuarioRepository.deleteById(id);//deleta meu usuário no banco, através do ID
	   
	   return new ResponseEntity<String>("Usuário deletado com sucesso!", HttpStatus.OK);//retorna um texto (isto é um fato), porém o @ResponseBody não traduz para json, visto que trata-se de um texto
	   //String  é  o corpo da resposta; HttpStatus é o status HTTP, que é 200 (ok)
	   
	}
   
   //endpoint para listar usuários pelo id (GET - faz buscas)
   @GetMapping(value = "buscarPeloId")
   @ResponseBody//retorna o corpo da resposta, (o corpo é como se fosse uma caixinha com os dados dentro) e geralmente é em JSON
   public ResponseEntity<UsuarioModel> buscarPeloId(@RequestParam(name = "id") Long id) {//@RequestParam recebe parâmetros que vêm na URL ou formulários que vêm no corpo de requisição
	   //(name = "id") é para reforçar a informação
	   /*O @RequestBody poderia ser usado neste caso, mas eu teria de substituir "Long id" por um objeto, e a partir deste onjeto resgatar o id - isto se dá pois */ 
	   
	   UsuarioModel usuarioModel = usuarioRepository.findById(id).get();//busca usuário pelo ID
	   
	   return new ResponseEntity<UsuarioModel>(usuarioModel, HttpStatus.OK);//retorna mue objeto no formato json, mais o status HTTP 200 (ok)
	   //usuario é  o corpo da resposta; HttpStatus é o status HTTP, que é 200 (ok)
     
	} 
   
   //endpoint para atualizar dados do usuário (PUT - atualiza um recurso existente)
   //este endpoint está praticamente obsoleto, pois não há nenhuma chamada ajax para ele 
   @PutMapping(value = "atualizar")
   @ResponseBody//retorna o corpo da resposta, (o corpo é como se fosse uma caixinha com os dados dentro) e geralmente é em JSON
   public ResponseEntity<?> atualizar(@RequestBody UsuarioModel usuarioModel) {//a ? me permite retornar qualquer coisa (fiz isso pois precisei retornar uma String, depois um objeto)
	   
	   if(usuarioModel.getId() == null) {//verificando se o ID está nulo ou não
		   return new ResponseEntity<String>("O id não foi informado", HttpStatus.BAD_REQUEST);//retorno um texto de alerta, mais o status http status 400
	   }
	   
	   if (!usuarioRepository.existsById(usuarioModel.getId())) {//verifica se o usuário não existe no banco de dados - isto se dá porque este endpoint deve atualizar dados de usuários existentes, e não salvar novos usuários
	        return new ResponseEntity<>("Você está tentando salvar um novo usuário!", HttpStatus.NOT_FOUND);
	    }
	   
	   UsuarioModel usuario = usuarioRepository.saveAndFlush(usuarioModel);//saveAndFlush atualiza e já salva 
	   
	   return new ResponseEntity<UsuarioModel>(usuario, HttpStatus.OK);//retorna um objeto (isto é um fato), porém o @ResponseBody traduz para json antes de enviar no corpo da resposta
	 //usuario é o corpo da resposta; HttpStatus é o status HTTP, que 200 (ok)
	   
   }
   
  //endpoint para listar usuários pelo nome (GET - faz buscas)
   @GetMapping(value = "buscarPeloNome")
   @ResponseBody//retorna o corpo da resposta, (o corpo é como se fosse uma caixinha com os dados dentro) e geralmente é em JSON
   public ResponseEntity<List<UsuarioModel>> buscarPeloNome(@RequestParam(name = "nome") String nome) {//@RequestParam recebe parâmetros que vêm na URL ou formulários que vêm no corpo de requisição
	   //(name = "id") é para reforçar a informação
	   
	    List<UsuarioModel> usuarioModel = usuarioRepository.buscarPorNome(nome.trim().toUpperCase());//busca usuário pelo nome - trim() retira o espaço
	   
	   return new ResponseEntity<List<UsuarioModel>>(usuarioModel, HttpStatus.OK);//retorna uma lista de objetos (isto é um fato), porém o @ResponseBody traduz para json antes de enviar no corpo da resposta
	   //lista de usuarios é o corpo da resposta; HttpStatus é o status HTTP, que é 200 (ok)
	} 
	
  
}