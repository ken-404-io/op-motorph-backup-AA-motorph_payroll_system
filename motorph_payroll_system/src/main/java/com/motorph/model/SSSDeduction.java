package com.motorph.model;

import java.util.HashMap;
import java.util.Map;

// Inheritance + Polymorphism
public class SSSDeduction extends BaseDeduction {

    private final Map<Double, Double> sssTable;

    public SSSDeduction() {
        super("SSS");
        this.sssTable = initSSSTable();
    }

    @Override
    public double calculate(double grossPay) {
        double contribution = 0.0;
        for (Map.Entry<Double, Double> entry : sssTable.entrySet()) {
            if (grossPay < entry.getKey()) {
                contribution = entry.getValue();
                break;
            }
        }
        return contribution;
    }

    private Map<Double, Double> initSSSTable() {
        Map<Double, Double> table = new HashMap<>();
        table.put(4250.0, 180.0);
        table.put(4750.0, 202.5);
        table.put(5250.0, 225.0);
        table.put(5750.0, 247.5);
        table.put(6250.0, 270.0);
        table.put(6750.0, 292.5);
        table.put(7250.0, 315.0);
        table.put(7750.0, 337.5);
        table.put(8250.0, 360.0);
        table.put(8750.0, 382.5);
        table.put(9250.0, 405.0);
        table.put(9750.0, 427.5);
        table.put(10250.0, 450.0);
        table.put(10750.0, 472.5);
        table.put(11250.0, 495.0);
        table.put(11750.0, 517.5);
        table.put(12250.0, 540.0);
        table.put(12750.0, 562.5);
        table.put(13250.0, 585.0);
        table.put(13750.0, 607.5);
        table.put(14250.0, 630.0);
        table.put(14750.0, 652.5);
        table.put(15250.0, 675.0);
        table.put(15750.0, 697.5);
        table.put(16250.0, 720.0);
        table.put(16750.0, 742.5);
        table.put(17250.0, 765.0);
        table.put(17750.0, 787.5);
        table.put(18250.0, 810.0);
        table.put(18750.0, 832.5);
        table.put(19250.0, 855.0);
        table.put(19750.0, 877.5);
        table.put(20250.0, 900.0);
        table.put(20750.0, 922.5);
        table.put(Double.MAX_VALUE, 945.0);
        return table;
    }
}
