package com.example.resttemplatepluskeepalivedemo.support;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class MoneyDeserializabler extends StdDeserializer<Money> {
    protected MoneyDeserializabler(){
        super(Money.class);
    }

    @Override
    public Money deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Money.of(CurrencyUnit.of("CNY"),p.getDecimalValue());
    }
}
