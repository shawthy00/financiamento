package com.financiamento.dto;

import java.math.BigDecimal;

public class SimulacaoRequest {

    public BigDecimal valorInicial;
    public BigDecimal taxaJurosMensal;
    public Integer prazoMeses;  //não foi usado "int" p/ caso não preencha sistema detecta NULL e acusa erro

}

