package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import model.Tarefa;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("session")
public class TarefasController {
	private List<Tarefa> tarefas;
	
	public TarefasController() {
		System.out.println("Controller");
		tarefas = new ArrayList<Tarefa>();
		
		Tarefa t1 = new Tarefa();
		t1.setId(new Long(1));
		t1.setDescricao("Estudar direito previdenciário");
		
		Tarefa t2 = new Tarefa();
		
		t2.setId(new Long(2));
		t2.setDescricao("Estudar direito administrativo");
		t2.setFinalizado(true);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date;
		try {
			date = sdf.parse("2015-09-28");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			t2.setDataFinalizacao(c);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		tarefas.add(t1);
		tarefas.add(t2);
	}
	
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
		
		tarefa.setId((long) this.tarefas.size() + 1);
		
		this.tarefas.add(tarefa);
		
		return "tarefa/tarefa-adicionada";
	}
	
	@RequestMapping("listaTarefas")
	public String lista(Model model) {
		model.addAttribute("tarefas", this.tarefas);
		
		return "tarefa/lista";
	}
	
	@RequestMapping("removeTarefa")
	public String remove(Tarefa tarefa) {
		this.tarefas.remove(getTarefaById(tarefa.getId()));
		
		return "redirect:listaTarefas";
	}
	
	@RequestMapping("mostraTarefa")
	public String mostra(Long id, Model model) {
		Tarefa tarefa = getTarefaById(Long.parseLong(id.toString()));
		
		model.addAttribute("tarefa", tarefa);
		
		return "tarefa/mostra";
	}
	
	@RequestMapping("alteraTarefa")
	public String altera(Tarefa tarefa) {
		this.tarefas.remove(getTarefaById(tarefa.getId()));
		this.tarefas.add(tarefa);
		
		return "redirect:listaTarefas";
	}
	
	public Tarefa getTarefaById(Long id) {
		Tarefa tarefa = new Tarefa();
		
		for (Tarefa t : this.tarefas) 
		{
			if(t.getId() == Long.parseLong(id.toString()) )
			{
				tarefa = t;
				break;
			}
		}
		
		return tarefa;
	}
}
