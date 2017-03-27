package patterns.observable;

public interface StockListener
{
	void stockPriceChanged(Stock stockName, double oldPrice, double newPrice);
}
