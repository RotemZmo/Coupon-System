package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class Admin extends Users{

	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true,nullable = false)
	private String email;
	private boolean active;
	
	public Admin(String email) {
		this.email = email;
		this.active = true;
	}
	
}
