package myjgoalga;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class TIndividual {
	/**  評価値 */
	private double fEvaluationValue;	//+∞で実行不可能
	/** 実数値ベクトル */
	private TVector fVector;

	/**
	 * コンストラクタ
	 */
	public TIndividual() {
		fEvaluationValue = Double.NaN;
		fVector = new TVector();
	}

	/**
	 * コピーコンストラクタ
	 * @param src 実数値ベクトル
	 */
	public TIndividual(TIndividual src) {
		fEvaluationValue = src.fEvaluationValue;
		fVector = new TVector(src.fVector);
	}

	public TIndividual copyFrom(TIndividual src) {
		setEvaluationValue(src.fEvaluationValue);
		fVector.copyFrom(src.fVector);

		return this;
	}

	/**
	 * 新しくTindividualを生成する
	 */
	@Override
	public TIndividual clone() {
		return new TIndividual(this);
	}

	public void writeTo(PrintWriter pw) {
		pw.println(fEvaluationValue);
		fVector.writeTo(pw);
	}

	public void readFrom(BufferedReader br)throws IOException{
		fEvaluationValue = Double.parseDouble(br.readLine());
		fVector.readFrom(br);
	}

	@Override
	public String toString() {
		String str = fEvaluationValue + "\n";
		str += fVector.toString();

		return str;
	}

	public double getEvaluationValue() {
		return fEvaluationValue;
	}

	public void setEvaluationValue(double eval) {
		fEvaluationValue = eval;
	}

	public TVector getTVector() {
		return fVector;
	}
}