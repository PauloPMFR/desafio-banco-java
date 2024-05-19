package com.example.restservice;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class Banco {
    private static ArrayList<Correntista> correntistas;
    private static Correntista correntistaAtual;
    private static Scanner scanner;
    private static Connection connection;

    public static void main(String[] args) {
        inicializarConexao();
        correntistas = lerBd();
        scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Bem-vindo ao Banco");
            System.out.println("1. Identificação/Login");
            System.out.println("2. Registrar novo Correntista");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 0) {
                break;
            } else if (opcao == 1) {
                realizarLogin();
            } else if (opcao == 2) {
                registrarCorrentista();
                salvar(correntistas);
            }
        }

        fecharConexao();
    }

    private static void inicializarConexao() {
        try {
            Properties props = new Properties();
            InputStream input = Banco.class.getClassLoader().getResourceAsStream("db.properties");
            props.load(input);

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void fecharConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão com o banco de dados fechada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void realizarLogin() {
        System.out.print("Conta corrente: ");
        String contaCorrente = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        for (Correntista correntista : correntistas) {
            if (correntista.getContaCorrente().equals(contaCorrente) && correntista.getSenha().equals(senha)) {
                correntistaAtual = correntista;
                exibirMenu();
                return;
            }
        }

        System.out.println("Conta corrente ou senha inválidos.");
    }

    private static void exibirMenu() {
        while (true) {
            if (correntistaAtual != null) {
                System.out.println("\nBem-vindo, " + correntistaAtual.getNome() + ":" + "Perfil de usuário, " + correntistaAtual.getPerfil());
                System.out.println("1. Ver Saldo");
                System.out.println("2. Extrato");
                System.out.println("3. Saque");
                System.out.println("4. Depósito");
                System.out.println("5. Transferência");
                if (correntistaAtual.getPerfil().equals("VIP")) {
                    System.out.println("6. Solicitar visita do gerente");
                }
                System.out.println("7. Trocar de usuário");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        correntistaAtual.verSaldo();
                        break;
                    case 2:
                        correntistaAtual.exibirExtrato();
                        break;
                    case 3:
                        correntistaAtual.realizarSaque(scanner);
                        break;
                    case 4:
                        correntistaAtual.realizarDeposito(scanner);
                        break;
                    case 5:
                        correntistaAtual.realizarTransferencia(correntistas, scanner);
                        break;
                    case 6:
                        if (correntistaAtual.getPerfil().equals("VIP")) {
                            correntistaAtual.solicitarVisitaGerente(scanner);
                        }
                        break;
                    case 7:
                        correntistaAtual = null;
                        break;
                    default:
                        break;
                }
                if (opcao == 0) {
                    break;
                }
            } else {
                break;
            }
            salvar(correntistas);
        }
    }

    private static ArrayList<Correntista> lerBd() {
        ArrayList<Correntista> correntistas = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM correntistas");

            while (rs.next()) {
                String contaCorrente = rs.getString("conta_corrente");
                String senha = rs.getString("senha");
                String nome = rs.getString("nome");
                String perfil = rs.getString("perfil");
                correntistas.add(new Correntista(contaCorrente, senha, nome, perfil));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return correntistas;
    }

    private static void salvar(ArrayList<Correntista> correntistas) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM correntistas"); // Limpa a tabela antes de inserir os novos dados

            for (Correntista c : correntistas) {
                String sql = "INSERT INTO correntistas (conta_corrente, senha, nome, perfil) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, c.getContaCorrente());
                pstmt.setString(2, c.getSenha());
                pstmt.setString(3, c.getNome());
                pstmt.setString(4, c.getPerfil());
                pstmt.executeUpdate();
                pstmt.close();
            }
            stmt.close();
            System.out.println("Os dados foram salvos com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registrarCorrentista() {
        System.out.print("Coloque seu nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite seu numero de conta: ");
        String contaCorrente = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();
        System.out.print("Digite seu perfil: ");
        String perfil = scanner.nextLine();
        correntistas.add(new Correntista(contaCorrente, senha, nome, perfil));
    }
}
