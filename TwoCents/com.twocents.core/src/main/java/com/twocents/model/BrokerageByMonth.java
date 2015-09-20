package com.twocents.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class BrokerageByMonth implements Comparable<BrokerageByMonth>{

    private static final Logger logger = Logger.getLogger(BrokerageByMonth.class);

    //Mes e Ano
    private Date dateYearAndMonth;

    //private Map<String, BrokerageNote> brokerageNoteList = new LinkedHashMap<String, BrokerageNote>();

    private List<BrokerageNote> listNote=new ArrayList<BrokerageNote>();

    public BrokerageByMonth(Date dateYearAndMonth){
        this.dateYearAndMonth=dateYearAndMonth;
    }

    public void addBrokerageNote(BrokerageNote note){
        getListNote().add(note);
    }


    public Date getDateYearAndMonth() {
        return dateYearAndMonth;
    }

    public void setDateYearAndMonth(Date dateYearAndMonth) {
        this.dateYearAndMonth = dateYearAndMonth;
    }


    public int compareTo(BrokerageByMonth arg0) {

        int results = this.getDateYearAndMonth().compareTo(arg0.getDateYearAndMonth());

        return 0;

    }

    @Override
    public String toString(){
        String result="Nota por Mês :["+getDateYearAndMonth()+"] - Items Size: "+ getListNote().size();

        return result;
    }

    public void setListNote(List<BrokerageNote> listNote) {
        this.listNote = listNote;
    }

    public List<BrokerageNote> getListNote() {
        return listNote;
    }

}
