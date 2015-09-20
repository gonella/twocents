package com.twocents.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.twocents.dao.CommentPatternDAO;
import com.twocents.exceptions.CoreException;
import com.twocents.model.CommentPattern;

public class CommentPatternServiceImpl extends BaseServiceImpl<CommentPattern, CommentPatternDAO> implements CommentPatternService {

	private static final Logger logger = Logger.getLogger(CommentPatternServiceImpl.class);
	
	public void setCommentPatternDAO(CommentPatternDAO dao) {
		super.setDao(dao);
	}
	
	public CommentPatternDAO getCommentPatternDAO() {
		return super.getDao();
	}
	
	
	public void saveThePattern(CommentPattern pattern) throws CoreException {		
		getDao().persist(pattern);		
	}

	public List<CommentPattern> listAllCommentPattern() throws CoreException {
		return getDao().findAll();
	}

	public CommentPattern findCommentPatternByName(String name)
			throws CoreException {		
		return getDao().findCommentPatternByName(name);
	}

	public void deleteCommentPatternById(Long id) throws CoreException {
		getDao().deleteById(id);		
	}

	public CommentPattern findCommentPatternById(Long id) throws CoreException {
		return getDao().findById(id);
	}
	
	
}
