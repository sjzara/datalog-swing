package com.spiffymap.datalog.ui.swing;

import java.awt.print.PrinterJob;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import net.spiffymap.datalog.shared.drawing.PrinterSettings;

/**
 *
 * @author steve
 */
public class AWTPrinterSettings extends PrinterSettings {

    private MediaSizeName mediaSizeFromPageSize() {
        switch (pageSize) {
            case "A0":
                return MediaSizeName.ISO_A0;
            case "A1":
                return MediaSizeName.ISO_A1;
            case "A2":
                return MediaSizeName.ISO_A2;
            case "A3":
                return MediaSizeName.ISO_A3;
            case "A4":
            default:
                return MediaSizeName.ISO_A4;
        }
    }

    public AWTPrinterSettings() {
        super(null, null, null);
    }

    @Override
    public void setupPrinter() {
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        if (pageSize != null) {
            attributes.add(mediaSizeFromPageSize());
        }
        PrinterJob job = PrinterJob.getPrinterJob();
        if (job.printDialog(attributes)) {
            System.out.println();
        }
    }
}
