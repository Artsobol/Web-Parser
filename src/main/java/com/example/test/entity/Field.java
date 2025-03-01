package com.example.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Validated
@Data
@NoArgsConstructor
@Entity
@Table(name = "field")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
//    @Column(name = "code")
    private Long code;

    @Column(name = "university_id", nullable = false)
    private Long universityId;

    @Column(name = "cost")
    @Min(0)
    private Integer cost;

    @Column(name = "free_place_quantity")
    @Min(0)
    private Integer freePlaceQuantity;

    @Column(name = "paid_place_quantity")
    @Min(0)
    private Integer paidPlaceQuantity;

    @Column(name = "target_place_quantity")
    @Min(0)
    private Integer targetPlaceQuantity;

    @Column(name = "last_update_date")
    private LocalDate lastUpdateDate;

    public Field(Long id, Long code, Long universityId, Integer cost, Integer freePlaceQuantity, Integer paidPlaceQuantity, Integer targetPlaceQuantity) {
        this.id = id;
        this.code = code;
        this.universityId = universityId;
        this.cost = cost;
        this.freePlaceQuantity = freePlaceQuantity;
        this.paidPlaceQuantity = paidPlaceQuantity;
        this.targetPlaceQuantity = targetPlaceQuantity;
    }
}
