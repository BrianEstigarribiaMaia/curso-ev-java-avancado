package br.com.ev.sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import br.com.ev.sistema.entidades.Funcionario;

public class FuncionariosConsultar  extends JPanel {

	private static final long serialVersionUID = 1L;
	Funcionario funcionarioAtual;
	JLabel labelTitulo, labelFuncionario;
	JTextField campoFuncionario;
	JButton botaoPesquisar, botaoEditar, botaoExcluir;
	
	DefaultListModel<Funcionario> listasFuncioanarioModelo = new DefaultListModel<Funcionario>();
	JList<Funcionario> listaFuncionarios;
	
	public FuncionariosConsultar(){
		criarComponentes();
		criarEventos();
	}
	
	public void criarComponentes() {
		
		setLayout(null);
		
		labelTitulo = new JLabel("Consultar Funcionarios",JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(),Font.PLAIN,20));
		
		labelFuncionario = new JLabel("Nome do Funcionario",JLabel.LEFT);
		campoFuncionario = new JTextField();
		
		botaoPesquisar = new JButton("Pesquisar Funcionario");
		
		botaoEditar = new JButton("Editar Funcionario");
		botaoEditar.setEnabled(false);
		
		botaoExcluir = new JButton("Excluir Funcionario");
		botaoExcluir.setEnabled(false);
		
		listasFuncioanarioModelo = new DefaultListModel<Funcionario>();
		listaFuncionarios = new JList<Funcionario>();
		listaFuncionarios.setModel(listasFuncioanarioModelo);
		listaFuncionarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
		labelTitulo.setBounds(20, 20, 660, 40);
		labelFuncionario.setBounds(150, 120, 400, 20);
		campoFuncionario.setBounds(150, 140, 400, 40);
		botaoPesquisar.setBounds(560, 140, 130, 40);
		listaFuncionarios.setBounds(150, 200, 400, 240);
		botaoEditar.setBounds(560, 360, 130, 40);
		botaoExcluir.setBounds(560, 400, 130, 40);
		
		add(labelTitulo);
		add(labelFuncionario);
		add(campoFuncionario);
		add(listaFuncionarios);
		add(botaoPesquisar);
		add(botaoEditar);
		add(botaoExcluir);
		
		setVisible(true);
	}
	
	public void criarEventos() {
		botaoPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				sqlPesquisarFuncionario(campoFuncionario.getText());
				
			}
		});
				
		botaoEditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Navegador.funcionarioEditar(funcionarioAtual);
			}
		});

		botaoExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				sqlDeletarFuncionario();
			}
		});

		listaFuncionarios.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				funcionarioAtual = listaFuncionarios.getSelectedValue();
			
				if(funcionarioAtual == null) {
					botaoEditar.setEnabled(false);
					botaoExcluir.setEnabled(false);
				}
				else {
					botaoEditar.setEnabled(true);
					botaoExcluir.setEnabled(true);
				}
			}
		});
				
	}
	
	private void sqlPesquisarFuncionario(String nome) {
		java.sql.Connection conexao;
		java.sql.Statement instrucaoSQL;
		ResultSet resultados;
		
		String sql = "SELECT * FROM funcionarios WHERE nome LIKE '%"+nome+"%' order by nome ASC";
		
		try {
				conexao = DriverManager.getConnection(BancoDeDados.servidor,BancoDeDados.usuario,BancoDeDados.senha);
				instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				resultados = instrucaoSQL.executeQuery(sql);
				
				listasFuncioanarioModelo.clear();
			
				while(resultados.next()) {
					Funcionario funcionario = new Funcionario();
					funcionario.setId(resultados.getInt("id"));
					funcionario.setNome(resultados.getString("nome").toString());
					funcionario.setSobrenome(resultados.getString("sobrenome").toString());
					funcionario.setDataDeNascimento(resultados.getString("data_de_nascimento"));
					funcionario.setEmail(resultados.getString("email"));
					
					if(resultados.getString("cargo") != null) {
						funcionario.setCargo(Integer.parseInt(resultados.getString("cargo")));
					}
					
					funcionario.setSalario(Double.parseDouble(resultados.getString("salario")));
					listasFuncioanarioModelo.addElement(funcionario);
				}
			
		} catch (SQLException e) {
	
			JOptionPane.showMessageDialog(null, "Ocorreu Erro ao consultar os Funcionario");
			Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE,null,e);
		}
	}
	
	private void sqlDeletarFuncionario() {
		int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente Excluir: "+funcionarioAtual.getNome()+"?"+JOptionPane.YES_NO_OPTION);
		
		if(confirmacao == JOptionPane.YES_OPTION) {
			
			java.sql.Connection conexao;
			java.sql.Statement instrucaoSQL;
			
			String sql = "DELETE FROM funcionarios WHERE id="+funcionarioAtual.getId()+";";
			
			try {
				conexao	= DriverManager.getConnection(BancoDeDados.servidor,BancoDeDados.usuario,BancoDeDados.senha);
				instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				instrucaoSQL.executeUpdate(sql);
				JOptionPane.showMessageDialog(null, "Funcionario Deletado Com Sucesso!");
			} catch (SQLException e) {
				
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao Deletar Funcionario!");
				Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, e);
			}
			
		}
	}
}
