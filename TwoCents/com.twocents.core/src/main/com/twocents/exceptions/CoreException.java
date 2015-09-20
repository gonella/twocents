package com.twocents.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import com.twocents.resources.CoreMessages;

@SuppressWarnings("serial")
public class CoreException extends Exception {

	public CoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public CoreException(String message) {
		super(message);
	}

	public CoreException(Integer errorCode) {
		super(CoreMessages.getMessage(errorCode));
	}

	public CoreException(Integer errorCode, Object... args) {
		super(CoreMessages.getMessage(errorCode.toString(), args));
	}

	public String toString() {
		return exToString(this).toString();
	}

	private StringBuffer exToString(CoreException ex) {
		StringBuffer sb = new StringBuffer();
		//String tab = "";

		sb.append("[message] = {").append(ex.getMessage()).append("}").append(
				"\n").append(
				((ex.getCause() != null) ? getStackTrace(ex.getCause()) : ""));
		/*
		 * if(!ex.isNestedExceptionsEmpty()) { sb.append("\n") .append(tab)
		 * .append("[nested exceptions] = {\n\t"); for (MVCDException nex :
		 * ex.getNestedExceptions()) { exToString(nex, level++,
		 * sb).append("\n"); } sb.append(tab).append("}\n"); }
		 */
		return sb;
	}

	public static String getStackTrace(Throwable throwable) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		throwable.printStackTrace(printWriter);
		return writer.toString();
	}
}
