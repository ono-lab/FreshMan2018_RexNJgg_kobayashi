package myjgoalga;

import java.util.Random;

public class TRex {
	/** 親個体の重心 */
	private TVector fXg;
	/** 乱数生成器 */
	private Random fRandom;
	/** 分散 */
	private double fsqrtVariance;

	public TRex(Random random) {
		fRandom = random;
		fXg = new TVector();
	}

	/**
	 * 子を生成する
	 * @param Parents : TPopulation 親個体
	 * @param Children : TPopulation 子個体
	 */
	public void makeOffSpring(TPopulation Parents, TPopulation Children) {
		int parentsSize = Parents.getPopulationSize();	//親の個体数
		int childrenSize = Children.getPopulationSize();	//子の個体数
		int dimension = Parents.getTIndividual(0).getTVector().getDimension();	//個体の次元数

		//重心ベクトルを0にリセット
		fXg.setDimension(dimension);
		for(int i = 0; i < dimension; i++) {
			fXg.setElement(i, 0);
		}

		//重心の計算
		for(int i = 0; i < parentsSize; i++) {
			fXg.add(Parents.getTIndividual(i).getTVector());
		}
		fXg.scalar(1.0/(double)parentsSize);

		//分散のルートの計算
		fsqrtVariance = Math.sqrt(1.0/(double)(parentsSize-1));

		//子をクリア
		clear(Children);

		//子の生成
		for(int i = 0; i < childrenSize; i++) {
			TPopulation copiedParents = new TPopulation(Parents);

			Children.getTIndividual(i).getTVector().add(fXg);	//xi = fXg

			for(int j = 0; j < parentsSize; j++) {
				//epsilon_i,j * (y_j - fXg)
				copiedParents.getTIndividual(j).getTVector().sub(fXg);

				//平均0,分散1/(ParentSize-1)の正規分布
				copiedParents.getTIndividual(j).getTVector().scalar(fsqrtVariance * fRandom.nextGaussian());

				Children.getTIndividual(i).getTVector().add( copiedParents.getTIndividual(j).getTVector());
			}
		}
	}

	public void clear(TPopulation population) {
		int PopulationSize = population.getPopulationSize();
		int Dimension = population.getTIndividual(0).getTVector().getDimension();

		for(int i = 0; i < PopulationSize; i++) {
			for(int j = 0; j < Dimension; j++) {
				population.getTIndividual(i).getTVector().setElement(j, 0.0);
			}
		}
	}
}
