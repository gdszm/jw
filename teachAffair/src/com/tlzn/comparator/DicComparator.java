package com.tlzn.comparator;

import java.util.Comparator;

import com.tlzn.model.sys.Tauth;
import com.tlzn.model.sys.Tdic;

/**
 * Dic排序
 * 
 * @author 刘平
 * 
 */
public class DicComparator implements Comparator<Tdic> {

	public int compare(Tdic o1, Tdic o2) {
		int i1 = o1.getCvalue() != null ? Integer.parseInt(o1.getCvalue()) : -1;
		int i2 = o2.getCvalue() != null ? Integer.parseInt(o2.getCvalue()): -1;
		return i1 - i2;
	}

}
