package br.com.ev.sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.ev.sistema.BancoDeDados;
import br.com.ev.sistema.Navegador;
import br.com.ev.sistema.entidades.Cargo;
import br.com.ev.sistema.entidades.Funcionario;

public class FuncionariosEditar  extends JPanel {

	private static final long serialVersionUID = 1L;
	Funcionario funcionarioAtual;

	JLabel  labelTitulo, labelNome, labelSobrenome, labelDataNascimento, labelEmail, labelCargo, 
	labelSalario, labemEmail;
	JTextField campoNome,campoEmail,campoSobrenome;
	JFormattedTextField campoDataNascimento;
	JComboBox<Cargo> comboboxCargo;
	JFormattedTextField campoSalario;
	JButton botaoGravar; 

	public FuncionariosEditar(Funcionario funcionario) {
		funcionarioAtual = funcionario;
		criarComponentes();
		criarEventos();
	}

	public void criarComponentes() {

		setLayout(null);

		String textoLabel = "Editar Funcionario: "+funcionarioAtual.getNome()+" "+funcionarioAtual.getSobrenome();
		labelTitulo = new JLabel(textoLabel,JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(),Font.PLAIN,20));

		labelNome = new JLabel("Nome: ",JLabel.LEFT);
		campoNome = new JTextField(funcionarioAtual.getNome());
		labelSobrenome = new JLabel("Sobrenome",JLabel.LEFT);
		campoSobrenome = new JTextField(funcionarioAtual.getSobrenome());

		labelDataNascimento = new JLabel("Data de Nascimento",JLabel.LEFT); 
		campoDataNascimento = new JFormattedTextField(funcionarioAtual.getDataDeNascimento());

		try {
			MaskFormatter dateMask = new MaskFormatter("##/##/####");
			dateMask.install(campoDataNascimento);

		}catch(ParseException e) {
			Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE,null,e);
		}

		labelEmail = new JLabel("E-mail", JLabel.LEFT);
		campoEmail = new JTextField(funcionarioAtual.getEmail());

		labelCargo = new JLabel("Cargo",JLabel.LEFT);
		comboboxCargo = new JComboBox<Cargo>();

		labelSalario = new JLabel("Salario", JLabel.LEFT);

		DecimalFormat formatter = new DecimalFormat("###0.00", new DecimalFormatSymbols(new Locale("pt","BR")));

		campoSalario = new JFormattedTextField(formatter);
		campoSalario.setValue(funcionarioAtual.getSalario());

		botaoGravar = new JButton("Salvar");

		labelTitulo.setBounds(20,20,660,40);

		labelNome.setBounds(150,80,400,20);
		campoNome.setBounds(150,100,400,40);

		labelSobrenome.setBounds(150,140,400,20);
		campoSobrenome.setBounds(150,160,400,40);

		labelDataNascimento.setBounds(150,200,400,20);
		campoDataNascimento.setBounds(150,220,400,40);

		labelEmail.setBounds(150,260,400, 20);
		campoEmail.setBounds(150,280,400,40);

		labelCargo.setBounds(150,320,400,20);
		comboboxCargo.setBounds(150,340,400,40);

		labelSalario.setBounds(150,380,400,20);
		campoSalario.setBounds(150,400,400,40);

		botaoGravar.setBounds(560,400,130,40);

		add(labelTitulo);
		add(labelNome);
		add(campoNome);
		add(labelSobrenome);
		add(campoSobrenome);
		add(labelDataNascimento);
		add(campoDataNascimento);
		add(labelEmail);
		add(campoEmail);
		add(labelCargo);
		add(labelCargo);
		add(comboboxCargo);
		add(labelSalario);
		add(campoSalario);
		add(botaoGravar);

		sqlCarregarCargos();

		setVisible(true);
	}

	public void sqlCarregarCargos() {
		Connection conexao;
		Statement instrucaoSQL;
		ResultSet resultados;

		String sql = "SELECT * FROM cargos ORDER BY nome ASC";

		try {
			conexao 	 = DriverManager.getConnection(BancoDeDados.servidor, BancoDeDados.usuario, BancoDeDados.senha);
			instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultados   = instrucaoSQL.executeQuery(sql);
			comboboxCargo.removeAll();

			while(resultados.next()){
				Cargo cargo = new Cargo();
				cargo.setId(resultados.getInt("id"));
				cargo.setNome(resultados.getString("nome"));
				comboboxCargo.addItem(cargo);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Ocorreu um erro ao carregar os Funcionarios");
			Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public void criarEventos() {
		botaoGravar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Funcionario novoFuncionario = new Funcionario();

				novoFuncionario.setNome(campoNome.getText());
				novoFuncionario.setSobrenome(campoSobrenome.getText());
				novoFuncionario.setDataDeNascimento(campoDataNascimento.getText());
				novoFuncionario.setEmail(campoEmail.getText());

				Cargo cargoSelecionado = (Cargo) comboboxCargo.getSelectedItem();

				if(cargoSelecionado != null) {
					novoFuncionario.setId(cargoSelecionado.getId());
				}

				novoFuncionario.setSalario(Double.valueOf(campoSalario.getText().replace(",", ".")));

				sqlAtualizarFuncionario(funcionarioAtual);
			}
		});
	}

	public void sqlAtualizarFuncionario(Funcionario funcionario) {

		if(campoNome.getText().length()<=1) {
			JOptionPane.showMessageDialog(null, "Por Favor, preencher o nome corretamente!");
			return;
		}


		if(campoSobrenome.getText().length()<=1) {
			JOptionPane.showMessageDialog(null, "Por Favor, preencher o sobrenome corretamente!");
			return;
		}

		if(Double.parseDouble(campoSalario.getText().replace(",", "."))<=100) {
			JOptionPane.showMessageDialog(null, "Por Favor, preencher o salario corretamente!");
			return;
		}

		Boolean emailValidado = false; 
		String ePattern = "^([0-9a-zA-Z]+([_.-]?[0-9a-zA-Z]+)*@[0-9a-zA-Z]+[0-9,a-z,A-Z,.,-]*(.){1}[a-zA-Z]{2,4})+$";
		Pattern p = Pattern.compile(ePattern); 
		Matcher m = p.matcher(campoEmail.getText()); 
		emailValidado = m.matches();

		if(!emailValidado) { 
			JOptionPane.showMessageDialog(null,"Por Favor, preencher o email corretamente!"); 
			return; 
		}

		Connection conexao;
		java.sql.PreparedStatement instrucaoSQL;

		String sql = "UPDATE funcionarios SET nome=?, sobrenome=?, data_de_nascimento=?, email=?, cargo=?, salario=? WHERE id="+funcionarioAtual.getId();

		try {
			conexao = DriverManager.getConnection(BancoDeDados.servidor, BancoDeDados.usuario, BancoDeDados.senha);
			instrucaoSQL = conexao.prepareStatement(sql);

			instrucaoSQL.setString(1, campoNome.getText());
			instrucaoSQL.setString(2, campoSobrenome.getText());
			instrucaoSQL.setString(3, campoDataNascimento.getText());
			instrucaoSQL.setString(4, campoEmail.getText());

			Cargo cargoSelecionado = (Cargo) comboboxCargo.getSelectedItem();
			if(cargoSelecionado != null) {
				instrucaoSQL.setInt(5, cargoSelecionado.getId());
			}else {
				instrucaoSQL.setNull(5, java.sql.Types.INTEGER);
			}

			instrucaoSQL.setString(6, campoSalario.getText().replace(",", "."));

			instrucaoSQL.executeUpdate();

			JOptionPane.showMessageDialog(null,"Funcionario Atualizado com Sucesso!");

			Navegador.inicio();
			conexao.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Ocorreu um erro ao adicionar o Funcionario");
			Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, e);
		}

	}
}
