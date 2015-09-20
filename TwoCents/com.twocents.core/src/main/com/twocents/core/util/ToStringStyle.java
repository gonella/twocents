package com.twocents.core.util;

import java.util.Collection;

import com.twocents.model.Persistent;

public class ToStringStyle extends org.apache.commons.lang.builder.ToStringStyle {

	public static ToStringStyle TW_DEFAULT = new ToStringStyle();
	
	private static final long serialVersionUID = 7729137773276002819L;
	

    /**
     * <p>Constructor.</p>
     *
     * <p>Use the static constant rather than instantiating.</p>
     */
	ToStringStyle() {
        super();
        this.setUseShortClassName(true);
        this.setUseIdentityHashCode(false);
    }

    /**
     * <p>Ensure <code>Singleton</ode> after serialization.</p>
     * @return the singleton
     */
    private Object readResolve() {
        return ToStringStyle.SHORT_PREFIX_STYLE;
    }

	protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
		if (!(value instanceof Persistent)) {
			buffer.append(value);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void appendDetail(StringBuffer buffer, String fieldName,
			Collection coll) {
		
	}
	

}
