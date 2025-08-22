package com.syp.invest.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Aporte implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "valor_investimento", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "O valor do investimento é obrigatório.")
    @DecimalMin(value = "1.0", message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    @Column(name = "data_do_investimento", nullable = false)
    @NotNull(message = "A data do investimento é obrigatória.")
    @PastOrPresent(message = "A data não pode estar no futuro.")
    //@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") // opcional se usar LocalDateTime
    private LocalDate instante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investimento_id", nullable = false)
    private Investimento investimento;
}
