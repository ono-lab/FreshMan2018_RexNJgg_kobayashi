package myjgoalga;
public class TKtablet {
	/**
	 * k-tablet関数
	 * @param v : TVector ベクトル
	 * @return 計算した評価値
	 */
	public static double ktablet(TVector v) {
		int k = (int)((double)v.getDimension()/4.0);
		double ret = 0.0;
		for(int i = 0; i  < v.getDimension(); i++) {
			double vi = v.getElement(i);
			if(i < k) {
				ret += vi * vi;
			}else {
				ret += 10000.0 * vi * vi;
			}
		}

		return ret;
	}
}
