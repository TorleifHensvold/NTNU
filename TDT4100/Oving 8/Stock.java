package patterns.observable;

import java.util.ArrayList;

public class Stock
{
	private String ticker;
	private double price;
	private ArrayList<StockListener> listeners = new ArrayList<StockListener>();
	
	public Stock(String ticker, double price)
	{
		checkIfValidPrice(price, true);
		this.ticker = ticker;
		this.price = price;
	}
	
	public void setPrice(double price)
	{
		double localOldPrice = 0 + this.price;
		if (checkIfValidPrice(price, true))
		{
			this.price = price;
		}
		if (localOldPrice != this.price)
		{
			informListeners(localOldPrice);
		}
	}
	public String getTicker()
	{
		return this.ticker;
	}
	public double getPrice()
	{
		return this.price;
	}
	
	public void addStockListener(StockListener listener)
	{
		if (!listeners.contains(listener))
		{
			listeners.add(listener);
		}
	}
	public void removeStockListener(StockListener listener)
	{
		if (listeners.contains(listener))
		{
			listeners.remove(listener);
		}
	}
	
	public ArrayList<StockListener> getStockListeners()
	{
		return this.listeners;
	}
	
	private boolean checkIfValidPrice(double price, boolean throwException)
	{
		if (price <= 0)
		{
			if (throwException)
			{
				throw new IllegalArgumentException("The price must be more than zero.");
			}
			return false;
		}
		return true;
	}
	
	private void informListeners(double oldPrice)
	{
		for (StockListener stockListener : listeners)
		{
			stockListener.stockPriceChanged(this, oldPrice, this.price);
		}
	}
	
	public static void main(String[] args)
	{
		Stock test = new Stock("bah", 2.2);
		System.out.println(test.getPrice());
		test.setPrice(5.4);
		System.out.println(test.getPrice());
	}
	
}
