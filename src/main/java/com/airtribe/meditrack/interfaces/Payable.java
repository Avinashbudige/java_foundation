package com.airtribe.meditrack.interfaces;

import com.airtribe.meditrack.entity.Bill;

public interface Payable {
    /**
     * Calculate the bill for a service
     * @return the calculated Bill
     */
    Bill calculateBill();
}
