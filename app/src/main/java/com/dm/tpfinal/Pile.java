package com.dm.tpfinal;

public class Pile {
    private Carte carteActive;
    private boolean ascendante;
    private boolean descendante;
    // variable qui mémorise le nombre de cartes sur la pile ? Pertinente?

    public Pile(boolean ascendante) {
        carteActive = null;
        if (ascendante) {
            this.ascendante = true;
            this.descendante = false;
        }
        else {
            this.ascendante = false;
            this.descendante = true;
        }
    }

    public Carte getCarteActive() {
        return carteActive;
    }

    public void setCarteActive(Carte carteActive) {
        this.carteActive = carteActive;
    }

    public boolean isAscendante() {
        return ascendante;
    }

    public void setAscendante(boolean ascendante) {
        this.ascendante = ascendante;
    }
}
