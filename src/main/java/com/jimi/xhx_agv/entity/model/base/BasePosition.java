package com.jimi.xhx_agv.entity.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePosition<M extends BasePosition<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setRow(java.lang.Integer row) {
		set("row", row);
		return (M)this;
	}
	
	public java.lang.Integer getRow() {
		return getInt("row");
	}

	public M setCol(java.lang.Integer col) {
		set("col", col);
		return (M)this;
	}
	
	public java.lang.Integer getCol() {
		return getInt("col");
	}

	public M setArea(java.lang.Integer area) {
		set("area", area);
		return (M)this;
	}
	
	public java.lang.Integer getArea() {
		return getInt("area");
	}

	public M setType(java.lang.Integer type) {
		set("type", type);
		return (M)this;
	}
	
	public java.lang.Integer getType() {
		return getInt("type");
	}

	public M setHasShelves(java.lang.Integer hasShelves) {
		set("has_shelves", hasShelves);
		return (M)this;
	}
	
	public java.lang.Integer getHasShelves() {
		return getInt("has_shelves");
	}

	public M setHasGoods(java.lang.Integer hasGoods) {
		set("has_goods", hasGoods);
		return (M)this;
	}
	
	public java.lang.Integer getHasGoods() {
		return getInt("has_goods");
	}

	public M setLoadGoodsTime(java.util.Date loadGoodsTime) {
		set("load_goods_time", loadGoodsTime);
		return (M)this;
	}
	
	public java.util.Date getLoadGoodsTime() {
		return get("load_goods_time");
	}

	public M setIsLock(java.lang.Integer isLock) {
		set("is_lock", isLock);
		return (M)this;
	}
	
	public java.lang.Integer getIsLock() {
		return getInt("is_lock");
	}

}
