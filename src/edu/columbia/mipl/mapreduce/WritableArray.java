package edu.columbia.mipl.mapreduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import edu.columbia.mipl.datastr.*;

public class WritableArray extends PrimitiveDoubleArray implements Writable, WritableComparable<WritableArray>  {
	long pos;

	public WritableArray() {

		super(0, 0);
//		System.out.println("WritableArray()");
	}
	
	public WritableArray(int row, int col, long pos) {
		super(row, col);
		this.pos = pos;
//		System.out.println("WritableArray(int row, int col, long pos)");
	}

	public WritableArray(int row, int col, double[] data, long pos) {
		super(row, col, data);
		this.pos = pos;
//		System.out.println("WritableArray(int row, int col, double[] data, long pos)");
	}

	public long getPos() {
		return pos;
	}

	public void readFields(DataInput in) throws IOException {
//		System.out.println("readFields");
		int i;
		int j;
		
		int row = in.readInt();
		int col = in.readInt();
		int paddedCol = in.readInt();
		
		pos = in.readLong();
		
		if (getRow() < row)
			increaseRow(row - getRow());
		if (getCol() < col)
			increaseCol(col - getCol());
		
		double[] data = getData();

		for (i = 0; i < row; i++)
			for (j = 0; j < col; j++)
				data[i * paddedCol + j] = in.readDouble();
	}
	public void write(DataOutput out) throws IOException {
		int i;
		int j;
		double[] data = getData();
		int row = getRow();
		int col = getCol();
		int paddedCol = getPaddedCol();

//		System.out.println("write " + row + " " + col + " " + paddedCol);
		out.writeInt(row);
		out.writeInt(col);
		out.writeInt(paddedCol);

		
		out.writeLong(pos);
		for (i = 0; i < row; i++)
			for (j = 0; j < col; j++)
				out.writeDouble(data[i * paddedCol + j]);
	}

	@Override
	public int compareTo(WritableArray arr) {
		return (int) (arr.pos - pos);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		double[] data = getData();
		int row = getRow();
		int col = getCol();
		int paddedCol = getPaddedCol();
		
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				sb.append(data[i * paddedCol + j] + " ");

		return sb.toString();
	}
}
