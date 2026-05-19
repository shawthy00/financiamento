package com.financiamento.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity // criando o banco de dados
@Table(name = "simulacoes")
public class Simulacao extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public BigDecimal valorInicial;

    @Column(nullable = false)
    public BigDecimal taxaJurosMensal;

    @Column(nullable = false)
    public Integer prazoMeses;

    @Column(nullable = false)
    public BigDecimal valorTotalFinal;

    @Column
    public BigDecimal valorTotalJuros;

    @Column
    public LocalDateTime criadoEm = LocalDateTime.now(); //se relaciona com o dia que foi feita, para nao ter juro de acerto

    @OneToMany(mappedBy = "simulacao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<ParcelaMemoria> memoriaCalculo;


}
