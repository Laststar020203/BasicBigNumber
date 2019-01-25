
public class BasicBigDecimal {

	public static String divide(double firstNum, double secondNum , int digit) {

		StringBuilder sb = new StringBuilder();

		while (((int) firstNum) - firstNum > 0 || ((int) secondNum) - firstNum > 0) {
			secondNum *= 10;
			firstNum *= 10;
		}

		int value = (int) (firstNum / secondNum);

		sb.append(value);

		if (firstNum % secondNum == 0) {
			return sb.toString();
		} else {
			sb.append(".");
			firstNum -= secondNum * value;

			for (int count = 0; count <= digit; count++) {

				while (!((int) (firstNum / secondNum) >= 1)) {
					firstNum *= 10;
					if (!(firstNum / secondNum >= 1))
						sb.append("0");
				}

				value = (int) (firstNum / secondNum);

				sb.append(value);

				if (sb.length() % 25 == 0)
					sb.append("\n");

				firstNum -= secondNum * value;

				if (firstNum == 0)
					break;

			}
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		double x = 7878;
		double y = 6786786.657567234;
		System.out.println(BasicBigDecimal.divide(x, y , 435));
		System.out.println(x / y);
	}

}
