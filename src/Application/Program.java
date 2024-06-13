package Application;

import Entities.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // 3.1 - Inserir todos os funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.parse("18/10/2000", formatter), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.parse("12/05/1990", formatter), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.parse("02/05/1961", formatter), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.parse("14/10/1988", formatter), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.parse("05/01/1995", formatter), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.parse("19/11/1999", formatter), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.parse("31/03/1993", formatter), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.parse("08/07/1994", formatter), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.parse("24/05/2003", formatter), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.parse("02/09/1996", formatter), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 - Remover o funcionário “João” da lista
        funcionarios.removeIf(func -> func.getNome().equals("João"));

        // 3.3 - Imprimir todos os funcionários
        System.out.println("Lista de funcionários:");
        for (Funcionario func : funcionarios) {
            System.out.printf("Nome: %s, Data de Nascimento: %s, Salário: %s, Função: %s%n",
                    func.getNome(),
                    func.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    formatarSalario(func.getSalario()),
                    func.getFuncao()
            );
        }

        // 3.4 - Aumentar salário em 10%
        for (Funcionario func : funcionarios) {
            BigDecimal aumento = func.getSalario().multiply(new BigDecimal("0.10"));
            func.setSalario(func.getSalario().add(aumento));
        }

        // 3.5 - Agrupar funcionários por função
        Map<String, List<Funcionario>> porFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 - Imprimir funcionários agrupados por função
        System.out.println("\nFuncionários agrupados por função:");
        for (Map.Entry<String, List<Funcionario>> entry : porFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (Funcionario func : entry.getValue()) {
                System.out.printf("  Nome: %s, Data de Nascimento: %s, Salário: %s%n",
                        func.getNome(),
                        func.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        formatarSalario(func.getSalario())
                );
            }
        }

        // 3.8 - Imprimir funcionários que fazem aniversário nos meses 10 e 12
        System.out.println("\nFuncionários que fazem aniversário nos meses 10 e 12:");
        for (Funcionario func : funcionarios) {
            int mes = func.getDataNascimento().getMonthValue();
            if (mes == 10 || mes == 12) {
                System.out.printf("Nome: %s, Data de Nascimento: %s%n",
                        func.getNome(),
                        func.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                );
            }
        }

        // 3.9 - Imprimir o funcionário com a maior idade
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);

        if (maisVelho != null) {
            int idade = LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear();
            System.out.printf("\nFuncionário mais velho: Nome: %s, Idade: %d%n", maisVelho.getNome(), idade);
        }

        // 3.10 - Imprimir lista de funcionários por ordem alfabética
        System.out.println("\nLista de funcionários por ordem alfabética:");
        List<Funcionario> ordenados = new ArrayList<>(funcionarios);
        ordenados.sort(Comparator.comparing(Funcionario::getNome));
        for (Funcionario func : ordenados) {
            System.out.printf("Nome: %s, Data de Nascimento: %s, Salário: %s, Função: %s%n",
                    func.getNome(),
                    func.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    formatarSalario(func.getSalario()),
                    func.getFuncao()
            );
        }

        // 3.11 - Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("\nTotal dos salários: %s%n", formatarSalario(totalSalarios));

        // 3.12 - Imprimir quantos salários mínimos cada funcionário ganha
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nQuantos salários mínimos cada funcionário ganha:");
        for (Funcionario func : funcionarios) {
            BigDecimal quantidadeSalariosMinimos = func.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_EVEN);
            System.out.printf("Nome: %s, Salários Mínimos: %s%n",
                    func.getNome(),
                    quantidadeSalariosMinimos.toString().replace('.', ',')
            );
        }
    }

    private static String formatarSalario(BigDecimal salario) {
        return String.format("%,.2f", salario).replace(',', 'X').replace('.', ',').replace('X', '.');
    }
}
