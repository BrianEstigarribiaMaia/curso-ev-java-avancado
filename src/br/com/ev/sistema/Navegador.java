package br.com.ev.sistema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import br.com.ev.sistema.entidades.Cargo;
import br.com.ev.sistema.entidades.Funcionario;
import br.com.ev.sistema.telas.CargosConsultar;
import br.com.ev.sistema.telas.CargosEditar;
import br.com.ev.sistema.telas.CargosInserir;
import br.com.ev.sistema.telas.FuncionariosConsultar;
import br.com.ev.sistema.telas.FuncionariosEditar;
import br.com.ev.sistema.telas.FuncionariosInserir;
import br.com.ev.sistema.telas.Inicio;
import br.com.ev.sistema.telas.Login;
import br.com.ev.sistema.telas.RelatoriosCargos;
import br.com.ev.sistema.telas.RelatoriosSalarios;

public class Navegador {

	private static boolean menuConstruido;
	private static boolean menuHabilitado;
	private static JMenuBar menuBar;
	private static JMenu menuArquivo, menuFuncionario, menuCargos, menuRelatorios;
	private static JMenuItem miSair, miFuncionariosConsultar, miFuncionariosCadastrar, miCargosConsultar;
	private static JMenuItem miCargosCadastrar, miRelatoriosCargos, miRelatoriosSalarios;

	public static void login() {
		SistemaFuncionario.tela = new Login(); 
		SistemaFuncionario.frame.setTitle("Funcionarios Company SA");
		Navegador.atualizarTela();
	}
	
	public static void inicio() {
		SistemaFuncionario.tela = new Inicio();
		SistemaFuncionario.frame.setTitle("Funcionarios Company SA");
		Navegador.atualizarTela();
	}
	
	public static void funcionarioCadastrar() {
		SistemaFuncionario.tela = new FuncionariosInserir();
		SistemaFuncionario.frame.setTitle("Funcionarios Company SA - Cadastrar Funcionario");
		Navegador.atualizarTela();
	}
	
	public static void funcionarioConsultar() {
		SistemaFuncionario.tela = new FuncionariosConsultar();
		SistemaFuncionario.frame.setTitle("Funcionarios Company SA - Consultar Funcionario");
		Navegador.atualizarTela();
	}
	
	public static void funcionarioEditar(Funcionario funcionario) {
		SistemaFuncionario.tela = new FuncionariosEditar(funcionario);
		SistemaFuncionario.frame.setTitle("Funcionarios Company SA - Editar Funcionario");
		Navegador.atualizarTela();
	}
	
	public static void cargoCadastrar() {
		SistemaFuncionario.tela = new CargosInserir();
		SistemaFuncionario.frame.setTitle("Funcionarios Company SA - Cadastrar Cargo");
		Navegador.atualizarTela();
	}
	
	public static void cargoConsultar() {
		SistemaFuncionario.tela = new CargosConsultar();
		SistemaFuncionario.frame.setTitle("Funcionarios Company SA - Consultar Cargo");
		Navegador.atualizarTela();
	}
	
	public static void cargoEditar(Cargo cargo) {
		SistemaFuncionario.tela = new CargosEditar(cargo);
		SistemaFuncionario.frame.setTitle("Funcionarios Company SA - Editar Consultar");
		Navegador.atualizarTela();
	}
	
	public static void RelatorioCargosCriar() {
		SistemaFuncionario.tela = new RelatoriosCargos();
		SistemaFuncionario.frame.setTitle("Relatorios por Cargos");
		Navegador.atualizarTela();
	}
	
	public static void RelatorioSalariosCriar() {
		SistemaFuncionario.tela = new RelatoriosSalarios();
		SistemaFuncionario.frame.setTitle("Relatorios por Cargos");
		Navegador.atualizarTela();
	}
	
	public static void atualizarTela() {
		SistemaFuncionario.frame.getContentPane().removeAll();
		SistemaFuncionario.tela.setVisible(true);
		SistemaFuncionario.frame.add(SistemaFuncionario.tela);
		SistemaFuncionario.frame.setVisible(true);
	}
	
	private static void construirMenu() {
		if(!menuConstruido) {
			menuConstruido = true;
			
			menuBar	= new JMenuBar();
					
			menuArquivo = new JMenu("Arquivo");
			menuBar.add(menuArquivo);
			miSair = new JMenuItem("Sair");
			menuArquivo.add(miSair);
			
			menuFuncionario = new JMenu("Funcionarios");
			menuBar.add(menuFuncionario);
			
			miFuncionariosCadastrar = new JMenuItem("Cadastrar");
			menuFuncionario.add(miFuncionariosCadastrar);
			
			miFuncionariosConsultar = new JMenuItem("Consultar");
			menuFuncionario.add(miFuncionariosConsultar);
			
			menuCargos = new JMenu("Cargo");
			menuBar.add(menuCargos);
			
			miCargosCadastrar = new JMenuItem("Cadastrar");
			menuCargos.add(miCargosCadastrar);
			
			miCargosConsultar = new JMenuItem("Consultar");
			menuCargos.add(miCargosConsultar);
			
			menuRelatorios = new JMenu("Relatorio");
			menuBar.add(menuRelatorios);
			
			miRelatoriosCargos = new JMenuItem("Funcionarios por Cargo");
			menuRelatorios.add(miRelatoriosCargos);
			
			miRelatoriosSalarios = new JMenuItem("Salario dos Funcionarios");
			menuRelatorios.add(miRelatoriosSalarios);
			
			criarEventosMenu();
		}
	}
	
	public static void habilitaMenu() {
		if(!menuConstruido)
			construirMenu();
		if(!menuHabilitado) {
			menuHabilitado = true;
			SistemaFuncionario.frame.setJMenuBar(menuBar);
		}
	}
	
	public static void desabilitaMenu() {
		if(!menuHabilitado) {
			menuHabilitado = false;
			SistemaFuncionario.frame.setJMenuBar(null);
		}
	}
	
	private static void criarEventosMenu() {
		miSair.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		miFuncionariosCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				funcionarioCadastrar();
			}
		});
		
		miFuncionariosConsultar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				funcionarioConsultar();
			}
		});
		
		miCargosCadastrar.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				cargoCadastrar();
			}
		});
		
		miCargosConsultar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cargoConsultar();
			}
		});
		
		miRelatoriosCargos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RelatorioCargosCriar();
			}
		});
		
		miRelatoriosSalarios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RelatorioSalariosCriar();
			}
		});
	}
}
