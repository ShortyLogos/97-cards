package com.dm.tpfinal;

public class Pile {
    private Carte carteActive;
    private boolean ascendante;

    public Pile(boolean ascendante) {
        carteActive = null;
        this.ascendante = ascendante;
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

    public boolean isPossible(Carte c) {
        if (ascendante) {
            // Vérification pile ascendante + Règle de 10
            return c.getValeur() > carteActive.getValeur() || carteActive.getValeur() - 10 == c.getValeur();
        }
        else {
            // Vérification pile descendante + Règle de 10
            return c.getValeur() < carteActive.getValeur() || carteActive.getValeur() + 10 == c.getValeur();
        }
    }
}
