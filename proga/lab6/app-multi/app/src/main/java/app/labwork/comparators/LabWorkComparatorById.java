package app.labwork.comparators;

import java.util.Comparator;

import app.labwork.LabWork;

public class LabWorkComparatorById implements Comparator<LabWork> {

    @Override
    public int compare(LabWork arg0, LabWork arg1) {
        return Long.valueOf(arg0.getId() - arg1.getId()).intValue();
    }
    
}
