package com.sdr.checkers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum DeviationCheckers {

    GRABBS_CHECKER("Grabbs criteria", GrabbsDeviationChecker.class);

    private String name;
    private Class<? extends DeviationChecker> checkerClass;

    DeviationCheckers(String name, Class<? extends DeviationChecker> checkerClass) {
        this.name = name;
        this.checkerClass = checkerClass;
    }

    public String getName() {
        return name;
    }

    public Class<? extends DeviationChecker> getCheckerClass() {
        return checkerClass;
    }

    public static Class<? extends DeviationChecker> getClassByName(String name) throws Exception {
        return Arrays.asList(DeviationCheckers.values())
                .stream()
                .filter(dc -> dc.getName().equals(name))
                .findFirst()
                .orElseThrow(Exception::new) // TODO add throwing custom exception
                .getCheckerClass();
    }

    public static List<String> getNames() {
        return Arrays.asList(DeviationCheckers.values())
                .stream()
                .map(dc -> dc.getName())
                .collect(Collectors.toList());
    }
}
