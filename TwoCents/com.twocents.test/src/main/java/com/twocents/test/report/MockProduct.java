/*
 * DynamicJasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

package com.twocents.test.report;

import java.util.ArrayList;
import java.util.List;

import ar.com.fdvs.dj.test.domain.Product;

public class MockProduct {

	public static List getDummyCollection(){

		List col =  new ArrayList();

		col.add(new Product(new Long("1"),"book","Harry Potter 7","Florida","Main Street", new Long("2500"), new Float("10000")));
		col.add(new Product(new Long("1"),"book","Harry Potter 7","Florida","Railway Station", new Long("1400"), new Float("2831.32")));
		col.add(new Product(new Long("1"),"book","Harry Potter 7","Florida","Baseball Stadium", new Long("4000"), new Float("38347")));
		col.add(new Product(new Long("1"),"book","Harry Potter 7","Florida","Shopping Center", new Long("3000"), new Float("9482.4")));
		
		
		
		return col;
	}
}
