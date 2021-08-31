package br.com.ev.sistema.telas;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.ev.sistema.Navegador;

public class Inicio extends JPanel{

	private static final long serialVersionUID = 1L;
	JLabel labelTitulo;
	
	public Inicio(){
		criarComponentes();
		criarEventos();
		Navegador.habilitaMenu();
	}

	private void criarComponentes(){
		setLayout(null);
		
		labelTitulo = new JLabel("Escolha uma opção no menu superior", JLabel.CENTER);
		labelTitulo.setBounds(20, 100, 660, 40);
		add(labelTitulo);
		
		setVisible(true);
		
	}

	private void criarEventos() {
		
	}
}
