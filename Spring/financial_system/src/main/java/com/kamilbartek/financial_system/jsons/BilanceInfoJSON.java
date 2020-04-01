package com.kamilbartek.financial_system.jsons;

import java.util.List;

public class BilanceInfoJSON {
    public String name;
    public String surname;
    public Long currentBilance;
    public List<TransferJSON> recentTenTransfers;
}
