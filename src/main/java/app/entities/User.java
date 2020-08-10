package app.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public @Data class User {

	@Id
	private long id;
	@Column(unique = true , nullable = false)
	private String email;
	@JsonIgnore
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private ClientType clientType;
	@JsonIgnore
	private boolean enabled;
	@JsonIgnore
	private boolean tokenExpired;
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	private Collection<Role> roles;

	public User(long id, String email, String password, ClientType clientType, Collection<Role> roles) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.clientType = clientType;
		this.roles = roles;
		this.enabled = true;
	}

}
