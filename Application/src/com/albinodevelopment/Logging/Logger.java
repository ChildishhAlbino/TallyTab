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
public class Logger extends Thread {

    private static PriorityLevel currentPriority;
    private static boolean on;
    private static PrintStream output;
    
    public static enum PriorityLevel {      
        Zero,
        Low,
        Medium,
        High,
        Unset,
    }

    public static void Log(String toBeLogged, PriorityLevel priorityLevel) {
        if (on) {
            if (currentPriority.compareTo(priorityLevel) >= 0) {
                output.println(toBeLogged);
            }
        }
    }

    public static void SetPriority(PriorityLevel priorityLevel) {
        if (currentPriority == PriorityLevel.Unset) {
            currentPriority = priorityLevel;
        } else {
            Log("Priority already set", PriorityLevel.High);
        }
    }

    public static void LoggingOnOrOff(boolean onOrOff){
        on = onOrOff;
    }

}
