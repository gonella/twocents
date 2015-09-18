package com.twocents.ui.client.report.header;

import com.twocents.ui.client.report.Report;

public class HeaderReportCompositeEmpty extends HeaderReportComposite {
	
	public HeaderReportCompositeEmpty(Report report) {
		super(report);
		
		getDescription().setText("");
	}

	@Override
	public void init() {
		
	}

	
}
