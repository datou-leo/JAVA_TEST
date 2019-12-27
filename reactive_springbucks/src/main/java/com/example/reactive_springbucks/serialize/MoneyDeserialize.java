package com.example.reactive_springbucks.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.IOException;

public class MoneyDeserialize extends StdDeserializer<Money> {

    protected  MoneyDeserialize(){
        super(Money.class);
    }

    @Override
    public Money deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Money.ofMinor(CurrencyUnit.of("CNY"),p.getLongValue());
    }
}
