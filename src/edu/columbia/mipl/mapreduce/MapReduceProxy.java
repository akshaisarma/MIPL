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

	private void job(Class jobClass, Class keyClass, Class valueClass, 
			Class<? extends org.apache.hadoop.mapred.Mapper> mapClass,
			Class<? extends org.apache.hadoop.mapred.Reducer> reduceClass,
			String outputPath, String inputPath) {
		job(jobClass, keyClass, valueClass, mapClass, reduceClass, outputPath, inputPath, null);
	}

	private void job(Class jobClass, Class keyClass, Class valueClass, 
			Class<? extends org.apache.hadoop.mapred.Mapper> mapClass,
			Class<? extends org.apache.hadoop.mapred.Reducer> reduceClass,
			String outputPath, String firstInputPath, String secondInputPath) {

		JobClient client = new JobClient();
		JobConf conf = new JobConf(jobClass);

		conf.setJobName(jobClass.getName());

		conf.setOutputKeyClass(WritableIndex.class);
		conf.setOutputValueClass(WritableArray.class);

		FileInputFormat.addInputPath(conf, new Path(firstInputPath));
		if (secondInputPath != null)
			FileInputFormat.addInputPath(conf, new Path(secondInputPath));

		FileOutputFormat.setOutputPath(conf, new Path(outputPath));

		conf.setMapperClass(mapClass);
		conf.setReducerClass(reduceClass);

		client.setConf(conf);

		try {
			JobClient.runJob(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// waitForComplete?
	}

	public void split(String inputPath, String outputPath) {
		job(MatrixSplitter.class, WritableIndex.class, WritableArray.class,
			MatrixSplitter.MatrixMapper.class, MatrixSplitter.MatrixReducer.class,
			outputPath, inputPath);
	}

	public void add(String inputPath1, String inputPath2, String outputPath) {
		// job(MatrixAdder.class, WritableIndex.class, WritableArray.class,
		//	MatrixAdder.MatrixMapper.class, MatrixAdder.MatrixReducer.class,
		//	outputPath, inputPath1, inputPath2);
	}

	public void merge() {
		// job(MatrixMerger.class, LongWritable.class, Text.class,
		// 	MatrixMerger.MatrixMapper.class, MatrixMerger.MatrixReducer.calss
		// 	outputPath, inputPath);
	}
}
