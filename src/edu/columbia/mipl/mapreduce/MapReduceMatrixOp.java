package edu.columbia.mipl.mapreduce;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.FileSplit;
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


public class MapReduceMatrixOp {
	static final int NUM_MATRIX_SPLIT = 8;
	static final int MIN_MATRIX_SIZE = NUM_MATRIX_SPLIT * 8;

	private static int count;
	public static int xsize;
	public static int ysize;
	public static int zsize;

	public static class MatrixMapper extends MapReduceBase
	implements Mapper<LongWritable, Text, LongWritable, WritableArray> {

		private static final LongWritable newKey = new LongWritable();

		private static String input1;
		private static String input2;

		private static int operation;
		private static double operand;

		public void map(LongWritable key, Text val,
				OutputCollector<LongWritable, WritableArray> output, Reporter reporter)
						throws IOException {

			FileSplit fs = (FileSplit) reporter.getInputSplit();
			String fileName = fs.getPath().getName();

			//			System.out.println(fs.getPath().getName());
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

			//			System.out.println("fileName = " + input1 + " " + fileName + " " + fileName.endsWith(input1));
			if (fileName.endsWith(input1) || input1.endsWith(fileName)) {
				//				System.out.println("input1?");
				array.setOperation(operation);
				array.setOperand(operand);
			}
			newKey.set(count);
			output.collect(newKey, array);
			count++;
		}

		public void configure(JobConf job) {
			//			System.out.println(job.get("input1"));
			//			System.out.println(job.get("input2"));

			input1 = job.get("input1");
			input2 = job.get("input2");

			operation = job.getInt("op", 0);

			if (operation >= MapReduceProxy.MATRIX_ADD_DOUBLE) {
				operand = job.getFloat("operand", 0);
				//				System.out.println("Operand = " + operand);
			}
			//			System.out.println(operation);
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
	implements Reducer<LongWritable, WritableArray, NullWritable, WritableArray> {
		private static String input1;
		private static String input2;

		private static int operation;


		public void reduce(LongWritable key, Iterator<WritableArray> values,
				OutputCollector<NullWritable, WritableArray> output, Reporter reporter)
						throws IOException {

			WritableArray sumArr = null;
			while (values.hasNext()) {
				WritableArray array = values.next();
				//				System.out.println("array = " + array);

				if (sumArr == null) { 
					sumArr = new WritableArray(array.getRow(), array.getCol(), array.getPos());
					sumArr.setOperation(array.getOperation());
					sumArr.setOperand(array.getOperand());
					//					System.out.println("reduce Operand = " + array.getOperand());
					/*
					for (double dd : array.getData()) {
						System.out.print(dd + " ");
					}
					 */
					//					System.out.println();
					sumArr.copyRange(array, 0, 0, 0, 0, array.getRow(), array.getCol());
					//					System.out.println("sumarr = " + sumArr);

					switch (sumArr.getOperation()) {
					case MapReduceProxy.MATRIX_ABS:
						sumArr = new WritableArray(array.getRow(), array.getCol(), ((PrimitiveDoubleArray) new DefaultMatrixOperations().abs(array)).getData(), array.getPos());
						break;
					case MapReduceProxy.MATRIX_ADD_DOUBLE:
						sumArr = new WritableArray(array.getRow(), array.getCol(), ((PrimitiveDoubleArray) new DefaultMatrixOperations().add(array, array.getOperand())).getData(), array.getPos());
						break;
					case MapReduceProxy.MATRIX_SUB_DOUBLE:
						sumArr = new WritableArray(array.getRow(), array.getCol(), ((PrimitiveDoubleArray) new DefaultMatrixOperations().sub(array, array.getOperand())).getData(), array.getPos());
						break;
					case MapReduceProxy.MATRIX_DIV_DOUBLE:
						sumArr = new WritableArray(array.getRow(), array.getCol(), ((PrimitiveDoubleArray) new DefaultMatrixOperations().div(array, array.getOperand())).getData(), array.getPos());
						break;

					}
				}
				else {

					if (sumArr.isFirstMatrix()) {
						//						System.out.println("sumarr = " + sumArr.getRow() + " " + sumArr.getCol() + " " + sumArr.getData().length);

						switch (sumArr.getOperation()) {
						case MapReduceProxy.MATRIX_ADD:
							sumArr = new WritableArray(sumArr.getRow(), sumArr.getCol(), ((PrimitiveDoubleArray) new DefaultMatrixOperations().add(sumArr, array)).getData(), sumArr.getPos());						
							break;
						case MapReduceProxy.MATRIX_SUB:
							sumArr = new WritableArray(sumArr.getRow(), sumArr.getCol(), ((PrimitiveDoubleArray) new DefaultMatrixOperations().sub(sumArr, array)).getData(), sumArr.getPos());						
							break;
						case MapReduceProxy.MATRIX_CELLMUL:
							sumArr = new WritableArray(sumArr.getRow(), sumArr.getCol(), ((PrimitiveDoubleArray) new DefaultMatrixOperations().cellmult(sumArr, array)).getData(), sumArr.getPos());						
							break;
						case MapReduceProxy.MATRIX_CELLDIV:
							//							System.out.println("SUMARR");
							//							sumArr.printMatrix();
							//							System.out.println("ARRAY");
							//							array.printMatrix();
							sumArr = new WritableArray(sumArr.getRow(), sumArr.getCol(), ((PrimitiveDoubleArray) new DefaultMatrixOperations().celldiv(sumArr, array)).getData(), sumArr.getPos());						
							break;
						}
						//						System.out.println("w sumarr = " + sumArr);
					}
					else {
						//						System.out.println("arr OP = " + array.getOperation());
						//						System.out.println("sum OP = " + sumArr.getOperation());
						switch (array.getOperation()) {
						case MapReduceProxy.MATRIX_ADD:
							sumArr = new WritableArray(sumArr.getRow(), sumArr.getCol(), ((PrimitiveDoubleArray) new DefaultMatrixOperations().add(array, sumArr)).getData(), sumArr.getPos());						
							break;
						case MapReduceProxy.MATRIX_SUB:
							sumArr = new WritableArray(sumArr.getRow(), sumArr.getCol(), ((PrimitiveDoubleArray) new DefaultMatrixOperations().sub(array, sumArr)).getData(), sumArr.getPos());						
							break;
						case MapReduceProxy.MATRIX_CELLMUL:
							sumArr = new WritableArray(sumArr.getRow(), sumArr.getCol(), ((PrimitiveDoubleArray) new DefaultMatrixOperations().cellmult(sumArr, array)).getData(), sumArr.getPos());						
							break;
						case MapReduceProxy.MATRIX_CELLDIV:
							sumArr = new WritableArray(sumArr.getRow(), sumArr.getCol(), ((PrimitiveDoubleArray) new DefaultMatrixOperations().celldiv(sumArr, array)).getData(), sumArr.getPos());						
							break;
						}
						//						System.out.println("here ? sumarr = " + sumArr);
					}
				}

			}
			//			System.out.println("sumarr = " + sumArr);
			//			System.out.println("array = " + array);
			output.collect(NullWritable.get(), sumArr);
		}
		public void configure(JobConf job) {
			//			System.out.println(job.get("input1"));
			//			System.out.println(job.get("input2"));

			input1 = job.get("input1");
			input2 = job.get("input2");

			operation = job.getInt("op", 0);
			//			System.out.println("Getting op = " + operation);
			//			System.out.println(operation);
		}

	}

	public static class MatrixMultiplyMapper extends MapReduceBase
	implements Mapper<LongWritable, Text, LongWritable, WritableArray> {

		private static final LongWritable newKey = new LongWritable();

		private static String input1;
		private static String input2;

		private static int operation;
		private static double operand;

		//		public static WritableArray array2 = null;

		public void map(LongWritable key, Text val,
				OutputCollector<LongWritable, WritableArray> output, Reporter reporter) {

			try {
				//			System.out.println("MAP");

				FileSplit fs = (FileSplit) reporter.getInputSplit();
				String fileName = fs.getPath().getName();

				int n = 1;

				if (key.get() == 0) count = 0;
				WritableArray array = new WritableArray(1, n, key.get());

				String line = val.toString();
				StringTokenizer itr = new StringTokenizer(line.toLowerCase());
				if (fileName.endsWith(input1) || input1.endsWith(fileName)) {
					while (itr.hasMoreTokens()) {

						if (array.getCol() < n)
							array.increaseCol();

						array.setValue(0, n - 1, Double.parseDouble(itr.nextToken()));
						n++;
					}


					array.setOperation(operation);
					array.setOperand(operand);
					newKey.set(count);
					output.collect(newKey, array);
					count++;
					//				System.out.println("input 1 = " + count);

					ysize = count;

					//				System.out.println("ysize = " + ysize);

				}
				else {
					int newCount = 0;
					while (itr.hasMoreTokens()) {


						array.setValue(0, 0, Double.parseDouble(itr.nextToken()));
						array.setRowPos(count);
						//					n++;

						newKey.set(newCount);
						output.collect(newKey, array);
						newCount++;

					}
					xsize = newCount;
					count++;
					zsize = count;
					//				System.out.println("xsize " + xsize);
					//				System.out.println("file name " + fileName);
					//				System.out.println("input 2 = " + count);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void configure(JobConf job) {
			//			System.out.println(job.get("input1"));
			//			System.out.println(job.get("input2"));

			input1 = job.get("input1");
			input2 = job.get("input2");

			operation = job.getInt("op", 0);

			if (operation >= MapReduceProxy.MATRIX_ADD_DOUBLE) {
				operand = job.getFloat("operand", 0);
				//				System.out.println("Operand = " + operand);
			}
			//			System.out.println(operation);
		}
	}
	public static class MatrixMultiplyReducer extends MapReduceBase
	implements Reducer<LongWritable, WritableArray, NullWritable, WritableArray> {
		private static String input1;
		private static String input2;

		private static int operation;
		private static int countEl;


		public void reduce(LongWritable key, Iterator<WritableArray> values,
				OutputCollector<NullWritable, WritableArray> output, Reporter reporter) {

			try {
				//			System.out.println("Reduce");
				//			System.out.println("x size = " + xsize);
				//			System.out.println("y size = " + ysize);
				WritableArray sumArr = null;
				WritableArray gatArr = null;

				while (values.hasNext()) {
					WritableArray array = values.next();
					//				System.out.println(key + " array = " + array);
					//				System.out.println("xsize = " + xsize);
					//				System.out.println(array.getSize() + " " + ysize);
					//				System.out.println("zsize = " + zsize);

					//				System.out.println(array.getSize());
					//				System.out.println(array.getRowPos());

					//				output.collect(NullWritable.get(), array);
					if (array.getSize() != zsize) {
						if (gatArr == null) {
							gatArr = new WritableArray(array.getRow(), zsize + 3, array.getPos());
							gatArr.setOperation(array.getOperation());
							gatArr.setOperand(array.getOperand());
							countEl = 0;
						}
						countEl++;
						double[] d = array.getData();
						gatArr.putData(d[0], 0, array.getRowPos() + 3);

						if (countEl == zsize) {
//							System.out.println("gatArr = " + gatArr);
							gatArr.putData(ysize, 0, 0);
							gatArr.putData(-1, 0, 1);
							gatArr.putData(key.get(), 0, 2);

							output.collect(NullWritable.get(), gatArr);
						}
					}
					else if (array.getSize() == zsize) {
						if (sumArr == null) {

							sumArr = new WritableArray(array.getRow(), array.getCol() + 3, array.getPos());
							sumArr.setOperation(array.getOperation());
							sumArr.setOperand(array.getOperand());
							//						sumArr.co
							sumArr.copyRange(array, 0, 0, 0, 3, array.getRow(), array.getCol());
						}
						sumArr.putData(xsize, 0, 0);
						sumArr.putData(key.get(), 0, 1);
						sumArr.putData(-1, 0, 2);
						//					System.out.println("sumarr = " + sumArr);

						output.collect(NullWritable.get(), sumArr);
					}


				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//			System.out.println("sumarr = " + sumArr);
			//			System.out.println("array = " + array);
			//			output.collect(NullWritable.get(), sumArr);
		}
		public void configure(JobConf job) {
			//			System.out.println(job.get("input1"));
			//			System.out.println(job.get("input2"));

			input1 = job.get("input1");
			input2 = job.get("input2");

			operation = job.getInt("op", 0);
			//			System.out.println("Getting op = " + operation);
			//			System.out.println(operation);
		}

	}

	public static class MatrixMultiplySecondMapper extends MapReduceBase
	implements Mapper<LongWritable, Text, WritableIndex, WritableArray> {

		private static final LongWritable newKey = new LongWritable();

		private static String input1;
		private static String input2;

		private static int operation;
		private static double operand;

		//		public static WritableArray array2 = null;

		public void map(LongWritable key, Text val,
				OutputCollector<WritableIndex, WritableArray> output, Reporter reporter) {
			try {

//				System.out.println("NoMap!");

				String line = val.toString();
				StringTokenizer itr = new StringTokenizer(line.toLowerCase());
				int forTime = (int) Double.parseDouble(itr.nextToken());
				//			int pos = Integer.parseInt(itr.nextToken());
				int n = 1;
				WritableArray array = new WritableArray(1, n, key.get());
				double r, c;
				r = -1;
				c = -1;
				double count = 0;
				boolean chk1, chk2;
				chk1 = false;
				chk2 = false;
				while (itr.hasMoreTokens()) {

					if (array.getCol() < n)
						array.increaseCol();

					if (!chk1) {
						r = Double.parseDouble(itr.nextToken());
						array.setValue(0, n - 1, r);
						chk1 = true;
					}
					else if (!chk2) {
						c = Double.parseDouble(itr.nextToken());
						array.setValue(0, n - 1, c);
						chk2 = true;
					}
					else
						array.setValue(0, n - 1, Double.parseDouble(itr.nextToken()));
					n++;

				}

				if (c == -1) {
					//				key.set((long) r);
					for (int i = 0; i < forTime; i++) {
						WritableIndex wi = new WritableIndex((long) r, i);
						array.putData(i, 0, 1);
						output.collect(wi, array);
					}
				}
				else {
					for (int i = 0; i < forTime; i++) {
						array.putData(i, 0, 0);
						WritableIndex wi = new WritableIndex(i, (long) c);

						//					key.set(i);
						output.collect(wi, array);
					}
				}
				//			key.set(pos);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static class MatrixMultiplySecondReducer extends MapReduceBase
	implements Reducer<WritableIndex, WritableArray, NullWritable, WritableArray> {
		private static String input1;
		private static String input2;

		private static int operation;
		private static int flush = 0;
		private WritableArray wa;


		public void reduce(WritableIndex key, Iterator<WritableArray> values,
				OutputCollector<NullWritable, WritableArray> output, Reporter reporter) {

			try {

				if (flush == 0) {
					wa = new WritableArray(1, 1, 0);
					flush = 1;
				}
				WritableArray sumArr = null;
				while (values.hasNext()) {
					double val = 0;
					WritableArray array = values.next();
//					System.out.println(array);
					if (sumArr == null) {
//						System.out.println(array.getRow() + " " + array.getCol() + " " + array.getPos());
						sumArr = new WritableArray(array.getRow(), array.getCol() - 2, array.getPos());
//						System.out.println(array.getRow() + " " + array.getCol() + " " + array.getPos());
						sumArr.copyRange(array, 0, 2, 0, 0, array.getRow(), array.getCol() - 2);
//						System.out.println("here??");
					}
					else {
						//					System.out.println("now");
						double[]a = sumArr.getData();
						double[]b = array.getData();


						//					for (int i = 0; i < sumArr.getCol(); i++)
						//						System.out.print(a[i] + " ");
						//					System.out.println();

						for (int i = 2; i < array.getCol(); i++)
							val += a[i - 2] * b[i];
						//						System.out.print(b[i] + " ");
						//					System.out.println();
						//					System.out.println("end");



						//					System.out.println("wacol " + wa.getCol());
						if (wa.getCol() <= key.getCol())
							wa.increaseCol();

						wa.setValue(0, key.getCol(), val);

						//					System.out.println("val = " + val);
						//					System.out.println(key.getCol() + " " + wa);
						if (key.getCol() == xsize - 1) {
							//						System.out.println(key.getCol() + " " + wa);
							output.collect(NullWritable.get(), wa);
							flush = 0;
						}

						//					output.collect(NullWritable.get(), wa);

					}
					//				System.out.println(key + " " +  sumArr);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The actual main() method for our program; this is the
	 * "driver" for the MapReduce job.
	 */
	public static void main(String[] args) {

		File folder = new File("output");
		if (folder.exists()) {
			for (File c : folder.listFiles())
				c.delete();
			folder.delete();
		}
		File folder2 = new File("intermediate");
		if (folder2.exists()) {
			for (File c : folder2.listFiles())
				c.delete();
			folder2.delete();
		}
		//		JobClient client = new JobClient();
		JobConf conf = new JobConf(MapReduceMatrixOp.class);


		conf.set("input1", "haha.txt");
		conf.set("input2", "haha2.txt");
		conf.setInt("op", MapReduceProxy.MATRIX_ADD);
		conf.setJobName("MatrixSplitter");

		conf.setMapOutputKeyClass(LongWritable.class);
		conf.setMapOutputValueClass(WritableArray.class);

		conf.setOutputKeyClass(WritableIndex.class);
		conf.setOutputValueClass(WritableArray.class);

		FileInputFormat.addInputPath(conf, new Path("haha.txt"));
		FileInputFormat.addInputPath(conf, new Path("haha2.txt"));
		FileOutputFormat.setOutputPath(conf, new Path("intermediate"));

		/*
		JobConf mapAConf = new JobConf(false);
		ChainMapper.addMapper(conf, MatrixMultiplyMapper.class, LongWritable.class, Text.class, LongWritable.class, WritableArray.class, false, mapAConf);

		JobConf reduceConf = new JobConf(false);
		ChainReducer.setReducer(conf, MatrixMultiplyReducer.class, LongWritable.class, WritableArray.class, NullWritable.class, WritableArray.class, false, reduceConf);

		JobConf mapBConf = new JobConf(false);
		ChainMapper.addMapper(conf, MatrixMultiplySecondMapper.class, LongWritable.class, WritableArray.class, LongWritable.class, WritableArray.class, false, mapBConf);
		 */


		//		ChainMapper.
		conf.setMapperClass(MatrixMultiplyMapper.class);
		//		conf.setPartitionerClass(MatrixMultiplyPartitioner.class);
		conf.setReducerClass(MatrixMultiplyReducer.class);

		//		client.setConf(conf);


		JobConf conf2 = new JobConf(MapReduceMatrixOp.class);
		conf2.setMapOutputKeyClass(WritableIndex.class);
		conf2.setMapOutputValueClass(WritableArray.class);

		conf2.setOutputKeyClass(WritableIndex.class);
		conf2.setOutputValueClass(WritableArray.class);

		conf2.setMapperClass(MatrixMultiplySecondMapper.class);
		conf2.setReducerClass(MatrixMultiplySecondReducer.class);
		FileInputFormat.addInputPath(conf2, new Path("intermediate"));
		FileOutputFormat.setOutputPath(conf2, new Path("output"));

		try {
			//			Job job1 = new Job(conf); 
			//			jc.addJob(job1);

			//			Job job2 = new Job(conf2);
			//			jc.addJob(job2);
			//			job2.addDependingJob(job1);

			//			jc.run();
			JobClient.runJob(conf);
			JobClient.runJob(conf2);


			//			JobClient.runJob(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
