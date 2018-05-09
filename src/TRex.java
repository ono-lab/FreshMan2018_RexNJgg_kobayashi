public class TRex {
	/** 親個体の重心 */
	private TVector fm;
	/** 拡張率 */
	private double fExpansionRatio = 1.0;
	/** 任意の確率乱数 */
	private TRandom epsilon;
	/** 確率分布の形状 */
	public enum ProbDist{
		UNIFORM,
		GAUSSIAN,
	}
	private ProbDist probdist;
	/** 交叉に参加する親個体数 */
	private int noOfparents;


	/**
	 * コンストラクタ
	 * @param dim : int ベクトルの次元数
	 * @param popsize : int
	 * @param noOfparents : int 交叉に参加する親個体数
	 * @param rand : Random 乱数生成器
	 */
	public TRex(int dim, int noOfparents, TRandom rand, ProbDist pd) {
		fm = new TVector();
		fm.setDimension(dim);
		epsilon = rand;
		this.noOfparents = noOfparents;
		probdist = pd;
	}

	/**
	 * 子個体を生成する
	 * @param parent : TPopulation 交叉に参加する親個体
	 * @param children : TPopulation 生成される子個体
	 */
	public void makeOffspring(TPopulation parents, TPopulation children) {//childrenはゼロベクトルを持つと仮定している
		calcCentroid(parents);	//重心を計算
		double sdt = 1/parents.getPopulationSize();

		for(int i = 0; i < children.getPopulationSize(); i++) {
			TPopulation copiedParents = new TPopulation(parents);	//コピーされた親個体
			children.getTIndividual(i).getTVector().add(fm);	//x_i += m

			// シグマ部分の計算
			for(int j = 0; j < noOfparents; j++) {
				copiedParents.getTIndividual(j).getTVector().sub(fm);	//y_j - m

				switch (probdist) {	//alpha*epsilon*(y_j - m)
				case GAUSSIAN:
					copiedParents.getTIndividual(j).getTVector().scalar(fExpansionRatio * sdt * epsilon.nextGaussian());
					break;
				case UNIFORM:
					copiedParents.getTIndividual(j).getTVector().scalar(fExpansionRatio * (2.0 * epsilon.nextDouble() - 1.0));
					break;
				}

				children.getTIndividual(i).getTVector().add(copiedParents.getTIndividual(j).getTVector());	// x_i = m + alpha*epsilon*(y_j - m)
			}
		}
	}

	/**
	 * 重心を計算する
	 * @param p : TPopulation 親個体
	 */
	public void calcCentroid(TPopulation p) {
		//重心の初期化
		fm.setDimension(p.getTIndividual(0).getTVector().getDimension());
		for(int i = 0; i < fm.getDimension(); i++) {
			fm.setElement(i,0.0);
		}

		for(int i = 0; i < p.getPopulationSize(); i++) {
			fm.add(p.getTIndividual(i).getTVector());
		}

		fm.scalar(1.0/ (double)p.getPopulationSize());
	}

	public TVector getfm() {
		return fm;
	}
}
