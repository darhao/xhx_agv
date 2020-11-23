package com.jimi.xhx_agv.web.util.hik;

import java.util.LinkedList;
import java.util.List;

public class SchedulingTaskPackage extends BaseRequestPackage {

	class Position {
		private String positionCode;
		private String type;

		public String getPositionCode() {
			return positionCode;
		}

		public void setPositionCode(String positionCode) {
			this.positionCode = positionCode;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

	private String taskTyp;
	private String wbCode;
	private List<Position> positionCodePath;
	private String podCode;
	private String podDir;
	private String podType;
	private String materialLot;
	private String priority;
	private String taskCode;
	private String agvCode;
	private String data;

	public SchedulingTaskPackage(String taskCode, String taskTyp, String wbCode, List<String> positionCodePath, String priority) {
		super(CodeGenerate.generate());
		this.taskTyp = taskTyp;
		this.wbCode = wbCode;
		this.priority = priority;
		this.taskCode = taskCode;
		this.positionCodePath = new LinkedList<>();
		for (String string : positionCodePath) {
			Position position = new Position();
			position.setType("00");
			position.setPositionCode(string);
			this.positionCodePath.add(position);
		}
	}

	public String getTaskTyp() {
		return taskTyp;
	}

	public void setTaskTyp(String taskTyp) {
		this.taskTyp = taskTyp;
	}

	public String getWbCode() {
		return wbCode;
	}

	public void setWbCode(String wbCode) {
		this.wbCode = wbCode;
	}

	public List<Position> getPositionCodePath() {
		return positionCodePath;
	}

	public void setPositionCodePath(List<Position> positionCodePath) {
		this.positionCodePath = positionCodePath;
	}

	public String getPodCode() {
		return podCode;
	}

	public void setPodCode(String podCode) {
		this.podCode = podCode;
	}

	public String getPodDir() {
		return podDir;
	}

	public void setPodDir(String podDir) {
		this.podDir = podDir;
	}

	public String getPodType() {
		return podType;
	}

	public void setPodType(String podType) {
		this.podType = podType;
	}

	public String getMaterialLot() {
		return materialLot;
	}

	public void setMaterialLot(String materialLot) {
		this.materialLot = materialLot;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getAgvCode() {
		return agvCode;
	}

	public void setAgvCode(String agvCode) {
		this.agvCode = agvCode;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

}
