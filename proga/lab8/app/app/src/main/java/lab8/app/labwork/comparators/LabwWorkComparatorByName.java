package lab8.app.labwork.comparators;

import java.util.Comparator;

import lab8.app.labwork.LabWork;

public class LabwWorkComparatorByName implements Comparator<LabWork> {

    @Override
    public int compare(LabWork arg0, LabWork arg1) {
        return arg0.getName().length() - arg1.getName().length();
    }
    
}
