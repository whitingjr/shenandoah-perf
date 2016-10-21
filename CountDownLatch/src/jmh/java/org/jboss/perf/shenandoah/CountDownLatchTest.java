package org.jboss.perf.shenandoah;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.jboss.perf.Collector;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
public class CountDownLatchTest {
	
	private CountDownLatch cdl;
	
	@Setup
	public void prepare(){
		cdl = new CountDownLatch(1);
	}
	
	@Benchmark
	public void testAwait(Blackhole bh) 
			throws InterruptedException 
	{
		try {
			bh.consume( cdl.await(4, TimeUnit.SECONDS));
		} finally {
			cdl = new CountDownLatch(1);
		}
	}
	
	public static void main(String[] args) throws RunnerException{
		Options opt = new OptionsBuilder()
				.include(CountDownLatchTest.class.getSimpleName())
				.threads(Runtime.getRuntime().availableProcessors())
				.warmupIterations(2)
                .measurementIterations(5)
                .forks(1)
				.jvmArgs(Collector.G1.getArgument())
				.build();
		new Runner(opt).run();
		System.out.println("Look out for interrupted threads. This indicates the await did not timeout !");
		Options opt2 = new OptionsBuilder()
				.include(CountDownLatchTest.class.getSimpleName())
				.threads(Runtime.getRuntime().availableProcessors())
				.warmupIterations(2)
                .measurementIterations(5)
                .forks(1)
				.jvmArgs(Collector.SHENANDOAH.getArgument())
				.build();
		new Runner(opt).run();
	}
}
