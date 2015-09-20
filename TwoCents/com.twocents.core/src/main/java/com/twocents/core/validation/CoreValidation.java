package com.twocents.core.validation;

import com.twocents.core.util.StringCheck;
import com.twocents.exceptions.CoreException;
import com.twocents.model.StockBroker;
import com.twocents.model.TwUser;

public class CoreValidation {

	// private Logger logger = Logger.getLogger(CoreValidation.class);

	public static void validateUser(TwUser user) throws CoreException {

		if (user == null) {
			throw new CoreException(1011);
		}

		if (StringCheck.isEmpty(user.getName())) {
			throw new CoreException(1012);
		}

		/*
		 * if(StringUtils.isEmpty(user.getAccountName())){ throw new
		 * UIException(9201); }
		 * 
		 * if(StringCheck.isEmpty(user.getEmail())){ throw new
		 * CoreException(1013); }
		 */
	}

	public static void validateStockBroker(StockBroker stockBroker)
			throws CoreException {

		if (StringCheck.isEmpty(stockBroker.getName())) {
			throw new CoreException(1012);
		}

		if (StringCheck.isEmpty(stockBroker.getUsername())) {
			throw new CoreException(1016);
		}

		if (StringCheck.isEmpty(stockBroker.getPassword())) {
			throw new CoreException(1017);
		}

	}

}
