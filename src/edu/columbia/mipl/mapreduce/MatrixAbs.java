package edu.columbia.mipl.mapreduce;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Partitioner;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import edu.columbia.mipl.datastr.PrimitiveDoubleArray;
import edu.columbia.mipl.matops.DefaultMatrixOperations;


public class MatrixAbs {
	static final int NUM_MATRIX_SPLIT = 8;
	static final int MIN_MATRIX_SIZE = NUM_MATRIX_SPLIT * 8;

	private static int count;
	public static class MatrixMapper extends MapReduceBase
	implements Mapper<LongWritable, Text, LongWritable, WritableArray> {

		private static final LongWritable newKey = new LongWritable();

		public void map(LongWritable key, Text val,
				OutputCollector<LongWritable, WritableArray> output, Reporter reporter)
						throws IOException {

			int n = 1;

			if (key.get() == 0) count = 0;
			WritableArray array = new WritableArray(1, n, key.get());

			String line = val.toString();
			StringTokenizer itr = new StringTokenizer(line.toLowerCase());
			while (itr.hasMoreTokens()) {

				if (array.getCol() < n)
					array.increaseCol();

				array.setValue(0, n - 1, Double.parseDouble(itr.nextToken()));
				n++;
			}
			newKey.set(count);
			output.collect(newKey, array);
			count++;

		}
	}

	private static class MatrixPartitioner extends MapReduceBase
	implements Partitioner<LongWritable, WritableArray> {

		public int getPartition(LongWritable writ, WritableArray arr, int numPartitions) {
			//			System.out.println("getPartition : " + writ.get() + " " + numPartitions);
			return 0;
		}
	}

	public static class MatrixReducer extends MapReduceBase
	implements Reducer<LongWritable, WritableArray, WritableIndex, WritableArray> {

		public void reduce(LongWritable key, Iterator<WritableArray> values,
				OutputCollector<WritableIndex, WritableArray> output, Reporter reporter)
						throws IOException {

			SortedMap<Long, WritableArray> sortedMap = new TreeMap<Long, WritableArray>();

			boolean first = true;
			StringBuilder toReturn = new StringBuilder();
			//			System.out.println("key = " + key.toString());
			//			System.out.println(values.toString());

			WritableArray sumArr = null;
			//			System.out.println("====================================================================");
			while (values.hasNext()) {
				WritableArray array = values.next();

				if (sumArr == null) { 
					sumArr = new WritableArray(array.getRow(), array.getCol(), ((PrimitiveDoubleArray) new DefaultMatrixOperations().abs(array)).getData(), array.getPos());


				}
				else {

				}

			}
			output.collect(new WritableIndex(key.get(), key.get()), sumArr);
		}
	}


	/**
	 * The actual main() method for our program; this is the
	 * "driver" for the MapReduce job.
	 */
	public static void main(String[] args) {

		boolean success = (new File("output")).delete();

		JobClient client = new JobClient();
		JobConf conf = new JobConf(MatrixAbs.class);

		conf.setJobName("MatrixSplitter");

		conf.setMapOutputKeyClass(LongWritable.class);
		conf.setMapOutputValueClass(WritableArray.class);

		conf.setOutputKeyClass(WritableIndex.class);
		conf.setOutputValueClass(WritableArray.class);

		FileInputFormat.addInputPath(conf, new Path("input/input1.csv"));
		FileInputFormat.addInputPath(conf, new Path("input/input2.csv"));
		FileOutputFormat.setOutputPath(conf, new Path("output"));

		conf.setMapperClass(MatrixMapper.class);
		conf.setPartitionerClass(MatrixPartitioner.class);
		//		conf.setCombinerClass(MatrixReducer.class);
		conf.setReducerClass(MatrixReducer.class);

		client.setConf(conf);

		try {
			JobClient.runJob(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
