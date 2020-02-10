package com.der3318;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class Console {

    public static ColoredPrinter Err = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.RED).build();
    public static ColoredPrinter Warn = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.YELLOW).build();
    public static ColoredPrinter Info = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.GREEN).build();

}
