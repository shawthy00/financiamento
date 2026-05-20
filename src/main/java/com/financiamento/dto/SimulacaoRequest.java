package com.financiamento.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SimulacaoRequest {

    @NotNull(message = "Obrigatório valor inicial.")
    @DecimalMin(value = "0.01", message = "Aporte inicial deve ser maior que zero.")
    public BigDecimal valorInicial;

    @NotNull(message = "Obrigatório declarar a taxa de juros.")
    @DecimalMin(value = "0.01", message = "O juro tem que ser maior que zero.")
    public BigDecimal taxaJurosMensal;

    @NotNull(message = "Prazo em meses é obrigatório.")
    @Min(value = 1, message = "Prazo minimo de 1 mÊs.")
    public Integer prazoMeses;  //não foi usado "int" p/ caso não preencha sistema detecta NULL e acusa erro

}

