package com.nuke.scheme.test;

public class Timer {
	private long m_startTime;
	private long m_elapsedTime;

	public Timer() {
		startTimer();
	}

	public void startTimer() {
		m_startTime = System.nanoTime();
	}

	public double stopTimer() {
		m_elapsedTime = System.nanoTime() - m_startTime;
      return m_elapsedTime / 1000000.0;
	}

	public long getNanoTime() {
		return m_elapsedTime;
	}

	public double getTimeMS() {
		return m_elapsedTime / 1000000.0;
	}
}
