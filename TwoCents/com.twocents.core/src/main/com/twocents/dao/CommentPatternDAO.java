package com.twocents.dao;

import com.twocents.model.CommentPattern;


public interface CommentPatternDAO extends BaseDAO<CommentPattern> {

	public CommentPattern findCommentPatternByName(String name);


}
