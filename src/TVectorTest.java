import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.jupiter.api.Test;

class TVectorTest {
	@Test
	void testcopyFrom() {
		TVector v1 = new TVector();
		v1.setDemension(10);
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, i);
		}

		TVector v2 = new TVector();
		v2.copyFrom(v1);
		assertEquals(true, v2.equals(v1));
	}

	@Test
	void testclone() {
		TVector v1 = new TVector();
		v1.setDemension(10);
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, i);
		}

		TVector v2 = v1.clone();
		assertEquals(true, v1.equals(v2));
	}

	@Test
	void testwriteTo() throws IOException{
		TVector v1 = new TVector();
		v1.setDemension(5);
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, i);
		}

		try {
			File tmpFile = new File(".\\testwriteTo.txt");
			tmpFile.createNewFile();

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tmpFile)));
			v1.writeTo(pw);
			pw.close();

			BufferedReader br = new BufferedReader(new FileReader(tmpFile));
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
	void testreadFrom() throws IOException{
		TVector v1 = new TVector();
		v1.setDemension(5);
		TVector v2 = new TVector();
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, i);
		}

		try {
			File tmpFile = new File(".\\testreadFrom.txt");
			tmpFile.createNewFile();

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tmpFile)));
			v1.writeTo(pw);
			pw.close();

			BufferedReader br = new BufferedReader(new FileReader(tmpFile));
			v2.readFrom(br);
			br.close();

			if(tmpFile.exists()) {
				tmpFile.delete();
			}

			assertEquals(true, v1.equals(v2));
		}catch(IOException e) {
			System.out.println(e);
		}
	}

	@Test
	void testtoString() { //0.33s -> (StringBuilder)0.02s
		StringBuilder cmpstr = new StringBuilder();
		TVector v1 = new TVector();

		v1.setDemension(10000);
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, i);
			cmpstr.append((double)i+" ");
		}

		assertEquals(cmpstr.toString(), v1.toString());
	}

	@Test
	void testequals() {
		TVector v1 = new TVector();
		TVector v2 = new TVector();
		v1.setDemension(10);
		v2.setDemension(10);
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, i);
			v2.setElement(i, i);
		}

		assertEquals(true, v1.equals(v2));
	}

	@Test
	void testadd() {
		TVector v1 = new TVector();
		TVector v2 = new TVector();
		v1.setDemension(10);
		v2.setDemension(10);
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, i);
			v2.setElement(i, 2*i);
		}

		v1.add(v1);
		assertEquals(v1, v2);
	}

	@Test
	void testsub() {
		TVector v1 = new TVector();
		TVector v2 = new TVector();
		v1.setDemension(10);
		v2.setDemension(10);
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, i);
			v2.setElement(i, 2*i);
		}

		v2.sub(v1);
		assertEquals(v1, v2);
	}

	@Test
	void testcalcurateL2Norm() {
		TVector v1 = new TVector();
		v1.setDemension(5);
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, 0.3);
		}

		assertEquals(Math.sqrt(0.3*0.3+0.3*0.3+0.3*0.3+0.3*0.3+0.3*0.3), v1.calcurateL2Norm(), 1e-10);
	}

	@Test
	void testnormalize() {
		TVector v1 = new TVector();
		v1.setDemension(5);
		TVector v2 = new TVector(v1);
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, 1);
			v2.setElement(i, 1/Math.sqrt(5));
		}

		assertEquals(true, v2.equals(v1.normalize()));
	}

	@Test
	void testInnerProduct() {
		TVector v1 = new TVector();
		v1.setDemension(5);
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, i);
		}


		assertEquals(1*1+2*2+3*3+4*4,v1.InnerProduct(v1));
	}

	@Test
	void testscalar() {
		TVector v1 = new TVector();
		v1.setDemension(5);
		TVector v2 = new TVector(v1);
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, i);
			v2.setElement(i, 2*i);
		}

		assertEquals(v2, v1.scalar(2));
	}

	@Test
	void testelementwiseProduct() {
		TVector v1 = new TVector();
		v1.setDemension(5);
		TVector v2 = new TVector(v1);
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, i);
			v2.setElement(i, i*i);
		}

		assertEquals(true, v2.equals(v1.elementwiseProduct(v1)));
	}

	@Test
	void testgetDimension() {
		TVector v1 = new TVector();
		assertEquals(0, v1.getDimension());
	}

	@Test
	void testsetDimension() {
		TVector v1 = new TVector();
		v1.setDemension(10);
		assertEquals(10,  v1.getDimension());
	}

	@Test
	void testgetElement() {
		TVector v1 = new TVector();
		v1.setDemension(10);
		assertEquals(0, v1.getElement(3));
	}

	@Test
	void testsetElement() {
		TVector v1 = new TVector();
		v1.setDemension(10);
		for(int i = 0; i < v1.getDimension(); i++) {
			v1.setElement(i, i);
		}

		for(int i = 0; i < v1.getDimension(); i++) {
			assertEquals(i, v1.getElement(i));
		}
	}

}
