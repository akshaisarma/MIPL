package edu.columbia.mipl.mapreduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

import edu.columbia.mipl.datastr.*;

public class WritableArray extends PrimitiveDoubleArray implements Writable {
	long pos;

	public WritableArray(int row, int col, long pos) {
		super(row, col);
		this.pos = pos;
	}

	public WritableArray(int row, int col, double[] data, long pos) {
		super(row, col, data);
		this.pos = pos;
	}

	public long getPos() {
		return pos;
	}

	public void readFields(DataInput in) throws IOException {
		int i;
		int j;
		double[] data = getData();
		int row = getRow();
		int col = getCol();
		int paddedCol = getPaddedCol();

		pos = in.readLong();
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

		out.writeLong(pos);
		for (i = 0; i < row; i++)
			for (j = 0; j < col; j++)
				out.writeDouble(data[i * paddedCol + j]);
	}
}
