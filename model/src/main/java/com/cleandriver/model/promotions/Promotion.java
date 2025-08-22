package com.cleandriver.model.promotions;


import com.cleandriver.config.DaysOfWeekConverter;
import com.cleandriver.model.ServiceType;
import com.cleandriver.model.enums.PromotionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "promotion")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Promotion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Long id;

    @Column(nullable = false)
    private String tittle;

    private String description;

    @Column(name = "only_customer" , nullable = false)
    private boolean onlyCustomer;   // ¿solo clientes registrados?

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private LocalDateTime createdAt;

    private boolean active;

    private int maxUses;

    @OneToMany(mappedBy = "promotion")
    private List<AppointmentPromotion> usePromotion;

    private PromotionType promotionType;

    @Column(nullable = false)
    private boolean onlyOnce;  // ejemplo: "Primer lavado gratis"

//    @Column(nullable = false)
//    private int maxByDay;    // ejemplo: solo 1 uso por día

    @ManyToMany
    @JoinTable(
            name = "promotion_services",
            joinColumns = @JoinColumn(name = "promotion_id"),
            inverseJoinColumns = @JoinColumn(name = "service_type_id")
    )    private List<ServiceType> serviceType;

    @Convert(converter = DaysOfWeekConverter.class)
    private List<DayOfWeek> daysToCollect;

    @Convert(converter = DaysOfWeekConverter.class)
    private List<DayOfWeek> daysToReserve;

    private BigDecimal discount;


    public boolean isActive() {
        LocalDate hoy = LocalDate.now();
        return (hoy.isEqual(getStartDate()) || hoy.isAfter(getStartDate()))
                && (hoy.isEqual(getEndDate()) || hoy.isBefore(getEndDate()));
    }


}
