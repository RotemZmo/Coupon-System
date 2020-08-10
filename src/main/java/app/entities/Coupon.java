package app.entities;

import java.sql.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupons")
@NoArgsConstructor
public @Data class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String title;
	@Column
	private String description;
	@ManyToOne(fetch = FetchType.EAGER)
	private Company company;
	@Column
	private int amount;
	@Column
	private double price;
	@Column
	private Date startDate;
	@Column
	private Date endDate;
	@Column
	private CategoryType type;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "coupon",orphanRemoval = true)
	@JsonIgnore
	private Set<CustomersCoupons> purchases;
	@Column
	private String image;
	@Column
	private boolean active;

	/**
	 * 
	 * @param title
	 * @param description
	 * @param company
	 * @param amount
	 * @param price
	 * @param startDate
	 * @param endDate
	 * @param image
	 * @param type
	 */
	public Coupon(String title, String description, Company company, int amount, double price, Date startDate,
			Date endDate, String image, CategoryType type) {
		super();
		this.title = title;
		this.description = description;
		this.company = company;
		this.amount = amount;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.active = true;
		this.image = image;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = (int) (prime * result + id);
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Coupon))
			return false;
		@SuppressWarnings("unused")
		Coupon other = (Coupon) obj;

		return this.hashCode() == obj.hashCode();
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", description=" + description + ", company=" + company
				+ ", amount=" + amount + ", price=" + price + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", type=" + type + ", purchases=" + purchases + "]";
	}

}
