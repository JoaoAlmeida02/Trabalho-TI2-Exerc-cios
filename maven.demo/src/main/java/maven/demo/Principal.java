package maven.demo;

import java.util.Scanner;

public class Principal {
	
	public static void main(String[] args) {
		
		DAO dao = new DAO();
		Scanner scanner = new Scanner(System.in);
		
		dao.conectar();

		int opcao;
		do {
			System.out.println("==== Menu CRUD ====");
			System.out.println("1. Inserir novo usuário");
			System.out.println("2. Listar todos os usuários");
			System.out.println("3. Atualizar usuário");
			System.out.println("4. Excluir usuário");
			System.out.println("5. Sair do programa");
			System.out.print("Escolha uma opção: ");
			
			opcao = scanner.nextInt();
			scanner.nextLine(); 
			
			switch(opcao) {
				case 1:
					System.out.print("Digite o código do novo usuário: ");
					int codigo = scanner.nextInt();
					scanner.nextLine(); 
					
					System.out.print("Digite o login do novo usuário: ");
					String login = scanner.nextLine();
					
					System.out.print("Digite a senha do novo usuário: ");
					String senha = scanner.nextLine();
					
					System.out.print("Digite o sexo do novo usuário (M/F): ");
					char sexo = scanner.nextLine().charAt(0);
					
					Usuario novoUsuario = new Usuario(codigo, login, senha, sexo);
					if(dao.inserirUsuario(novoUsuario)) {
						System.out.println("Novo usuário inserido com sucesso: " + novoUsuario.toString());
					} else {
						System.out.println("Falha ao inserir novo usuário.");
					}
					break;
					
				case 2:
					System.out.println("==== Listar todos os usuários ====");
					Usuario[] usuarios = dao.getUsuarios();
					for(Usuario u : usuarios) {
						System.out.println(u.toString());
					}
					break;
					
				case 3:
					System.out.print("Digite o código do usuário a ser atualizado: ");
					int codigoAtualizar = scanner.nextInt();
					scanner.nextLine(); 
					
					System.out.print("Digite a nova senha do usuário: ");
					String novaSenha = scanner.nextLine();
					
					Usuario usuarioAtualizar = new Usuario(codigoAtualizar, null, novaSenha, '\0');
					if(dao.atualizarUsuario(usuarioAtualizar)) {
						System.out.println("Usuário atualizado com sucesso: " + usuarioAtualizar.toString());
					} else {
						System.out.println("Falha ao atualizar usuário.");
					}
					break;
					
				case 4:
					System.out.print("Digite o código do usuário a ser excluído: ");
					int codigoExcluir = scanner.nextInt();
					
					if(dao.excluirUsuario(codigoExcluir)) {
						System.out.println("Usuário com código " + codigoExcluir + " excluído com sucesso.");
					} else {
						System.out.println("Falha ao excluir usuário com código " + codigoExcluir + ".");
					}
					break;
					
				case 5:
					System.out.println("Saindo do programa...");
					break;
					
				default:
					System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
			}
			
		} while(opcao != 5);
		
		scanner.close();
		dao.close();
	}
}
