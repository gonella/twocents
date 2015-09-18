package com.twocents.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twocents.exceptions.CoreException;
import com.twocents.model.CommentPattern;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Throwable.class, CoreException.class})
public interface CommentPatternService extends BaseService<CommentPattern> {

	String BEAN_NAME = "com.twocents.service.CommentPatternService";
	
	public void saveThePattern(CommentPattern pattern) throws CoreException;

	public List<CommentPattern> listAllCommentPattern() throws CoreException;

	public CommentPattern findCommentPatternByName(String name) throws CoreException;

	public void deleteCommentPatternById(Long id) throws CoreException;

	public CommentPattern findCommentPatternById(Long id) throws CoreException;
}

