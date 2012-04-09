package edu.columbia.mipl.mapreduce;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;


public class MapReduceProxy {
	/* refer to : http://gdfm.me/2011/06/10/iterative-algorithms-in-hadoop/ */
	/* giving properties : http://grokbase.com/t/hadoop/mapreduce-user/119f7mv2ft/passing-a-global-variable-into-a-mapper */
	/* properties can be used of scalar value delivery ex) 5 + Matrix  ex) reversedIndex for 2nd argurment of Multiplication */

	/* 2.10. How do I change final output file name with the desired name rather than in partitions like part-00000, part-00001?
	 *  
	 *  You can subclass the OutputFormat.java class and write your own. You can look at the code of TextOutputFormat MultipleOutputFormat.java etc. for reference. It might be the case that you only need to do minor changes to any of the existing Output Format classes. To do that you can just subclass that class and override the methods you need to change. 
	 */
	public MapReduceProxy() {
	}
	
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


	}

	public void split(String inputPath, String outputPath) {
		job(MatrixAdd.class, WritableIndex.class, WritableArray.class,
			MatrixAdd.MatrixMapper.class, MatrixAdd.MatrixReducer.class,
			outputPath, inputPath);
	}
	
	private void job(Class jobClass, JobClient client, JobConf conf) {
		conf.setJobName(MatrixAdd.class.getName());
		
		conf.setMapOutputKeyClass(LongWritable.class);
		conf.setMapOutputValueClass(WritableArray.class);

		conf.setOutputKeyClass(WritableIndex.class);
		conf.setOutputValueClass(WritableArray.class);


		conf.setMapperClass(MatrixAdd.MatrixMapper.class);
		conf.setReducerClass(MatrixAdd.MatrixReducer.class);

		client.setConf(conf);
		

	}

	public void add(String inputPath1, String inputPath2, String outputPath) {
		try {
			JobClient client = new JobClient();
			JobConf conf = new JobConf(MatrixAdd.class);
			
			job(MatrixAdd.class, client, conf);
			
			FileInputFormat.addInputPath(conf, new Path(inputPath1));
			FileInputFormat.addInputPath(conf, new Path(inputPath2));
			FileOutputFormat.setOutputPath(conf, new Path(outputPath));

			JobClient.runJob(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
