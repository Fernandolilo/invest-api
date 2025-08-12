package com.syp.invest;

import java.util.Scanner;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.syp.invest.entity.request.Conta;
import com.syp.invest.service.impl.ContaServiceImpl;

@SpringBootApplication
public class InvestimentosApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(InvestimentosApplication.class, args);
	}

	@Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o UUID da conta: ");
        String idInput = scanner.nextLine();

        try {
            UUID contaId = UUID.fromString(idInput);

            ContaServiceImpl service = new ContaServiceImpl();
            Conta conta = service.findConta(contaId);

            System.out.println("\n=== Conta encontrada ===");
            System.out.println(conta);
        } catch (IllegalArgumentException e) {
            System.err.println("UUID inválido. Use o formato correto.");
        } catch (Exception e) {
            System.err.println("Erro ao buscar conta: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

}
