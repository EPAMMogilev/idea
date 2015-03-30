package com.epam.idea.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "ROLE")
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "NAME", nullable = false)
	private Authority name;

	@ManyToMany
	@JoinTable(name = "USER_ROLE",
			joinColumns = @JoinColumn(name = "ROLE_ID"),
			inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private List<User> usersWithRole;

	public Role() {
		this.usersWithRole = new ArrayList<>();
	}

	public Role(Authority name) {
		requireNonNull(name, "Name cannot be null");
		this.name = name;
		this.usersWithRole = new ArrayList<>();
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

	public List<User> getUsersWithRole() {
		return usersWithRole;
	}

	public void setUsersWithRole(List<User> usersWithRole) {
		this.usersWithRole = usersWithRole;
	}

	public void addUser(User user) {
		this.usersWithRole.add(user);
	}

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", name=" + name +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, usersWithRole);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Role other = (Role) obj;
		return Objects.equals(this.name, other.name)
				&& Objects.equals(this.usersWithRole, other.usersWithRole);
	}
}
