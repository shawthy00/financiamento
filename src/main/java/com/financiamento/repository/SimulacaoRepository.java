package com.financiamento.repository;

import com.financiamento.entity.Simulacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SimulacaoRepository implements PanacheRepository <Simulacao>{
    // usaremos apenas os metodos nativos    do panache

}
