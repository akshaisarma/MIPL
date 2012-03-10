package edu.columbia.mipl.mapreduce;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Arrays;
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
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import edu.columbia.mipl.datastr.*;

public class MatrixSplitter {
	static final int NUM_MATRIX_SPLIT = 8;
	static final int MIN_MATRIX_SIZE = NUM_MATRIX_SPLIT * 8;

	public static class MatrixMapper extends MapReduceBase
			implements Mapper<LongWritable, Text, LongWritable, WritableArray> {

		private static final LongWritable newKey = new LongWritable();

		public void map(LongWritable key, Text val,
				OutputCollector<LongWritable, WritableArray> output, Reporter reporter)
				throws IOException {

			int n = 1;
			WritableArray array = new WritableArray(1, n, key.get());

			String line = val.toString();
			StringTokenizer itr = new StringTokenizer(line.toLowerCase());
			while (itr.hasMoreTokens()) {

				if (array.getCol() < n)
					array.increaseCol();

				array.setValue(0, n - 1, Double.parseDouble(itr.nextToken()));
			}

			if (array.getCol() < MIN_MATRIX_SIZE) {
				output.collect(key, array);
				return;
			}

			int nSplitCols = ((array.getCol() - 1) / NUM_MATRIX_SPLIT) + 1;
			// int paddedLength = nSplitCols * NUM_MATRIX_SPLIT;
			double[] data = array.getData();
			for (n = 0; n < NUM_MATRIX_SPLIT; n++) {
				newKey.set(n);
				// TODO: adjust nSplitCols for the last unaligned data!
				output.collect(newKey, new WritableArray(1, nSplitCols, Arrays.copyOfRange(data, n * nSplitCols, nSplitCols), key.get()));
			}
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
			while (values.hasNext()) {
				WritableArray array = values.next();
				sortedMap.put(array.getPos(), array);
			}
			int nSplitRows = ((sortedMap.size() - 1) / NUM_MATRIX_SPLIT) + 1;
			// int nPaddedRows = nSplitRows * NUM_MATRIX_SPLIT;

			int i = 0;
			int n = 0;
			WritableArray represent = null;
			for (WritableArray array : sortedMap.values()) {
				if (represent == null)
					represent = array;
				else
					represent.mergeVertically(array);
				i++;
				if (i == nSplitRows) {
					output.collect(new WritableIndex(n, key.get()), represent);
					n++;
					represent = null;
				}
			}
			if (represent != null)
				output.collect(new WritableIndex(n, key.get()), represent);
		}
	}


	/**
	 * The actual main() method for our program; this is the
	 * "driver" for the MapReduce job.
	 */
	public static void main(String[] args) {
		JobClient client = new JobClient();
		JobConf conf = new JobConf(MatrixSplitter.class);

		conf.setJobName("MatrixSplitter");

		conf.setOutputKeyClass(WritableIndex.class);
		conf.setOutputValueClass(WritableArray.class);

		FileInputFormat.addInputPath(conf, new Path("input"));
		FileOutputFormat.setOutputPath(conf, new Path("output"));

		conf.setMapperClass(MatrixMapper.class);
		conf.setReducerClass(MatrixReducer.class);

		client.setConf(conf);

		try {
			JobClient.runJob(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
