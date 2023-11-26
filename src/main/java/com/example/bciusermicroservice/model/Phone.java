package com.example.bciusermicroservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long number;
    private int citycode;
    private String countrycode;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(id, phone.id) &&
                Objects.equals(number, phone.number) &&
                Objects.equals(citycode, phone.citycode) &&
                Objects.equals(countrycode, phone.countrycode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, citycode, countrycode);
    }

}
