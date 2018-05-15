package myjgoalga;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class TVector {
	private static double EPS = 1e-10;
	private double [] fData;

	/**
	 * コンストラクタ
	 */
	public TVector() {
		fData = new double[0];
	}

	public TVector(TVector src) {
		fData = new double[src.fData.length];
		for(int i = 0; i < fData.length; i++) {
			fData[i] = src.fData[i];
		}
	}

	public TVector copyFrom(TVector src) {
		if(fData.length != src.fData.length) {
			fData = new double[src.fData.length];
		}

		for(int i = 0; i < fData.length; i++) {
			fData[i] = src.fData[i];
		}

		return this;
	}

	@Override
	public TVector clone() {
		return new TVector(this);
	}

	public void writeTo(PrintWriter pw) {
		pw.println(fData.length);
		for(int i = 0; i < fData.length; i++) {
			pw.print(fData[i] + " ");
		}
		pw.println();
	}

	public void readFrom(BufferedReader br)throws IOException{
		int dimension = Integer.parseInt(br.readLine());
		setDimension(dimension);

		String[] tokens = br.readLine().split(" ");
		for(int i = 0; i < dimension; i++) {
			fData[i] = Double.parseDouble(tokens[i]);
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < fData.length; i++) {
			str.append(fData[i]+" ");
		}

		return str.toString();
	}

	@Override
	public boolean equals(Object o) {
		TVector v = (TVector)o;
		assert fData.length == v.fData.length;

		for(int i = 0; i < fData.length; i++) {
			if(Math.abs(fData[i] - v.fData[i]) > EPS) {
				return false;
			}
		}

		return true;
	}

	public TVector add(TVector v) {
		assert fData.length == v.fData.length;

		for(int i = 0; i < fData.length; i++) {
			fData[i] += v.fData[i];
		}

		return this;
	}

	public TVector sub(TVector v) {
		assert fData.length == v.fData.length;

		for(int i = 0; i < fData.length; i++) {
			fData[i] -= v.fData[i];
		}

		return this;
	}

	public double calcurateL2Norm() {
		double sum = 0.0;

		for(int i = 0; i < fData.length; i++) {
			sum += fData[i]*fData[i];
		}

		return Math.sqrt(sum);
	}

	/**
	 * ベクトルの正規化
	 * @return 正規化されたベクトル
	 * @throws ArithmeticException ゼロ除算を行った場合
	 */
	public TVector normalize() {
		double l2norm = calcurateL2Norm();

		if(Math.abs(l2norm) < EPS) {
			throw new ArithmeticException();
		}

		for(int i = 0; i < fData.length; i++) {
			fData[i] = fData[i]/l2norm;
		}

		return this;
	}

	public double InnerProduct(TVector v) {
		assert fData.length == v.fData.length;

		double sum = 0.0;

		for(int i = 0; i < fData.length; i++) {
			sum += fData[i] * v.fData[i];
		}

		return sum;
	}

	public TVector scalar(double x) {
		for(int i = 0; i < fData.length; i++) {
			fData[i] = fData[i]*x;
		}

		return this;
	}

	public TVector elementwiseProduct(TVector v) {
		for(int i = 0; i < fData.length; i++) {
			fData[i] = fData[i]*v.fData[i];
		}

		return this;
	}

	public int getDimension() {
		return fData.length;
	}

	public void setDimension(int dimension) {
		if(fData.length != dimension) {
			fData = new double[dimension];
		}
	}

	public double getElement(int index) {
		return fData[index];
	}

	public void setElement(int index, double e) {
		fData[index] = e;
	}
}