package com.epam.idea.core.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "ROLE")
public class Role implements Serializable, GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "NAME", nullable = false)
	private Authority name;

	public Role() {
	}

	public Role(Authority name) {
		requireNonNull(name, "Name cannot be null");
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public Authority getName() {
		return name;
	}

	public void setName(Authority name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", name=" + name +
				'}';
	}

    @Override
    public String getAuthority() {
        return "ROLE_" + name.toString();
    }
}
