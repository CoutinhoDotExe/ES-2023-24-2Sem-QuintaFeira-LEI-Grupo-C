package pt.iscte_iul.ista.ES_2324_LEI_GC;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Class {
    private String curso;
    private String unidadeCurricular;
    private String turno;
    private String turma;
    private int inscritos;
    private String caraSala;
    private String diaDaSemana;
    private String horaInicio;
    private String horaFim;
    private String dataDaAula;
    private String salaAtribuida;

    public Class(String curso, String unidadeCurricular, String turno, String turma, int inscritos, String caraSala) {
        this.curso = curso;
        this.unidadeCurricular = unidadeCurricular;
        this.turno = turno;
        this.turma = turma;
        this.inscritos = inscritos;
        this.caraSala = caraSala;
    }

    // Getters
    public String getCurso() {
        return curso;
    }

    public String getUnidadeCurricular() {
        return unidadeCurricular;
    }

    public String getTurno() {
        return turno;
    }

    public String getTurma() {
        return turma;
    }

    public int getInscritos() {
        return inscritos;
    }

    public String getCaraSala() {
        return caraSala;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public String getDataDaAula() {
        return dataDaAula;
    }

    public String getSalaAtribuida() {
        return salaAtribuida;
    }
    // Setters
    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public void setDataDaAula(String dataDaAula) {
        this.dataDaAula = dataDaAula;
    }

    public void setSalaAtribuida(String salaAtribuida) {
        this.salaAtribuida = salaAtribuida;
    }

    public String toString() {
        return "Class{" +
                "curso='" + curso + '\'' +
                ", unidadeCurricular='" + unidadeCurricular + '\'' +
                ", turno='" + turno + '\'' +
                ", turma='" + turma + '\'' +
                ", inscritos=" + inscritos +
                ", caraSala='" + caraSala + '\'' +
                ", diaDaSemana='" + diaDaSemana + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFim='" + horaFim + '\'' +
                ", dataDaAula='" + dataDaAula + '\'' +
                ", salaAtribuida='" + salaAtribuida + '\'' +
                '}';
    }

    public static String formatTimeSlot(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = LocalTime.parse(endTime, formatter).plusMinutes(30);
        return startTime + " - " + end.format(formatter);
    }
}
