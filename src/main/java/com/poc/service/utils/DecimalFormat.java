package com.poc.service.utils;

import java.math.RoundingMode;
import java.text.DecimalFormatSymbols;

public class DecimalFormat {
    private static java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");

    public static java.text.DecimalFormat getDecimalFormatTwoDigits() {
        df.setRoundingMode(RoundingMode.HALF_UP);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(symbols);
        return df;
    }
}
