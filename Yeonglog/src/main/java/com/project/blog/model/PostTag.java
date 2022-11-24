
package com.project.blog.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder

@AllArgsConstructor

@NoArgsConstructor

@Data

@Entity

@IdClass(PostTag.class)
public class PostTag implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id

	@ManyToOne

	@JoinColumn(name = "postId") 
	private Board board;

	@Id

	@ManyToOne

	@JoinColumn(name = "tagId")
	private Tag tag;

	@Override
	public String toString() {
		return "PostTag [board=" + board + ", tagId=" + tag + "]";
	}

}
