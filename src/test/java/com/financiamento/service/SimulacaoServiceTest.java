package com.financiamento.service;

import com.financiamento.dto.SimulacaoResponse;
import com.financiamento.dto.SimulacaoRequest;
import com.financiamento.repository.SimulacaoRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SimulacaoServiceTest {

    @Inject
    SimulacaoService service;

    @Test
    public void deveRetornarNullParaIdInexistente(){
        SimulacaoResponse response = service.buscaPorId(999L);
        assertNull(response);
    }

    @Test
    @Transactional
    public void deveCriarSimulacaoComSucesso() {
        SimulacaoRequest request = new SimulacaoRequest();

        request.valorInicial = new BigDecimal("1000.00");
        request.taxaJurosMensal = new BigDecimal("1.5");
        request.prazoMeses = 3;

        SimulacaoResponse response = service.simular(request);

        assertNotNull(response.id);
        assertEquals(new BigDecimal("1045.68"), response.valorTotalFinal);
        assertEquals(new BigDecimal("45.68"), response.valorTotalJuros);
        assertEquals(3, response.memoriaCalculo.size());
    }

    @Test
    @Transactional
    public void deveCalcularMemoriaCorretamente(){
        SimulacaoRequest request = new SimulacaoRequest();
        request.valorInicial = new BigDecimal("1000.00");
        request.taxaJurosMensal = new BigDecimal("1.5");
        request.prazoMeses = 1;

        SimulacaoResponse response = service.simular(request);

        assertEquals(1, response.memoriaCalculo.size());
        assertEquals(new BigDecimal("1000.00"), response.memoriaCalculo.get(0).saldoInicial);
        assertEquals(new BigDecimal("15.00"), response.memoriaCalculo.get(0).juro);
        assertEquals(new BigDecimal("1015.00"), response.memoriaCalculo.get(0).saldoFinal);

    }

}
