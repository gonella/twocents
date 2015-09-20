package com.twocents.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;

@Entity
@NamedQueries({
	@NamedQuery(name="commentPattern.findCommentPatternByName", 
			query="select c from CommentPattern c where c.name = :name")
			}
)

public class CommentPattern extends Persistent {
	
	private static final long serialVersionUID = 4983345897472053015L;

	public static final String FIND_COMMENT_PATTERN_BY_NAME = "commentPattern.findCommentPatternByName";
	
	private String name;
	private String commentPattern;
	private boolean defaultComment;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommentPattern() {
		return commentPattern;
	}

	public void setCommentPattern(String commentPattern) {
		this.commentPattern = commentPattern;
	}

	public boolean isDefaultComment() {
		return defaultComment;
	}

	public void setDefaultComment(boolean defaultComment) {
		this.defaultComment = defaultComment;
	}

	public CommentPattern() {
		super();
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.TW_DEFAULT);
	}
	
}
