package com.tinqin.library.book.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Builder
    public Subscription(LocalDate startDate){
        this.startDate = startDate;

        this.endDate = startDate.plusDays(30);
        this.canRent = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "can_rent", nullable = false)
    private Boolean canRent;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<RentedBook> rentedBooks;
}
