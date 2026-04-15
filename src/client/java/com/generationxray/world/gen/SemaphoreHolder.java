package com.generationxray.world.gen;

import java.util.concurrent.Semaphore;

public interface SemaphoreHolder {
    Semaphore getMutex();
}
