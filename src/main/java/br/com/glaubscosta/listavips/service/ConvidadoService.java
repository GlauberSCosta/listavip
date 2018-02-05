package br.com.glaubscosta.listavips.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.glaubscosta.listavips.model.Convidado;
import br.com.glaubscosta.listavips.repository.ConvidadoRepository;

@Service
public class ConvidadoService {

	@Autowired
	private ConvidadoRepository convidadoRepository;
	
	
	public Iterable<Convidado> obterTodos() {
		Iterable<Convidado> convidados = convidadoRepository.findAll();
		return convidados;
	}
	
	public void salvar(Convidado convidado) {
		convidadoRepository.save(convidado);
	}
}
