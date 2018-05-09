import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.jupiter.api.Test;

class TIndividualTest {
	@Test
	void testclone() {
		TIndividual id1 = new TIndividual();
		id1.setEvaluationValue(1.0);

		id1.getTVector().setDimension(5);
		for(int i = 0; i < id1.getTVector().getDimension();  i++) {
			id1.getTVector().setElement(i, i);
		}

		TIndividual id2 = id1.clone();

		assertEquals(id1.getEvaluationValue(), id2.getEvaluationValue());
		assertEquals(true, id1.getTVector().equals(id2.getTVector()));
	}

	@Test
	void testcopyFrom() {
		TIndividual id1 = new TIndividual();
		id1.setEvaluationValue(1.0);

		id1.getTVector().setDimension(5);
		for(int i = 0; i < id1.getTVector().getDimension(); i++) {
			id1.getTVector().setElement(i, i);
		}

		TIndividual id2 = new TIndividual();
		id2.copyFrom(id1);

		assertEquals(id1.getEvaluationValue(), id2.getEvaluationValue());
		assertEquals(true, id1.getTVector().equals(id2.getTVector()));
	}

	@Test
	void testwriteTo() {
		TIndividual id1 = new TIndividual();
		id1.setEvaluationValue(1);
		id1.getTVector().setDimension(5);
		for(int i = 0; i < id1.getTVector().getDimension();  i++) {
			id1.getTVector().setElement(i, i);
		}

		try {
			File tmpFile = new File(".\\testwriteTo.txt");
			tmpFile.createNewFile();

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tmpFile)));
			id1.writeTo(pw);
			pw.close();

			BufferedReader br = new BufferedReader(new FileReader(tmpFile));
			assertEquals("1.0", br.readLine());
			assertEquals("5", br.readLine());
			assertEquals("0.0 1.0 2.0 3.0 4.0 ", br.readLine());
			br.close();

			if(tmpFile.exists()) {
				tmpFile.delete();
			}

		}catch(IOException e) {
			System.out.println(e);
		}
}

	@Test
	void readFrom() throws IOException{
		TIndividual id1 = new TIndividual();
		TIndividual id2 = new TIndividual();

		id1.setEvaluationValue(1);
		id1.getTVector().setDimension(5);
		for(int i = 0; i < id1.getTVector().getDimension();  i++) {
			id1.getTVector().setElement(i, i);
		}

		try {
			File tmpFile = new File(".\\testreadFrom.txt");
			tmpFile.createNewFile();

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tmpFile)));
			id1.writeTo(pw);
			pw.close();

			BufferedReader br = new BufferedReader(new FileReader(tmpFile));
			id2.readFrom(br);
			br.close();

			if(tmpFile.exists()) {
				tmpFile.delete();
			}

			assertEquals(id1.getEvaluationValue(), id2.getEvaluationValue());
			assertEquals(true, id1.getTVector().equals(id2.getTVector()));
		}catch(IOException e) {
			System.out.println(e);
		}

	}

	@Test
	void testtoString() {
		final double eval = 1.0;

		StringBuilder cmpstr = new StringBuilder();
		TIndividual id1 = new TIndividual();

		assertEquals(Double.toString(Double.NaN)+"\n", id1.toString());

		//setEvaluationValue
		id1.setEvaluationValue(eval);
		cmpstr.append(eval+"\n");

		//setTVector
		id1.getTVector().setDimension(10000);
		for(int i = 0; i < id1.getTVector().getDimension();  i++) {
			id1.getTVector().setElement(i, i);
			cmpstr.append((double)i+ " ");
		}

		assertEquals(cmpstr.toString(), id1.toString());
	}

	@Test
	void testgetTVector() {
		TIndividual id1 = new TIndividual();
		assertEquals(0, id1.getTVector().getDimension());
	}

	@Test
	void testgetEvaluationValue() {
		TIndividual id1 = new TIndividual();

		assertEquals(Double.NaN, id1.getEvaluationValue());
	}

	@Test
	void testsetEvaluationValue() {
		TIndividual id1 = new TIndividual();
		id1.setEvaluationValue(1.0);

		assertEquals(1.0, id1.getEvaluationValue());
	}
}
