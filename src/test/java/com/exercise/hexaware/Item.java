package com.exercise.hexaware;

public class Item {

	private int id;
	private float price;
	private String name;
	private float score;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the score
	 */
	public float getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(float score) {
		this.score = score;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Item [id=");
		builder.append(id);
		builder.append(", price=");
		builder.append(price);
		builder.append(", name=");
		builder.append(name);
		builder.append(", score=");
		builder.append(score);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
}
