package com.ti2cc;
import java.util.Scanner;

public class Principal {
    
    public static void main(String[] args) {
        
        DAO dao = new DAO();
        Scanner scanner = new Scanner(System.in);
        
        dao.conectar();

        int opcao;
        do {
            System.out.println("==== Menu CRUD ====");
            System.out.println("1. Inserir novo aluno");
            System.out.println("2. Listar todos os alunos");
            System.out.println("3. Atualizar aluno");
            System.out.println("4. Excluir aluno");
            System.out.println("5. Sair do programa");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); 
            
            switch(opcao) {
                case 1:
                    System.out.print("Digite a matrícula do novo aluno: ");
                    int matricula = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    System.out.print("Digite a nota do novo aluno: ");
                    int nota = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    System.out.print("Digite a matéria favorita do novo aluno: ");
                    String materiafav = scanner.nextLine();
                    
                    System.out.print("Digite o sexo do novo aluno (M/F): ");
                    char sexo = scanner.nextLine().charAt(0);
                    
                    alunos novoAluno = new alunos(matricula, nota, materiafav, sexo);
                    if(dao.inserirAluno(novoAluno)) {
                        System.out.println("Novo aluno inserido com sucesso: " + novoAluno.toString());
                    } else {
                        System.out.println("Falha ao inserir novo aluno.");
                    }
                    break;
                    
                case 2:
                    System.out.println("==== Listar todos os alunos ====");
                    alunos[] alunos = dao.getAlunos();
                    if (alunos != null) {
                        for(alunos a : alunos) {
                            System.out.println(a.toString());
                        }
                    } else {
                        System.out.println("Nenhum aluno encontrado.");
                    }
                    break;
                    
                case 3:
                    System.out.print("Digite a matrícula do aluno a ser atualizado: ");
                    int matriculaAtualizar = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    System.out.print("Digite a nova nota do aluno: ");
                    int novaNota = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    System.out.print("Digite a nova matéria favorita do aluno: ");
                    String novaMateriafav = scanner.nextLine();
                    
                    alunos alunoAtualizar = new alunos(matriculaAtualizar, novaNota, novaMateriafav, '\0');
                    if(dao.atualizarAluno(alunoAtualizar)) {
                        System.out.println("Aluno atualizado com sucesso: " + alunoAtualizar.toString());
                    } else {
                        System.out.println("Falha ao atualizar aluno.");
                    }
                    break;
                    
                case 4:
                    System.out.print("Digite a matrícula do aluno a ser excluído: ");
                    int matriculaExcluir = scanner.nextInt();
                    
                    if(dao.excluirAluno(matriculaExcluir)) {
                        System.out.println("Aluno com matrícula " + matriculaExcluir + " excluído com sucesso.");
                    } else {
                        System.out.println("Falha ao excluir aluno com matrícula " + matriculaExcluir + ".");
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
