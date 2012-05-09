package edu.columbia.mipl.mapreduce;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

import edu.columbia.mipl.mapreduce.MapReduceMatrixOp.MatrixMultiplyMapper;
import edu.columbia.mipl.mapreduce.MapReduceMatrixOp.MatrixMultiplyReducer;
import edu.columbia.mipl.mapreduce.MapReduceMatrixOp.MatrixMultiplySecondMapper;
import edu.columbia.mipl.mapreduce.MapReduceMatrixOp.MatrixMultiplySecondReducer;


public class MapReduceProxy {
	
	public static final int MATRIX_ABS = 1;
	public static final int MATRIX_ADD = 2;
	public static final int MATRIX_SUB = 3;
	public static final int MATRIX_MUL = 4;
	public static final int MATRIX_DIV = 5;
	public static final int MATRIX_CELLMUL = 6;
	public static final int MATRIX_CELLDIV = 7;
	public static final int MATRIX_ADD_DOUBLE = 8;
	public static final int MATRIX_SUB_DOUBLE = 9;
	public static final int MATRIX_MUL_DOUBLE = 10;
	public static final int MATRIX_DIV_DOUBLE = 11;
	/* refer to : http://gdfm.me/2011/06/10/iterative-algorithms-in-hadoop/ */
	/* giving properties : http://grokbase.com/t/hadoop/mapreduce-user/119f7mv2ft/passing-a-global-variable-into-a-mapper */
	/* properties can be used of scalar value delivery ex) 5 + Matrix  ex) reversedIndex for 2nd argurment of Multiplication */

	/* 2.10. How do I change final o
	 * utput file name with the desired name rather than in partitions like part-00000, part-00001?
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
		job(MapReduceMatrixOp.class, WritableIndex.class, WritableArray.class,
			MapReduceMatrixOp.MatrixMapper.class, MapReduceMatrixOp.MatrixReducer.class,
			outputPath, inputPath);
	}
	
	private void job(Class jobClass, JobClient client, JobConf conf) {
		conf.setJobName(MapReduceMatrixOp.class.getName());
		
		conf.setMapOutputKeyClass(LongWritable.class);
		conf.setMapOutputValueClass(WritableArray.class);

		conf.setOutputKeyClass(WritableIndex.class);
		conf.setOutputValueClass(WritableArray.class);


		conf.setMapperClass(MapReduceMatrixOp.MatrixMapper.class);
		conf.setReducerClass(MapReduceMatrixOp.MatrixReducer.class);

		client.setConf(conf);
		

	}

	public void abs(String inputPath1, String outputPath) {
		try {
			System.out.println("MapReduce : Abs");
//			showFile(inputPath1);
			JobClient client = new JobClient();
			JobConf conf = new JobConf(MapReduceMatrixOp.class);
			
			job(MapReduceMatrixOp.class, client, conf);
			
			conf.set("input1", inputPath1);
			conf.setInt("op", MapReduceProxy.MATRIX_ABS);
			conf.setJobName("MapReduceMatrixOp");
			
			FileInputFormat.addInputPath(conf, new Path(inputPath1));
			FileOutputFormat.setOutputPath(conf, new Path(outputPath));

			JobClient.runJob(conf);
//			System.out.println("Output");
//			showFile(outputPath + "/part-00000");

		} catch (Exception e) {
			e.printStackTrace();
		}

		// job(MapReduceMatrixOper.class, WritableIndex.class, WritableArray.class,
		//	MapReduceMatrixOper.MatrixMapper.class, MapReduceMatrixOper.MatrixReducer.class,
		//	outputPath, inputPath1, inputPath2);
	}
	
	public void showFile(String input) {
		System.out.println("===========================================");
		System.out.println("File Name = " + input);
		
		try {
			FileInputStream fstream = new FileInputStream(input);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			while ((strLine = br.readLine()) != null)   {
				  System.out.println(strLine);
			}
		} catch (Exception e) {
			
		}
	}

	public void add(String inputPath1, String inputPath2, String outputPath) {
		try {
			System.out.println("MapReduce : Add");
//			showFile(inputPath1);
//			showFile(inputPath2);
			JobClient client = new JobClient();
			JobConf conf = new JobConf(MapReduceMatrixOp.class);
			
			job(MapReduceMatrixOp.class, client, conf);
			
			conf.set("input1", inputPath1);
			conf.set("input2", inputPath2);
			conf.setInt("op", MapReduceProxy.MATRIX_ADD);
			conf.setJobName("MapReduceMatrixOp");

			
			FileInputFormat.addInputPath(conf, new Path(inputPath1));
			FileInputFormat.addInputPath(conf, new Path(inputPath2));
			FileOutputFormat.setOutputPath(conf, new Path(outputPath));

			JobClient.runJob(conf);
			
//			System.out.println("Output");
//			showFile(outputPath + "/part-00000");

		} catch (Exception e) {
			e.printStackTrace();
		}

		// job(MapReduceMatrixOper.class, WritableIndex.class, WritableArray.class,
		//	MapReduceMatrixOper.MatrixMapper.class, MapReduceMatrixOper.MatrixReducer.class,
		//	outputPath, inputPath1, inputPath2);
	}
	
	public void add(String inputPath1, double arg2, String outputPath) {
		try {
			System.out.println("MapReduce : Add");
//			showFile(inputPath1);
			JobClient client = new JobClient();
			JobConf conf = new JobConf(MapReduceMatrixOp.class);
			
			job(MapReduceMatrixOp.class, client, conf);
			
			conf.set("input1", inputPath1);
			conf.setInt("op", MapReduceProxy.MATRIX_ADD_DOUBLE);
			conf.setFloat("operand", (float) arg2);
//			conf.se
			conf.setJobName("MapReduceMatrixOp");

			
			FileInputFormat.addInputPath(conf, new Path(inputPath1));
			FileOutputFormat.setOutputPath(conf, new Path(outputPath));

			JobClient.runJob(conf);
			
//			System.out.println("Output");
//			showFile(outputPath + "/part-00000");

		} catch (Exception e) {
			e.printStackTrace();
		}

		// job(MapReduceMatrixOper.class, WritableIndex.class, WritableArray.class,
		//	MapReduceMatrixOper.MatrixMapper.class, MapReduceMatrixOper.MatrixReducer.class,
		//	outputPath, inputPath1, inputPath2);
	}
	
	public void sub(String inputPath1, double arg2, String outputPath) {
		try {
			System.out.println("MapReduce : Sub");
//			showFile(inputPath1);
			JobClient client = new JobClient();
			JobConf conf = new JobConf(MapReduceMatrixOp.class);
			
			job(MapReduceMatrixOp.class, client, conf);
			
			conf.set("input1", inputPath1);
			conf.setInt("op", MapReduceProxy.MATRIX_SUB_DOUBLE);
			conf.setFloat("operand", (float) arg2);
//			conf.se
			conf.setJobName("MapReduceMatrixOp");

			
			FileInputFormat.addInputPath(conf, new Path(inputPath1));
			FileOutputFormat.setOutputPath(conf, new Path(outputPath));

			JobClient.runJob(conf);
			
			System.out.println("Done");
//			System.out.println("Output");
//			showFile(outputPath + "/part-00000");

		} catch (Exception e) {
			e.printStackTrace();
		}

		// job(MapReduceMatrixOper.class, WritableIndex.class, WritableArray.class,
		//	MapReduceMatrixOper.MatrixMapper.class, MapReduceMatrixOper.MatrixReducer.class,
		//	outputPath, inputPath1, inputPath2);
	}
	
	
	public void div(String inputPath1, double arg2, String outputPath) {
		try {
			System.out.println("MapReduce : Div");
//			showFile(inputPath1);
			JobClient client = new JobClient();
			JobConf conf = new JobConf(MapReduceMatrixOp.class);
			
			job(MapReduceMatrixOp.class, client, conf);
			
			conf.set("input1", inputPath1);
			conf.setInt("op", MapReduceProxy.MATRIX_DIV_DOUBLE);
			conf.setFloat("operand", (float) arg2);
//			conf.se
			conf.setJobName("MapReduceMatrixOp");

			
			FileInputFormat.addInputPath(conf, new Path(inputPath1));
			FileOutputFormat.setOutputPath(conf, new Path(outputPath));

			JobClient.runJob(conf);
			
//			System.out.println("Output");
//			showFile(outputPath + "/part-00000");

		} catch (Exception e) {
			e.printStackTrace();
		}

		// job(MapReduceMatrixOper.class, WritableIndex.class, WritableArray.class,
		//	MapReduceMatrixOper.MatrixMapper.class, MapReduceMatrixOper.MatrixReducer.class,
		//	outputPath, inputPath1, inputPath2);
	}
	
	

	public void sub(String inputPath1, String inputPath2, String outputPath) {
		try {
			System.out.println("MapReduce : Sub");
//			showFile(inputPath1);
//			showFile(inputPath2);
			JobClient client = new JobClient();
			JobConf conf = new JobConf(MapReduceMatrixOp.class);
			
			job(MapReduceMatrixOp.class, client, conf);
			
			conf.set("input1", inputPath1);
			conf.set("input2", inputPath2);
			conf.setInt("op", MapReduceProxy.MATRIX_SUB);
			conf.setJobName("MapReduceMatrixOp");
			
			FileInputFormat.addInputPath(conf, new Path(inputPath1));
			FileInputFormat.addInputPath(conf, new Path(inputPath2));
			FileOutputFormat.setOutputPath(conf, new Path(outputPath));

			JobClient.runJob(conf);
			System.out.println("Done");
//			System.out.println("Output");
//			showFile(outputPath + "/part-00000");

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public void cellmul(String inputPath1, String inputPath2, String outputPath) {
		try {
			System.out.println("MapReduce : CellMul");
//			showFile(inputPath1);
//			showFile(inputPath2);
			JobClient client = new JobClient();
			JobConf conf = new JobConf(MapReduceMatrixOp.class);
			
			job(MapReduceMatrixOp.class, client, conf);
			
			conf.set("input1", inputPath1);
			conf.set("input2", inputPath2);
			conf.setInt("op", MapReduceProxy.MATRIX_CELLMUL);
			conf.setJobName("MapReduceMatrixOp");
			
			FileInputFormat.addInputPath(conf, new Path(inputPath1));
			FileInputFormat.addInputPath(conf, new Path(inputPath2));
			FileOutputFormat.setOutputPath(conf, new Path(outputPath));

			JobClient.runJob(conf);
//			System.out.println("Output");
//			showFile(outputPath + "/part-00000");

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public void celldiv(String inputPath1, String inputPath2, String outputPath) {
		try {
			System.out.println("MapReduce : CellDiv");
//			showFile(inputPath1);
//			showFile(inputPath2);
			JobClient client = new JobClient();
			JobConf conf = new JobConf(MapReduceMatrixOp.class);
			
			job(MapReduceMatrixOp.class, client, conf);
			
			conf.set("input1", inputPath1);
			conf.set("input2", inputPath2);
			conf.setInt("op", MapReduceProxy.MATRIX_CELLDIV);
			conf.setJobName("MapReduceMatrixOp");
			
			FileInputFormat.addInputPath(conf, new Path(inputPath1));
			FileInputFormat.addInputPath(conf, new Path(inputPath2));
			FileOutputFormat.setOutputPath(conf, new Path(outputPath));

			JobClient.runJob(conf);
//			System.out.println("Output");
//			showFile(outputPath + "/part-00000");

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void mul(String inputPath1, String inputPath2, String outputPath) {
		try {
			System.out.println("MapReduce : Mul");
//			showFile(inputPath1);
//			showFile(inputPath2);
//			JobClient client = new JobClient();
			JobConf conf = new JobConf(MapReduceMatrixOp.class);
			
//			job(MapReduceMatrixOp.class, client, conf);
			
			conf.set("input1", inputPath1);
			conf.set("input2", inputPath2);
			conf.setInt("op", MapReduceProxy.MATRIX_MUL);
			conf.setJobName("MapReduceMatrixOp");
			
			FileInputFormat.addInputPath(conf, new Path(inputPath1));
			FileInputFormat.addInputPath(conf, new Path(inputPath2));
			FileOutputFormat.setOutputPath(conf, new Path("intermediate"));
			conf.setMapOutputKeyClass(LongWritable.class);
			conf.setMapOutputValueClass(WritableArray.class);

			conf.setOutputKeyClass(WritableIndex.class);
			conf.setOutputValueClass(WritableArray.class);

			conf.setMapperClass(MatrixMultiplyMapper.class);
			conf.setReducerClass(MatrixMultiplyReducer.class);

			
			JobConf conf2 = new JobConf(MapReduceMatrixOp.class);
			conf2.setMapOutputKeyClass(WritableIndex.class);
			conf2.setMapOutputValueClass(WritableArray.class);

			conf2.setOutputKeyClass(WritableIndex.class);
			conf2.setOutputValueClass(WritableArray.class);

			conf2.setMapperClass(MatrixMultiplySecondMapper.class);
			conf2.setReducerClass(MatrixMultiplySecondReducer.class);
			FileInputFormat.addInputPath(conf2, new Path("intermediate"));
			FileOutputFormat.setOutputPath(conf2, new Path(outputPath));


			JobClient.runJob(conf);
//			System.out.println("EndReduce");
			JobClient.runJob(conf2);

//			System.out.println("Output");
//			showFile(outputPath + "/part-00000");

		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}

	public void merge() {
		// job(MatrixMerger.class, LongWritable.class, Text.class,
		// 	MatrixMerger.MatrixMapper.class, MatrixMerger.MatrixReducer.calss
		// 	outputPath, inputPath);
	}
}
