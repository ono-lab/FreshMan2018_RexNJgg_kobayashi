import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class TPopulationTest {
	long seed = 1;
	double EPS = 1e-10;
	Random randint = new Random(seed);
	Random randdouble = new Random(seed);


	@Before
	public void setup() {
	}

	@Test
	void testTPopulation() {
		TPopulation p = new TPopulation();

		assertEquals(0, p.getPopulationSize());
	}

	@Test
	void testTPopulationInt() {
		TPopulation p = new TPopulation(10);

		assertEquals(10, p.getPopulationSize());
	}

	@Test
	void testTPopulationTPopulation() {
		TPopulation p1 = new TPopulation(10);
		//TindividualのEvaluationValueとTVectorの次元をセット
		for(int i = 0; i < p1.getPopulationSize(); i ++) {
			p1.getTIndividual(i).setEvaluationValue(randdouble.nextDouble());
			p1.getTIndividual(i).getTVector().setDimension(10);
		}

		for(int i = 0; i < p1.getPopulationSize(); i++) {
			for(int j = 0; j < p1.getTIndividual(i).getTVector().getDimension(); j++) {
				p1.getTIndividual(i).getTVector().setElement(j, randdouble.nextDouble());
			}
		}

		TPopulation p2 = new TPopulation(p1);

		//PopulationSizeの確認
		assertEquals(p1.getPopulationSize(), p2.getPopulationSize());
		//fIndividualsの確認
		for(int i = 0; i < p1.getPopulationSize(); i++) {
			//fIndividualの評価値の確認
			assertEquals(p1.getTIndividual(i).getEvaluationValue(), p2.getTIndividual(i).getEvaluationValue());
			//fIndividualのTVectorの確認
			assertEquals(true, p1.getTIndividual(i).getTVector().equals(p2.getTIndividual(i).getTVector()));
		}
	}

	@Test
	void testClone() {
		TPopulation p1 = new TPopulation(10);
		//TindividualのEvaluationValueとTVectorの次元をセット
		for(int i = 0; i < p1.getPopulationSize(); i ++) {
			p1.getTIndividual(i).setEvaluationValue(randdouble.nextDouble());
			p1.getTIndividual(i).getTVector().setDimension(10);
		}

		for(int i = 0; i < p1.getPopulationSize(); i++) {
			for(int j = 0; j < p1.getTIndividual(i).getTVector().getDimension(); j++) {
				p1.getTIndividual(i).getTVector().setElement(j, randdouble.nextDouble());
			}
		}

		TPopulation p2 = p1.clone();

		//PopulationSizeの確認
		assertEquals(p1.getPopulationSize(), p2.getPopulationSize());
		//fIndividualsの確認
		for(int i = 0; i < p1.getPopulationSize(); i++) {
			//fIndividualの評価値の確認
			assertEquals(p1.getTIndividual(i).getEvaluationValue(), p2.getTIndividual(i).getEvaluationValue());
			//fIndividualのTVectorの確認
			assertEquals(true, p1.getTIndividual(i).getTVector().equals(p2.getTIndividual(i).getTVector()));
		}
	}

	@Test
	void testCopyFrom() {
		TPopulation p1 = new TPopulation(10);
		//TindividualのEvaluationValueとTVectorの次元をセット
		for(int i = 0; i < p1.getPopulationSize(); i ++) {
			p1.getTIndividual(i).setEvaluationValue(randdouble.nextDouble());
			p1.getTIndividual(i).getTVector().setDimension(10);
		}

		for(int i = 0; i < p1.getPopulationSize(); i++) {
			for(int j = 0; j < p1.getTIndividual(i).getTVector().getDimension(); j++) {
				p1.getTIndividual(i).getTVector().setElement(j, randdouble.nextDouble());
			}
		}

		TPopulation p2 = new TPopulation();
		p2.copyFrom(p1);

		//PopulationSizeの確認
		assertEquals(p1.getPopulationSize(), p2.getPopulationSize());
		//fIndividualsの確認
		for(int i = 0; i < p1.getPopulationSize(); i++) {
			//fIndividualの評価値の確認
			assertEquals(p1.getTIndividual(i).getEvaluationValue(), p2.getTIndividual(i).getEvaluationValue());
			//fIndividualのTVectorの確認
			assertEquals(true, p1.getTIndividual(i).getTVector().equals(p2.getTIndividual(i).getTVector()));
		}
	}

	@Test
	void testWriteTo() {
		TPopulation p1 = new TPopulation(10);
		//TindividualのEvaluationValueとTVectorの次元をセット
		for(int i = 0; i < p1.getPopulationSize(); i ++) {
			p1.getTIndividual(i).setEvaluationValue(randdouble.nextDouble());
			p1.getTIndividual(i).getTVector().setDimension(10);
		}

		//TVectorの中身をセット
		for(int i = 0; i < p1.getPopulationSize(); i++) {
			for(int j = 0; j < p1.getTIndividual(i).getTVector().getDimension(); j++) {
				p1.getTIndividual(i).getTVector().setElement(j, randdouble.nextDouble());
			}
		}

		try {
			//書き込み用の一時的なファイルの作成
			File tmpFile = new File(".\\Pop_writeTo.txt");
			tmpFile.createNewFile();

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tmpFile)));
			p1.writeTo(pw);
			pw.close();

			BufferedReader br = new BufferedReader(new FileReader(tmpFile));

			//PopulationSizeの確認
			assertEquals(p1.getPopulationSize(), Integer.parseInt(br.readLine()));
			//fIndividualsの確認
			for(int i = 0; i < p1.getPopulationSize(); i++) {
				//fIndividualの評価値の確認
				assertEquals(p1.getTIndividual(i).getEvaluationValue(),Double.parseDouble( br.readLine()));
				//fIndividualのTVectorの確認
				assertEquals(p1.getTIndividual(i).getTVector().getDimension(), Integer.parseInt(br.readLine())); //配列長の確認
				assertEquals(p1.getTIndividual(i).getTVector().toString(), br.readLine());
			}
			br.close();

			//ファイルの削除
			if(tmpFile.exists()) {
				tmpFile.delete();
			}
		}catch(IOException e) {
			System.out.println(e);
		}
	}

	@Test
	void testReadFrom() {
		TPopulation p1 = new TPopulation(10);
		//TindividualのEvaluationValueとTVectorの次元をセット
		for(int i = 0; i < p1.getPopulationSize(); i ++) {
			p1.getTIndividual(i).setEvaluationValue(randdouble.nextDouble());
			p1.getTIndividual(i).getTVector().setDimension(10);
		}

		//TVectorの中身をセット
		for(int i = 0; i < p1.getPopulationSize(); i++) {
			for(int j = 0; j < p1.getTIndividual(i).getTVector().getDimension(); j++) {
				p1.getTIndividual(i).getTVector().setElement(j, randdouble.nextDouble());
			}
		}

		TPopulation p2 = new TPopulation();

		try {
			File tmpFile = new File(".\\Pop_readFrom.txt");
			tmpFile.createNewFile();

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tmpFile)));
			p1.writeTo(pw);
			pw.close();

			BufferedReader br = new BufferedReader(new FileReader(tmpFile));
			p2.readFrom(br);
			br.close();

			if(tmpFile.exists()) {
				tmpFile.delete();
			}

		}catch(IOException e) {
			System.out.println(e);
		}

		//PopulationSizeの確認
		assertEquals(p1.getPopulationSize(), p2.getPopulationSize());
		//fIndividualsの確認
		for(int i = 0; i < p1.getPopulationSize(); i++) {
			//fIndividualの評価値の確認
			assertEquals(p1.getTIndividual(i).getEvaluationValue(), p2.getTIndividual(i).getEvaluationValue());
			//fIndividualのTVectorの確認
			assertEquals(p1.getTIndividual(i).getTVector().getDimension(), p2.getTIndividual(i).getTVector().getDimension()); //配列長の確認
			assertEquals(true, p1.getTIndividual(i).getTVector().equals(p2.getTIndividual(i).getTVector()));
		}
	}

	@Test
	void testToString() {
		StringBuilder cmpsb = new StringBuilder();

		TPopulation p1 = new TPopulation();

		p1.setPopulationSize(10);
		cmpsb.append(p1.getPopulationSize()+"\n");

		//TindividualのEvaluationValueとTVectorの次元をセット
		for(int i = 0; i < p1.getPopulationSize(); i ++) {
			p1.getTIndividual(i).setEvaluationValue(randdouble.nextDouble());
			p1.getTIndividual(i).getTVector().setDimension(10);
		}

		//TVectorの中身をセット
		for(int i = 0; i < p1.getPopulationSize(); i++) {
			for(int j = 0; j < p1.getTIndividual(i).getTVector().getDimension(); j++) {
				p1.getTIndividual(i).getTVector().setElement(j, randdouble.nextDouble());
			}

			cmpsb.append(p1.getTIndividual(i).toString()+"\n");
		}

		assertEquals(cmpsb.toString(), p1.toString());
	}

	@Test
	void testSortByEvaluationValue() {
		TPopulation p1 = new TPopulation(10);
		//TindividualのEvaluationValueとTVectorの次元をセット
		for(int i = 0; i < p1.getPopulationSize(); i ++) {
			p1.getTIndividual(i).setEvaluationValue(randdouble.nextDouble());
			p1.getTIndividual(i).getTVector().setDimension(10);
		}

		//TVectorの中身をセット
		for(int i = 0; i < p1.getPopulationSize(); i++) {
			for(int j = 0; j < p1.getTIndividual(i).getTVector().getDimension(); j++) {
				p1.getTIndividual(i).getTVector().setElement(j, randdouble.nextDouble());
			}
		}

		p1.sortByEvaluationValue();

		for(int i = 0; i < p1.getPopulationSize()-1; i++) {
			assertEquals(true, p1.getTIndividual(i).getEvaluationValue() >= p1.getTIndividual(i).getEvaluationValue());
		}
	}

	@Test
	void testGetTIndividual() {
		TPopulation p = new TPopulation(1);

		p.getTIndividual(0).setEvaluationValue(0);
		assertEquals(0, p.getTIndividual(0).getEvaluationValue());
	}

	@Test
	void testGetPopulationSize() {
		TPopulation p = new TPopulation();
		assertEquals(0, p.getPopulationSize());
	}

	@Test
	void testSetPopulationSize() {
		TPopulation p = new TPopulation();
		p.setPopulationSize(0);
		assertEquals(0, p.getPopulationSize());
	}

}
