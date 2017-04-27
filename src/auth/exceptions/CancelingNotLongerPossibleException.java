package auth.exceptions;

import data.entities.Ticket;

public class CancelingNotLongerPossibleException extends Exception {
    public CancelingNotLongerPossibleException(Ticket ticket) {
        super("Plane for ticket " + ticket.getID() + " already took off!");
    }
}
