package com.example.springbucks.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="T_COFFEE")
@Builder
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Coffee extends BaseEntity {
    private String name;

    @Type(type="org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
    parameters = {@org.hibernate.annotations.Parameter(name="currencyCode",value="CNY")})
    private Money price;

}
