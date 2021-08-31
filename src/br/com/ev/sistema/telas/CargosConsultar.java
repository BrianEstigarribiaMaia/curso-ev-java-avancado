package br.com.ev.sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.ev.sistema.BancoDeDados;
import br.com.ev.sistema.Navegador;
import br.com.ev.sistema.entidades.Cargo;

public class CargosConsultar extends JPanel {

	private static final long serialVersionUID = 1L;
	Cargo  cargoAtual;
	JLabel labelTitulo, labelCargo;
	JTextField campoCargo;
	JButton botaoPesquisar, botaoEditar, botaoExcluir;
	DefaultListModel<Cargo> listaCargoModelo = new DefaultListModel<>();
	JList<Cargo> listaCargos;

	public CargosConsultar(){
		criarComponentes();
		criarEventos();
	}

	private void criarComponentes() {
		setLayout(null);

		labelTitulo =  new JLabel("Consulta de Cargo",JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
		labelCargo = new JLabel("Nome Cargo",JLabel.LEFT);
		campoCargo = new JTextField();
		botaoPesquisar = new JButton("Pesquisar Cargo");
		botaoEditar = new JButton("Editar Cargo");
		botaoEditar.setEnabled(false);
		botaoExcluir = new JButton("Excluir Cargo");
		botaoExcluir.setEnabled(false);
		listaCargoModelo = new DefaultListModel<>();
		listaCargos =  new JList<>();
		listaCargos.setModel(listaCargoModelo);
		listaCargos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);;

		labelTitulo.setBounds(20, 20, 660, 40);
		labelCargo.setBounds(150, 120, 400, 20);
		campoCargo.setBounds(150, 140, 400, 40);
		botaoPesquisar.setBounds(560, 140, 130, 40);
		listaCargos.setBounds(150, 200, 400, 240);
		botaoEditar.setBounds(560, 360, 130, 40);
		botaoExcluir.setBounds(560, 400, 130, 40);

		add(labelTitulo);
		add(labelCargo);
		add(campoCargo);
		add(botaoPesquisar);
		add(listaCargos);
		add(botaoEditar);
		add(botaoExcluir);

		setVisible(true);

	}
	
	private void criarEventos() {
		botaoPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sqlPesquisarCargo(campoCargo.getText());
			}
		});
		
		botaoEditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Navegador.cargoEditar(cargoAtual);
			}
		});
		
		botaoExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sqlDeletarCargo();
			}
		});
		
		listaCargos.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				cargoAtual = listaCargos.getSelectedValue();
				if(cargoAtual == null){
					botaoEditar.setEnabled(false);
					botaoExcluir.setEnabled(false);
				}else{
					botaoEditar.setEnabled(true);
					botaoExcluir.setEnabled(true);
				}
				
			}
		});
	}
	
	private void sqlPesquisarCargo(String nome) {
		
		Connection conexao;
		Statement instrucaoSQL;
		ResultSet resultados;
		
		try{

			conexao = DriverManager.getConnection(BancoDeDados.servidor, BancoDeDados.usuario, BancoDeDados.senha);
			
			instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			resultados = instrucaoSQL.executeQuery("SELECT * FROM cargos WHERE nome like '%"+nome+"%'");
			
			listaCargoModelo.clear();
			
			while(resultados.next()){
				Cargo cargo = new Cargo();
				cargo.setId(resultados.getInt("id"));
				cargo.setNome(resultados.getString("nome"));
				
				listaCargoModelo.addElement(cargo);
			}
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar os cargos.");
			Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	private void sqlDeletarCargo() {

		int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o cargo"
				+cargoAtual.getNome()+"?","Excluir", JOptionPane.YES_NO_OPTION);

		if(confirmacao == JOptionPane.YES_OPTION){

			Connection conexao;
			Statement instrucaoSQL;

			try{

				conexao = DriverManager.getConnection(BancoDeDados.servidor, BancoDeDados.usuario, BancoDeDados.senha);

				instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

				instrucaoSQL.executeUpdate("DELETE FROM cargos WHERE id=" + cargoAtual.getId() + "");

				JOptionPane.showMessageDialog(null, "Cargo deletado com sucesso");

			}catch(SQLException e){
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir o cargo.");
				Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
			}

		}
	}
}
