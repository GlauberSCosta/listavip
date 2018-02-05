package br.com.glaubscosta.listavips;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.glaubscosta.emailSender.EmailService;
import br.com.glaubscosta.listavips.model.Convidado;
import br.com.glaubscosta.listavips.repository.ConvidadoRepository;
import br.com.glaubscosta.listavips.service.ConvidadoService;

@Controller
public class ConvidadoController {

	@Autowired
	private ConvidadoRepository convidadoRepository;
	
	@Autowired
	private ConvidadoService convidadoService;

	@RequestMapping("/")
	public String index() {
		System.out.println("teste");
		return "index";
	}

	@RequestMapping("listaconvidados")
	public String listaConvidados(Model model) {
		Iterable<Convidado> convidados = convidadoService.obterTodos();
		model.addAttribute("convidados", convidados);
		return "listaconvidados";
	}

	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	public String salvar(@RequestParam("nome") String nome, @RequestParam("email") String email,
			@RequestParam("telefone") String telefone, Model model) {

		Convidado convidado = new Convidado(nome, email, telefone);
		convidadoService.salvar(convidado);
		
		EmailService enviaEmail = new EmailService();
		enviaEmail.enviar(nome, email);
		
		Iterable<Convidado> listaContatos = convidadoService.obterTodos();
		model.addAttribute("convidados", listaContatos);

		return "listaconvidados";
	}

	@RequestMapping(value = "deletar", method = RequestMethod.GET)
	public String deletar(@RequestParam("id") Long id, Model model) {
		convidadoRepository.delete(id);
		Iterable<Convidado> listaConvidados = convidadoService.obterTodos();
		model.addAttribute("convidados", listaConvidados);
		return "listaconvidados";
	}

}
