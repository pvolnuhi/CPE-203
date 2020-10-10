public class Buy
{
	private String sessionID;
	private String productID;
	private int price;
	private int quantity;

	public Buy(String sessionID, String productID, int price, int quantity)
	{
		this.sessionID = sessionID;
		this.productID = productID;
		this.price = price;
		this.quantity = quantity;
	}

	public String getSessionID()
	{
		return sessionID;
	}
	public String getProduct()
	{
		return productID;
	}
	public int getPrice()
	{
		return price;
	}
	public int getQuantity()
	{
		return quantity;
	}
}