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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.ev.sistema.BancoDeDados;
import br.com.ev.sistema.entidades.Cargo;

public class CargosEditar  extends JPanel {

	private static final long serialVersionUID = 1L;

	Cargo cargoAtual;
	JLabel labelTitulo, labelCargo;
	JTextField campoCargo;
	JButton botaoGravar;

	public CargosEditar(Cargo cargo) {
		cargoAtual = cargo;
		criarComponentes();
		criarEventos();
	}

	private void criarComponentes() {
		setLayout(null);

		labelTitulo = new JLabel("Editar o Cargo: "+cargoAtual.getNome(),JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(),Font.PLAIN,20));
		labelCargo = new JLabel("Nome do cargo: ", JLabel.LEFT);
		campoCargo = new JTextField();
		botaoGravar = new JButton("Salvar");

		labelTitulo.setBounds(20, 20, 660, 40);
		labelCargo.setBounds(150, 100, 400, 40);
		campoCargo.setBounds(150, 140, 400, 40);
		botaoGravar.setBounds(250, 380, 200, 40);

		add(labelTitulo);
		add(labelCargo);
		add(campoCargo);
		add(botaoGravar);

		setVisible(true);

	}

	private  void criarEventos() {

		botaoGravar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cargoAtual.setNome(campoCargo.getText());
				sqlAtualizarCargo();
			}
		});
	}

	private void sqlAtualizarCargo() {
		if(campoCargo.getText().length() <= 3) {
			JOptionPane.showMessageDialog(null, "Por favor inserir o nome correto");
		}else {

			Connection conexao;
			Statement instrucaoSQL;

			String sql = "UPDATE cargos SET nome='"+campoCargo.getText()+"' WHERE id ="+cargoAtual.getId()+";";

			try {
				conexao = DriverManager.getConnection(BancoDeDados.servidor, BancoDeDados.usuario, BancoDeDados.senha);
				instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				instrucaoSQL.executeUpdate(sql);
				JOptionPane.showMessageDialog(null,"Cargo Atualizado com Sucesso!");

			} catch (SQLException e) {

				JOptionPane.showMessageDialog(null,"Ocorreu um erro ao atualizar o Cargo");
				Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
			}

		}
	}
}
