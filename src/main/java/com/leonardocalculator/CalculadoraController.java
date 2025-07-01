package com.leonardocalculator; // Ajuste o pacote conforme o seu projeto

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import net.objecthunter.exp4j.Expression; // Importe esta classe
import net.objecthunter.exp4j.ExpressionBuilder; // Importe esta classe

@RestController
public class CalculadoraController {

    // Classe interna para representar a requisição de cálculo
    static class CalculoRequest {
        private String expressao;

        public String getExpressao() {
            return expressao;
        }

        public void setExpressao(String expressao) {
            this.expressao = expressao;
        }
    }

    // Classe interna para representar a resposta do cálculo
    static class CalculoResponse {
        private double resultado;
        private String erro; // Adiciona campo para mensagem de erro

        public CalculoResponse(double resultado) {
            this.resultado = resultado;
        }

        public CalculoResponse(String erro) {
            this.erro = erro;
            this.resultado = Double.NaN; // Usa NaN (Not a Number) para indicar erro no resultado
        }

        public double getResultado() {
            return resultado;
        }

        public String getErro() {
            return erro;
        }
    }

    // Endpoint que vai receber a expressão e calcular
    @PostMapping("/api/calcular")
    public CalculoResponse calcular(@RequestBody CalculoRequest request) {
        String expressao = request.getExpressao();
        System.out.println("Recebida expressão para cálculo: " + expressao); // Log para depuração

        if (expressao == null || expressao.trim().isEmpty()) {
            return new CalculoResponse("Expressão vazia ou nula!");
        }

        try {
            // Usa ExpressionBuilder da exp4j para construir a expressão
            Expression e = new ExpressionBuilder(expressao)
                    .build(); // Constrói a expressão

            double resultado = e.evaluate(); // Avalia a expressão

            return new CalculoResponse(resultado);

        } catch (IllegalArgumentException ex) {
            // Captura erros de sintaxe da expressão (ex: "2++2", "abc")
            System.err.println("Erro na expressão: " + expressao + " - " + ex.getMessage());
            return new CalculoResponse("Erro de sintaxe na expressão: " + ex.getMessage());
        } catch (ArithmeticException ex) {
            // Captura erros aritméticos (ex: divisão por zero)
            System.err.println("Erro aritmético: " + expressao + " - " + ex.getMessage());
            return new CalculoResponse("Erro aritmético: " + ex.getMessage());
        } catch (Exception ex) {
            // Captura qualquer outro erro inesperado
            System.err.println("Erro inesperado no cálculo: " + expressao + " - " + ex.getMessage());
            return new CalculoResponse("Erro inesperado no cálculo. Verifique a expressão.");
        }
    }
}