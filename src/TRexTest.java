import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TRexTest { //重心を求めるメソッドのみテスト
	@Test
	public void testcalcCenttroid() {
		TPopulation p1 = new TPopulation(10);
		TVector v = new TVector();

		//TindividualのEvaluationValueとTVectorの次元をセット
		for(int i = 0; i < p1.getPopulationSize(); i ++) {
			p1.getTIndividual(i).setEvaluationValue(1.0);
			p1.getTIndividual(i).getTVector().setDimension(10);
		}
		v.setDimension(p1.getTIndividual(0).getTVector().getDimension());

		//TVectorの中身をセット
		for(int i = 0; i < p1.getPopulationSize(); i++) {
			for(int j = 0; j < p1.getTIndividual(i).getTVector().getDimension(); j++) {
				p1.getTIndividual(i).getTVector().setElement(j, i);
				v.setElement(j, j);
			}
		}

		v.scalar(1/p1.getPopulationSize());

		TRex t = new TRex(p1.getTIndividual(0).getTVector().getDimension(), p1.getPopulationSize(), null,TRex.ProbDist.GAUSSIAN);

		t.calcCentroid(p1);

		assertEquals(true, t.getfm().equals(v));
	}
}
