package org.jboss.perf;

public enum Collector {

	G1("-XX:+UseG1GC"), SHENANDOAH("-XX:+UseShenandoahGC");
	
	private String argument;
	private Collector(String arg)
	{
		argument = arg;
	}
	public String getArgument(){
		return argument;
	}
}
