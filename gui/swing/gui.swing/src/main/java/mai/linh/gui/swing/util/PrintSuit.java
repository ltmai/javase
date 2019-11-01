package mai.linh.gui.swing.util;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.RepaintManager;

public class PrintSuit implements Printable {
	private Component compToPrint;
	
	public static void printComponent(Component c)
	{
		new PrintSuit(c).print();
	}
	
	public PrintSuit(Component c)
	{
		compToPrint = c;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException 
	{
		if (pageIndex > 0)
		{
			return (NO_SUCH_PAGE);
		}
		else
		{
			int x = (int)pageFormat.getImageableX() + 1;
			int y = (int)pageFormat.getImageableY() + 1;
			graphics.translate(x,y);
			RepaintManager currentManager = RepaintManager.currentManager(compToPrint);
			currentManager.setDoubleBufferingEnabled(false);				
			compToPrint.paint(graphics);
			currentManager.setDoubleBufferingEnabled(true);
			return (PAGE_EXISTS);
		}
	}	

	private void print() {
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		printerJob.setPrintable(this);
		
		if (printerJob.printDialog()) {
			try {
				printerJob.print();
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}
}
