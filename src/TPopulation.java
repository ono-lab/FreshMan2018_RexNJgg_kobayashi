import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class TPopulation {
	/** TIndividualを格納する配列 */
	private TIndividual[] fIndividuals;

	/**
	 * コンストラクタ
	 */
	public TPopulation() {
		fIndividuals = new TIndividual[0];
	}

	/**
	 * 配列サイズを初期化する際にセットするコンストラクタ
	 * @param populationSize : int
	 */
	public TPopulation(int populationSize) {
		fIndividuals = new TIndividual[populationSize];

		for(int i = 0; i < getPopulationSize(); i++) {
			fIndividuals[i] = new TIndividual();
		}
	}

	/**
	 * コピーコンストラクタ
	 * @param src : TIndividual
	 */
	public TPopulation(TPopulation src) {
		fIndividuals = new TIndividual[src.getPopulationSize()];
		for(int i = 0; i < getPopulationSize(); i++) {
			fIndividuals[i] = new TIndividual(src.fIndividuals[i]);
		}
	}

	/**
	 * 自身と同じインスタンスを生成する
	 */
	@Override
	public TPopulation clone() {
		return new TPopulation(this);
	}

	/**
	 * 引数のオブジェクトをディープコピーする
	 * @param src : TPopulation コピーしたいオブジェクト
	 * @return コピーされたオブジェクト
	 */
	public TPopulation copyFrom(TPopulation src) {
		setPopulationSize(src.fIndividuals.length);
		for(int i = 0; i < getPopulationSize(); i++) {
			fIndividuals[i].copyFrom(src.fIndividuals[i]);
		}

		return this;
	}

	/**
	 * ファイルにpoulationSize,fIndifidualの情報を書き込む
	 * @param pw : PrintWriter
	 */
	public void writeTo(PrintWriter pw) {
		pw.println(fIndividuals.length);
		for(int i = 0; i < getPopulationSize(); i++) {
			fIndividuals[i].writeTo(pw);
		}
	}

	/**
	 * ファイルから読み込んだ情報からTPopulationを作成
	 * @param br : BufferedReader
	 * @throws IOException
	 */
	public void readFrom(BufferedReader br)throws IOException{
		int popSize = Integer.parseInt(br.readLine());
		setPopulationSize(popSize);
		for(int i = 0; i < getPopulationSize(); i++) {
			fIndividuals[i].readFrom(br);
		}
	}

	/**
	 * TPopulationの情報を文字列に置換する
	 * @return 置換された文字列
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(getPopulationSize() + "\n");
		for(int i = 0; i < getPopulationSize(); i++) {
			str.append(fIndividuals[i].toString() +"\n");
		}

		return str.toString();
	}

	/**
	 * 評価値で昇順にバブルソートする
	 */
	void sortByEvaluationValue() {
		for(int i = 0; i < getPopulationSize()-1; i++) {
			for(int j = i+1; j < getPopulationSize(); j++) {
				if(fIndividuals[i].getEvaluationValue() > fIndividuals[j].getEvaluationValue()) {
					TIndividual tmp = fIndividuals[i];
					fIndividuals[i] = fIndividuals[j];
					fIndividuals[j] = tmp;
				}
			}
		}
	}

	/**
	 * indexで指定されたfIndividualsを返す
	 * @param index : int
	 * @return
	 */
	public TIndividual getTIndividual(int index) {
		return fIndividuals[index];
	}

	/**
	 * 配列数を返す
	 * @return 配列数
	 */
	public int getPopulationSize() {
		return fIndividuals.length;
	}

	/**
	 * populationSizeのセット
	 * @param popSize : int
	 */
	public void setPopulationSize(int popSize) {
		if(getPopulationSize() != popSize) {
			fIndividuals = new TIndividual[popSize];

			for(int i = 0; i < getPopulationSize(); i++) {
				fIndividuals[i] = new TIndividual();
			}
		}
	}
}
