package com.twocents.ui.client.components.facade;

import java.util.List;

import com.twocents.model.CommentPattern;
import com.twocents.service.CommentPatternService;
import com.twocents.spring.ServiceLocator;
import com.twocents.ui.exceptions.UIException;

public class CommentConfigurationFacade {
	
	CommentPatternService commentPatternService = (CommentPatternService)ServiceLocator.getBean(CommentPatternService.BEAN_NAME);

	public CommentPatternService getCommentPatternService() {
		return commentPatternService;
	}
	
	public void saveCommentPattern(CommentPattern pattern) throws UIException {
		try {
			getCommentPatternService().saveThePattern(pattern);
		} catch (Exception e) {
			throw new UIException(e);
		}
		
	}
	
	public List<CommentPattern> listAllCommentPattern() throws UIException{
		try {
			return getCommentPatternService().listAllCommentPattern();
		} catch (Exception e) {
			throw new UIException(e);
		}		
	}

	public CommentPattern findCommentPatternByName(String name) throws UIException {
		try{
			return getCommentPatternService().findCommentPatternByName(name);
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	public CommentPattern findCommentPatternById(Long id) throws UIException {
		try{
			return getCommentPatternService().findCommentPatternById(id);
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	public void deleteCommentPatternById(Long id) throws UIException {
		try{
			getCommentPatternService().deleteCommentPatternById(id);
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	
}
