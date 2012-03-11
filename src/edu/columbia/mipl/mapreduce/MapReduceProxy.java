package edu.columbia.mipl.mapreduce;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

import edu.columbia.mipl.datastr.*;

public class MapReduceProxy {
	/* refer to : http://gdfm.me/2011/06/10/iterative-algorithms-in-hadoop/ */
	/* giving properties : http://grokbase.com/t/hadoop/mapreduce-user/119f7mv2ft/passing-a-global-variable-into-a-mapper */
	/* properties can be used of scalar value delivery ex) 5 + Matrix  ex) reversedIndex for 2nd argurment of Multiplication */
	String inputPath;
	String outputPath;
	String currentPath;
	int cntJob;

	public MapReduceProxy(String inputPath, String outputPath) {
		this.inputPath = inputPath;
		this.outputPath = outputPath;
	}

	private void job(Class jobClass, Class keyClass, Class valueClass, Class mapClass, Class reduceClass) {
		job(jobClass, keyClass, valueClass, mapClass, reduceClass, false);
	}

	private void job(Class jobClass, Class keyClass, Class valueClass, Class mapClass, Class reduceClass, boolean output) {
		cntJob++;
		JobClient client = new JobClient();
		JobConf conf = new JobConf(jobClass);

		conf.setJobName(jobClass.getName());

		conf.setOutputKeyClass(WritableIndex.class);
		conf.setOutputValueClass(WritableArray.class);

		FileInputFormat.addInputPath(conf, new Path(currentPath));

		if (output)
			currentPath = outputPath;
		else 
			currentPath = "MATRIX.temp.job." + jobClass.getName() + "." + cntJob;

		FileOutputFormat.setOutputPath(conf, new Path(currentPath));

		conf.setMapperClass(mapClass);
		conf.setReducerClass(reduceClass);

		client.setConf(conf);

		try {
			JobClient.runJob(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public void split() {
		job(MatrixSplitter.class, WritableIndex.class, WritableArray.class,
			MatrixSplitter.MatrixMapper.class, MatrixSplitter.MatrixReducer.class);
	}

	public void add() {
		// job(MatrixAdder.class, WritableIndex.class, WritableArray.class,
		// 	MatrixAdder.MatrixMapper.class, MatrixAdder.MatrixReducer.calss);
	}

	public void merge() {
		// job(MatrixMerger.class, LongWritable.class, Text.class,
		// 	MatrixMerger.MatrixMapper.class, MatrixMerger.MatrixReducer.calss, true);
	}
}
