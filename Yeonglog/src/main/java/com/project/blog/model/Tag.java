
package com.project.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tagId;

	@Column(nullable = false, length = 50) // not null 
	private String name;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "boardId") private Board board;
	 */
	@Override
	public String toString() {
		return "Tag [tagId=" + tagId + ", name=" + name + "]";
	}

}
