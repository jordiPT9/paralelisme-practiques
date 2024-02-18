package p02_ImpLockTable;

import p00_CommonA.Table;

public class ImplicitLockTable extends Table {

    private enum Card {
        JACK,
        QUEEN,
        KING
    }

    private boolean tableIsFreeToUse = true;
    private volatile Card firstCard;

    protected void gainExclusiveAccess() {
        boolean exitLoop = false;
        while (!exitLoop) {
            synchronized (this) {
                if (tableIsFreeToUse) {
                    this.tableIsFreeToUse = false;
                    exitLoop = true;
                }
            }
        }
    }

    protected void releaseExclusiveAccess() {
        synchronized (this) {
            this.tableIsFreeToUse = true;
        }
    }

    public void putJack(int id) {
        this.gainExclusiveAccess();
        while (handIsFull() || (ffs == 3 && firstCard != Card.JACK)) {
            this.releaseExclusiveAccess();
            Thread.yield();
            this.gainExclusiveAccess();
        }

        if (ffs == 0) {
            this.firstCard = Card.JACK;
        }
    }

    public void putQueen(int id) {
        this.gainExclusiveAccess();
        while (handIsFull() || (ffs == 3 && firstCard != Card.QUEEN)) {
            this.releaseExclusiveAccess();
            Thread.yield();
            this.gainExclusiveAccess();
        }

        if (ffs == 0) {
            this.firstCard = Card.QUEEN;
        }
    }

    public void putKing(int id) {
        this.gainExclusiveAccess();
        while (handIsFull() || (ffs == 3 && firstCard != Card.KING)) {
            this.releaseExclusiveAccess();
            Thread.yield();
            this.gainExclusiveAccess();
        }

        if (ffs == 0) {
            this.firstCard = Card.KING;
        }
    }

    public void cardPut() {
        this.releaseExclusiveAccess();
    }

    public void startCheck(int id) {
        this.gainExclusiveAccess();
        while (this.ffs < 4) {
            this.releaseExclusiveAccess();
            Thread.yield();
            this.gainExclusiveAccess();
        }
    }

    public void endCheck(int id) {
        this.ffs = 0;
        this.firstCard = null; // Reset the first card to null
        this.releaseExclusiveAccess();
    }

    /* metode creat per nosaltres */
    private boolean handIsFull() {
        return this.ffs >= this.NUM_SLOTS;
    }
}
