package mpc;

public class Item {
	
	private String name;
	private int price;
	private String description;

	public Item(String name, int price, String description) {
		
		this.name = name;
		this.price = price;
		this.description = description;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setPrice(int price)
	{
		this.price = price;
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public String getDescription()
	{
		return this.description;
	}

}
