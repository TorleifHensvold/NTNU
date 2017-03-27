package patterns.observable;

import java.util.ArrayList;
import java.util.Arrays;

public class StockIndex implements StockListener
{
	private ArrayList<Stock> stocks;
	private String name;	// Skjønner ikke hvorfor det skulle være et name på indeksen.
	private double indeks;
	
	@Override
	public void stockPriceChanged(Stock stockName, double oldPrice, double newPrice)
	{
		//double localCheck = this.indeks + (newPrice - oldPrice);
		computeIndex();
		//System.out.println(localCheck + " | " + this.indeks);
	}
	
	public StockIndex(String name, Stock... stocks)
	{
		this.name = name;
		this.stocks = new ArrayList<Stock>(Arrays.asList(stocks));
		for (Stock stock: stocks)
		{
			stock.addStockListener(this);
		}
		computeIndex();
	}
	
	private void computeIndex()
	{
		double localSum = 0;
		for (Stock stock : stocks)
		{
			localSum += stock.getPrice();
		}
		this.indeks = localSum;
	}
	
	public void addStock(Stock stock)
	{
		if (!stocks.contains(stock))
		{
			stocks.add(stock);
			stock.addStockListener(this);
		}
		computeIndex();
	}
	
	public void removeStock(Stock stock)
	{
		if (stocks.contains(stock))
		{
			stocks.remove(stock);
			stock.removeStockListener(this);
		}
		computeIndex();
	}
	
	public double getIndex()
	{
		return this.indeks;
	}
	
	public static void main(String[] args)
	{
		Stock test = new Stock("bah", 2.2);
		System.out.println(test.getPrice());
		StockIndex indeks = new StockIndex("ftff", test);
		System.out.println(indeks.getIndex());
		Stock test2 = new Stock("pah", 5.5);
		indeks.addStock(test2);
		System.out.println(indeks.getIndex());
		test2.setPrice(1.0);
		System.out.println(indeks.getIndex());
		System.out.println(test2.getPrice());
		System.out.println(test.getStockListeners());
	}
	
}
