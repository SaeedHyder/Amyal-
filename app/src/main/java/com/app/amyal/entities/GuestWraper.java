package com.app.amyal.entities;

/**
 * Created by Addi.
 */
public class GuestWraper {
    int nofInfant=0;
    int nofAdult=1;
    int nofChild=0;

    public GuestWraper add(int nofAdult, int nofChild, int nofInfant) {
        setNofAdult(nofAdult);
        setNofChild(nofChild);
        setNofInfant(nofInfant);
        return this;
    }

    public int getNofInfant() {
        return nofInfant;
    }

    public void setNofInfant(int nofInfant) {
        this.nofInfant = nofInfant;
    }

    public int getNofAdult() {
        return nofAdult;
    }

    public void setNofAdult(int nofAdult) {
        this.nofAdult = nofAdult;
    }

    public int getNofChild() {
        return nofChild;
    }

    public void setNofChild(int nofChild) {
        this.nofChild = nofChild;
    }
}
