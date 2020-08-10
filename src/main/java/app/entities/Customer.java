package app.entities;

import java.util.Objects;
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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class Customer extends Users{
	@Id
	@GeneratedValue
	private long id;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(unique = true, nullable = false)
	private String email;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
	@JsonIgnore
	private Set<CustomersCoupons> purchases;
	private boolean active;

	/**
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param email
	 */
	public Customer(long id, String firstName, String lastName, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		}

	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 */
	public Customer(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.active = true;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", purchases=" + purchases + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Customer))
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(email, other.email) && id == other.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id);
	}

}
