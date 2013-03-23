package com.jrk.aderoid;

public class Course {

	private String datecourse;
	private String content;
	private String hfin;
	private String hdebut;
	private String hours;
	private String matiere;
	private String week;
	private String color1;
	private String color2;
	
	public String getColor1() {
		return color1;
	}

	public void setColor1(String color1) {
		this.color1 = color1;
	}

	public String getColor2() {
		return color2;
	}

	public void setColor2(String color2) {
		this.color2 = color2;
	}

	private String creneau;
	
	public Course(String c,String d){
		
		this.datecourse = d;
		this.creneau = c;
	}

	public Course(String datecourse, String content, String hfin,
			String hdebut, String hours, String matiere, String week,
			String creneau) {
		this.datecourse = datecourse;
		this.content = content;
		this.hfin = hfin;
		this.hdebut = hdebut;
		this.hours = hours;
		this.matiere = matiere;
		this.week = week;
		this.creneau = creneau;
	}

	@Override
	public String toString() {
		return "Course [datecourse=" + datecourse + ", content=" + content
				+ ", hfin=" + hfin + ", hdebut=" + hdebut + ", hours=" + hours
				+ ", matiere=" + matiere + ", week=" + week + ", creneau="
				+ creneau + "]";
	}

	public String getDatecourse() {
		return datecourse;
	}

	public void setDatecourse(String datecourse) {
		this.datecourse = datecourse;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHfin() {
		return hfin;
	}

	public void setHfin(String hfin) {
		this.hfin = hfin;
	}

	public String getHdebut() {
		return hdebut;
	}

	public void setHdebut(String hdebut) {
		this.hdebut = hdebut;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getMatiere() {
		return matiere;
	}

	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getCreneau() {
		return creneau;
	}

	public void setCreneau(String creneau) {
		this.creneau = creneau;
	}
	
	

}
