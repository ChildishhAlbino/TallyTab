/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Logging;

import java.io.PrintStream;

/**
 *
 * @author conno
 */
public class ConnorLogger extends Thread {

    private static PriorityLevel currentPriority = PriorityLevel.Unset;
    private static boolean loggingState;
    private static PrintStream output = System.out;

    public static enum PriorityLevel {
        Zero,
        Low,
        Medium,
        High,
        Unset,
    }

    public static void log(String toBeLogged, PriorityLevel priorityLevel) {
        if (loggingState) {
            if (currentPriority.compareTo(priorityLevel) <= 0) {
                output.println(toBeLogged);
            }
        }
    }

    public static void setPriority(PriorityLevel priorityLevel) {
        if (currentPriority == PriorityLevel.Unset) {
            currentPriority = priorityLevel;
        } else {
            log("Priority already set", PriorityLevel.High);
        }
    }

    public static void setLoggingState(boolean loggingState) {
        ConnorLogger.loggingState = loggingState;
    }

}
