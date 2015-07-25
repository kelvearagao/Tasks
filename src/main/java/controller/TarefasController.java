package controller;

import javax.validation.Valid;

import model.Tarefa;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TarefasController {
	
	@RequestMapping("novaTarefa")
	public String form() {
		return "tarefa/formulario";
	}
	
	@RequestMapping("adicionaTarefa")
	public String adiciona(@Valid Tarefa tarefa, BindingResult result) {
		if(result.hasFieldErrors("descricao"))
			return "tarefa/formulario";
		else if(result.hasErrors())
			return "tarefa/formulario";
		
		System.out.println("Kelve -- Adicionando tarefa!");
		
		return "tarefa/tarefa-adicionada";
	}
}
