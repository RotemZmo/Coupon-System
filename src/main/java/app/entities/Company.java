package app.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "companies")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class Company extends Users{
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true, nullable = false)
	private String name;
	@Column(unique = true, nullable = false)
	private String email;
	@OneToMany(mappedBy = "company", fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonIgnore
	private Set<Coupon> coupons;
	private boolean active;
	private double totalIncome;
	private double fee;

	/**
	 * 
	 * @param name
	 * @param email
	 */
	public Company(String name, String email) {
		super();
		this.name = name;
		this.email = email;
		this.active = true;
		this.fee = 0.1;
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param email
	 * @param active
	 * @param fee
	 */
	public Company(int id, String name, String email, boolean active, double fee) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.active = true;
		this.fee = 0.1;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email  + ", active="
				+ active + ", totalIncome=" + totalIncome + ", fee=" + fee + "]";
	}

}
