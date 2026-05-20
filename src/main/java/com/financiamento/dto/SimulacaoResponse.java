package com.financiamento.dto;

import com.financiamento.entity.ParcelaMemoria;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SimulacaoResponse {

    public Long id;
    public BigDecimal valorInicial;
    public BigDecimal taxaJurosMensal;
    public Integer prazoMeses;
    public BigDecimal valorTotalFinal;
    public BigDecimal valorTotalJuros;
    public LocalDateTime criadoEm;
    public List<ParcelaMemoriaResponse> memoriaCalculo;

}
