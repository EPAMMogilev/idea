package com.epam.idea.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "TAG")
public class Tag implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@ManyToMany
	@JoinTable(name = "IDEA_TAG",
			joinColumns = @JoinColumn(name = "TAG_ID"),
			inverseJoinColumns = @JoinColumn(name = "IDEA_ID"))
	private List<Idea> ideasWithTag;

	public Tag() {
		this.ideasWithTag = new ArrayList<>();
	}

	public Tag(String name) {
		requireNonNull(name, "name cannot be null");
		this.name = name;
		this.ideasWithTag = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Idea> getIdeasWithTag() {
		return ideasWithTag;
	}

	public void setIdeasWithTag(List<Idea> ideasWithTag) {
		this.ideasWithTag = ideasWithTag;
	}

	public void addIdea(Idea idea) {
		this.ideasWithTag.add(idea);
	}

	@Override
	public String toString() {
		return "Tag{" +
				"id=" + id +
				", name='" + name +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, ideasWithTag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Tag other = (Tag) obj;
		return Objects.equals(this.name, other.name)
				&& Objects.equals(this.ideasWithTag, other.ideasWithTag);
	}
}
