package teste;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Principal {
	public static void main(String[] args) {
		String servidor = "jdbc:mysql://localhost:3306/loja_de_automoveis?useTimezone=true&serverTimezone=UTC";
		String usuario = "root";
		String senha = "";

		Connection conexao;
		Statement instrucaoSQL;
		ResultSet resultado;

		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection(servidor, usuario, senha);
			instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultado = instrucaoSQL.executeQuery("select * from carros");

			while(resultado.next()){

				String nome = resultado.getString("nome");
				String modelo = resultado.getString("modelo");
				String motor = resultado.getString("motor");
				String fabricante = resultado.getString("fabricante");
				int anoFabricacao = resultado.getInt("anoFabricacao");
				int anoModelo = resultado.getInt("anoModelo");
				String cor = resultado.getString("cor");
				int id = resultado.getInt("id");

				System.out.println("MOSTRAR BANCO DE DADOS: ");
				System.out.println("ID: "+id);
				System.out.println("NOME: "+nome);
				System.out.println("MODELO: "+modelo);
				System.out.println("MOTOR: "+motor);
				System.out.println("FABRICANTE: "+fabricante);
				System.out.println("ANO DE FABRICA��O: "+anoFabricacao);
				System.out.println("ANO DO MODELO: "+anoModelo);
				System.out.println("COR DO VEICULO: "+cor);
				System.out.println("------------------------------------------------------------------");

			}


		}catch(SQLException e){
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
