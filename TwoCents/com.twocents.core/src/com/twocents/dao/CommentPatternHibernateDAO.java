package com.twocents.dao;

import javax.persistence.Query;

import com.twocents.model.CommentPattern;


public class CommentPatternHibernateDAO extends BaseDAOImpl<CommentPattern> implements CommentPatternDAO {
	
	public CommentPattern findCommentPatternByName(String name) {
		Query q = getEntityManager().createNamedQuery(CommentPattern.FIND_COMMENT_PATTERN_BY_NAME);
	    q.setParameter("name", name);	    
	    return (CommentPattern)q.getSingleResult();
	}

	



}
