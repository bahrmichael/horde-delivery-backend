package de.bahr.manager;

/**
 * Created by michaelbahr on 31/07/16.
 */
public class Contract {
    private boolean isCourier;
    private boolean isCompleted;
    private boolean isAssignedToLogistics;
    private boolean isOutstanding;

    public boolean isCourierAndNotCompleted() { return isCourier && !isCompleted; }

    public boolean isShipping() {
        return isCourier && !isCompleted && !isOutstanding && isAssignedToLogistics;
    }

    public boolean isWaitingForShipment() {
        return isCourier && isOutstanding && isAssignedToLogistics;
    }

    public void setIsCourier(boolean isCourier) {
        this.isCourier = isCourier;
    }

    public boolean isCourier() {
        return isCourier;
    }

    public void setCourier(boolean isCourier) {
        this.isCourier = isCourier;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setIsAssignedToLogistics(boolean isAssignedToLogistics) {
        this.isAssignedToLogistics = isAssignedToLogistics;
    }

    public boolean isAssignedToLogistics() {
        return isAssignedToLogistics;
    }

    public void setAssignedToLogistics(boolean isAssignedToLogistics) {
        this.isAssignedToLogistics = isAssignedToLogistics;
    }

    public void setIsOutstanding(boolean isOutstanding) {
        this.isOutstanding = isOutstanding;
    }

    public boolean isOutstanding() {
        return isOutstanding;
    }

    public void setOutstanding(boolean isOutstanding) {
        this.isOutstanding = isOutstanding;
    }
}
