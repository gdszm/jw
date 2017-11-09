package com.tlzn.service.sys;

public interface RepairServiceI {

	/**
	 * 修复数据
	 */
	public void repair();

	/**
	 * 先清空数据，然后再修复数据
	 */
	public void deleteAndRepair();

}
