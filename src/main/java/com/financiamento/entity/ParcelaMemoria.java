package com.financiamento.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "parcelas_memoria")
public class ParcelaMemoria extends PanacheEntityBase {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Long id;

    //todos elementos das tuplas do SGBD devem ser não nulos
    @Column(nullable = false)
    public Integer mes;

    @Column(nullable = false)
    public BigDecimal saldoInicial;

    @Column(nullable = false)
    public BigDecimal juro;

    @Column(nullable = false)
    public BigDecimal saldoFinal;

    @ManyToOne
    @JoinColumn (name = "simulacao_id", nullable = false)   // FK para id do objeto Simulacoes
    public Simulacao simulacao;
}
