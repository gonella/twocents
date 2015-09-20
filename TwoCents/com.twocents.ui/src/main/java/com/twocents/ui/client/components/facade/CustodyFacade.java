package com.twocents.ui.client.components.facade;

import java.util.List;

import org.apache.log4j.Logger;

import com.twocents.model.Account;
import com.twocents.model.Custody;
import com.twocents.service.CustodyService;
import com.twocents.spring.ServiceLocator;

public class CustodyFacade {

	private static Logger logger = Logger.getLogger(CustodyFacade.class);

	private final CustodyService custodyService = (CustodyService) ServiceLocator
			.getBean(CustodyService.BEAN_NAME);

	public double getCustodyAverage(Account account) {
		double capitalInvestido = 0.0;
		double posicalAtual = 0.0;

		try {

			List<Custody> custodies = custodyService
					.findCustodyByAccount(account);

			if (custodies != null && !custodies.isEmpty()) {

				capitalInvestido = custodyService
						.getInvestmentMadeByAccount(account);

				posicalAtual = custodyService.getCurrentPositionValue(account);
				
				if (posicalAtual == 0) {
					return 0d;
				}

			}

		} catch (Exception e) {

			logger
					.error(
							"Erro na durante o calculo de rentabilidade para a conta "
									+ account.getNumber()
									+ " retornando o valor: 0.0 ", e);

			return 0;
		}

		return capitalInvestido > 0 ? (posicalAtual - capitalInvestido)
				/ capitalInvestido : 0.0;
	}
}
