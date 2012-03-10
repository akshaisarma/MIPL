package edu.columbia.mipl.mapreduce;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

import edu.columbia.mipl.datastr.*;

public class MsTester {
	public static void main(String[] args) {
		/* refer to : http://gdfm.me/2011/06/10/iterative-algorithms-in-hadoop/ */
		JobClient client = new JobClient();
		JobConf conf = new JobConf(MatrixSplitter.class);

		conf.setJobName("MatrixSplitter");

		conf.setOutputKeyClass(WritableIndex.class);
		conf.setOutputValueClass(WritableArray.class);

		FileInputFormat.addInputPath(conf, new Path("input"));
		FileOutputFormat.setOutputPath(conf, new Path("output"));

		conf.setMapperClass(MatrixSplitter.MatrixMapper.class);
		conf.setReducerClass(MatrixSplitter.MatrixReducer.class);

		client.setConf(conf);

		try {
			JobClient.runJob(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
