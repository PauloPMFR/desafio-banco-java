package com.example.restservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Correntista implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String contaCorrente;
    private String senha;
    private String nome;
    private String perfil;
    private double saldo;
    private List<String> extrato;
    private LocalDateTime entradaChequeEspecial;

    public Correntista(String contaCorrente, String senha, String nome, String perfil) {
        this.contaCorrente = contaCorrente;
        this.senha = senha;
        this.nome = nome;
        this.perfil = perfil;
        this.saldo = 0.0;
        this.extrato = new ArrayList<>();
        this.entradaChequeEspecial=LocalDateTime.now();
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public String getSenha() {
        return senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public String getNome() {
        return nome;
    }

    private void setNome(String nome) {
        this.nome = nome;
    }

    public void setPerfil(String perfil) {

    }

    private double getSaldo() {
        return saldo;
    }

    private void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    private List<String> getExtrato() {
        return extrato;
    }

    private void adicionarMovimentacao(String movimentacao) {
        extrato.add(movimentacao);
    }

    private double getSaldoArredondado() {
        double saldoRedondo = Math.round(getSaldo() * 100.0) / 100.0;
        return saldoRedondo;
    }

    public void verSaldo() {
        System.out.println("Saldo atual: R$" + getSaldoArredondado());
        if (getPerfil().equals("VIP") && getSaldo() < 0) {
            double taxa = getSaldo() * 0.001;
            System.out.println("Redução por minuto no cheque especial: R$" + taxa);

        }
    }



    public void exibirExtrato() {
        System.out.println("Extrato:");
        for (String movimentacao : getExtrato()) {
            System.out.println(movimentacao);

        }
    }

    public void realizarSaque(Scanner scanner) {
        System.out.print("Valor do saque: R$");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        if(getPerfil().equals("Normal")) {
            if(getSaldo()< valor) {
                System.out.println("Saldo insuficiente.");
            }
            else {
                setSaldo(getSaldo() - valor);
                adicionarMovimentacao(formatarMovimentacao("Saque",- valor));
                System.out.println("Saque realizado com sucesso.");
            }

        }

        if (getPerfil().equals("VIP")) {
            if (getSaldo() < 0) {
                LocalDateTime agora = LocalDateTime.now();
                double minutosNoChequeEspecial = entradaChequeEspecial.until(agora, ChronoUnit.MINUTES);
                double reducaoPorMinuto = getSaldo() * 0.001; // 0.1% do saldo negativo

                setSaldo(getSaldo() + reducaoPorMinuto);
                adicionarMovimentacao(formatarMovimentacao("Cheque Especial", reducaoPorMinuto));
                System.out.println("Saldo negativo reduzido em " + reducaoPorMinuto + ". Tempo no cheque especial: " + minutosNoChequeEspecial + " minutos.");



            } else {
                setSaldo(getSaldo() - valor);
                adicionarMovimentacao(formatarMovimentacao("Saque", -valor));
                System.out.println("Saque realizado com sucesso.");
            }

        }


    }

    public void realizarDeposito(Scanner scanner) {
        System.out.print("Valor do depósito: R$");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        setSaldo(getSaldo() + valor);
        adicionarMovimentacao(formatarMovimentacao("Depósito", valor));
        System.out.println("Depósito realizado com sucesso.");
    }

    public void realizarTransferencia(ArrayList<Correntista> correntistas, Scanner scanner) {
        System.out.print("Valor da transferência: R$");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        if(valor<0) {
            System.out.println("Digite um valor positivo");
            return;
        }

        if (getPerfil().equals("Normal") && valor > 1000.0) {
            System.out.println("Transferência não permitida. Limite máximo para transferência é de R$1000,00.");
            return;
        }
        if (getPerfil().equals("Normal") && valor+8 > getSaldo()) {
            System.out.println("Transferência não permitida.Saldo insuficiente");
            return;
        }
        if (getPerfil().equals("VIP") && valor+valor*0.008 > getSaldo()) {
            System.out.println("Transferência não permitida.Saldo insuficiente");
            return;
        }

        System.out.print("Conta corrente do destinatário: ");
        String contaDestinatario = scanner.nextLine();

        if (contaDestinatario.equals(getContaCorrente())) {
            System.out.println("Não é possível transferir para a mesma conta corrente.");
            return;
        }

        Correntista destinatario = null;
        for (Correntista correntista : correntistas) {
            if (correntista.getContaCorrente().equals(contaDestinatario)) {
                destinatario = correntista;
                break;
            }
        }

        if (destinatario == null) {
            System.out.println("Conta corrente do destinatário não encontrada.");
            return;
        }

        if (getPerfil().equals("Normal")) {

            setSaldo(getSaldo() - (valor + 8.0));
            adicionarMovimentacao(formatarMovimentacao("Taxa de transferência", -8.0));
            adicionarMovimentacao(formatarMovimentacao("Transferência para " + contaDestinatario, -valor));

            destinatario.setSaldo(destinatario.getSaldo() + valor);
            destinatario.adicionarMovimentacao(formatarMovimentacao("Transferência de " + getContaCorrente(), valor));
        } else if (getPerfil().equals("VIP")) {
            double taxa = valor * 0.008;
            setSaldo(getSaldo() - taxa);
            adicionarMovimentacao(formatarMovimentacao("Taxa de transferência", -taxa));

            setSaldo(getSaldo() - valor);
            destinatario.setSaldo(destinatario.getSaldo() + valor);
            adicionarMovimentacao(formatarMovimentacao("Transferência para " + contaDestinatario, -valor));
            destinatario.adicionarMovimentacao(formatarMovimentacao("Transferência de " + getContaCorrente(), valor));
            //resetarTempoChequeEspecial();
        }

        System.out.println("Transferência realizada com sucesso.");
    }

    public void solicitarVisitaGerente(Scanner scanner) {
        System.out.println("Deseja solicitar a visita do gerente? (S/N)");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("S")) {
            setSaldo(getSaldo() - 50.0);
            adicionarMovimentacao(formatarMovimentacao("Visita do gerente", -50.0));

            System.out.println("Solicitação de visita do gerente realizada com sucesso.");
        }
    }

    private void entrarChequeEspecial() {
        entradaChequeEspecial = LocalDateTime.now();
        double taxa = getSaldo() * 0.001;
        setSaldo(getSaldo() + taxa);


    }

    private String formatarMovimentacao(String descricao, double valor) {
        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dataHora.format(formatoDataHora) + " - " + descricao + (valor < 0 ? " (" + valor + ")" : " +" + valor);

    }

}



