package com.financiamento.service;

import com.financiamento.dto.ParcelaMemoriaResponse;
import com.financiamento.dto.SimulacaoRequest;
import com.financiamento.dto.SimulacaoResponse;
import com.financiamento.entity.ParcelaMemoria;
import com.financiamento.entity.Simulacao;
import com.financiamento.repository.SimulacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimulacaoService {

    @Inject
    SimulacaoRepository repository;

    @Transactional  //rollback de protecao para metodo simular()
    public SimulacaoResponse simular (SimulacaoRequest request) {

        // conversor para taxa de percentual para decimal
        BigDecimal taxa = request.taxaJurosMensal.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        // memoria de calculo
        List<ParcelaMemoria> parcelas = new ArrayList<>();
        BigDecimal saldo = request.valorInicial;

        for (int mes = 1; mes <= request.prazoMeses; mes++) {
            BigDecimal juro = saldo.multiply(taxa).setScale(2, RoundingMode.HALF_UP);
            BigDecimal saldoFinal = saldo.add(juro).setScale(2, RoundingMode.HALF_UP);

            ParcelaMemoria parcela = new ParcelaMemoria();
            parcela.mes = mes;
            parcela.saldoInicial = saldo.setScale(2, RoundingMode.HALF_UP);
            parcela.juro = juro;
            parcela.saldoFinal = saldoFinal;

            parcelas.add(parcela);
            saldo = saldoFinal;
        }

        // Calcula somatorios
        BigDecimal valorTotalFinal = saldo.setScale(2, RoundingMode.HALF_UP);
        BigDecimal valorTotalJuros = valorTotalFinal.subtract(request.valorInicial).setScale(2, RoundingMode.HALF_UP);

        // monta as entidades
        Simulacao simulacao = new Simulacao();
        simulacao.valorInicial = request.valorInicial;
        simulacao.taxaJurosMensal = request.taxaJurosMensal;
        simulacao.prazoMeses = request.prazoMeses;
        simulacao.valorTotalFinal = valorTotalFinal;
        simulacao.valorTotalJuros = valorTotalJuros;
        simulacao.memoriaCalculo = parcelas;

        //linka as parcelas à simulação
        for (ParcelaMemoria parcela:parcelas) {
            parcela.simulacao = simulacao;
        }

        repository.persist(simulacao);

        return toResponse(simulacao);
    }

    public SimulacaoResponse buscaPorId (Long id) {
        Simulacao simulacao = repository.findById(id);
        if( simulacao == null) return null; // como so tem um comando, if pode ser em uma linha;

        return toResponse(simulacao);
    }

    // conversor de Entity para DTO - dados para o usuario
    private SimulacaoResponse toResponse(Simulacao simulacao) {
        SimulacaoResponse response = new SimulacaoResponse();
        response.id = simulacao.id;
        response.valorInicial = simulacao.valorInicial;
        response.taxaJurosMensal = simulacao.taxaJurosMensal;
        response.prazoMeses = simulacao.prazoMeses;
        response.valorTotalFinal = simulacao.valorTotalFinal;
        response.valorTotalJuros = simulacao.valorTotalJuros;
        response.criadoEm = simulacao.criadoEm;

        List<ParcelaMemoriaResponse> memoriaResponse = new ArrayList<>();
        for (ParcelaMemoria parcela : simulacao.memoriaCalculo) {
            ParcelaMemoriaResponse pr = new ParcelaMemoriaResponse();
            pr.mes = parcela.mes;
            pr.saldoInicial = parcela.saldoInicial;
            pr.juro = parcela.juro;
            pr.saldoFinal = parcela.saldoFinal;
            memoriaResponse.add(pr);
        }

        response.memoriaCalculo = memoriaResponse;

        return response;
    }

}


// QUestionar sobre INVESTIMENTO x FINANCIAMENTO
// desafio fala sobre financiamento, mas trata o problema como investimento - uma vez que não tem amortização etc.