/**
 * 
 * @author �ֽų�
 * @since 2019.04.02
 * 
 * ���� ���ϱ� �ۿ� ������ ���Ŀ� ���� �߰� ����
 *
 */
public class CBigIntegerOP {

	private static long[] one;
	private static long[] two;
	public static long[] value;
	private static final long compareDigit = 1000000000000000000L;

	private CBigIntegerOP() {

	}
	
	/**
	 * 
	 * @param number �ǿ�����
	 * @param square �ǿ������� ����� ���Ұ���?
	 * @return
	 */
	public static String Square(int number , int square) {
		String value = number+"";
		
		for (int i = 1; i < square; i++) {
			value = add(value, value);
		}	
		
		return value;
	}
	

	/**
	 * 
	 * �ǿ����ڸ� �� �ڸ������ ������ longŸ�� �迭 one, two�� ���� �ε��� ���� ���Ѵ��� ���� ���� ���ڿ��� �ٽ� ��ȯ�� ����
	 * �ڸ������� ū �ڸ��� �������� ���ʴ�� ���Դϴ�. ����) ���ڸ����� �迭�� ����ִ� ���������� : 1 1 + 1 2 >>
	 * (1+1)+""+(1+2); result = 23 ���� ���� ���� ���� �ڸ� ���� �Ѿ�� ���(18�ڸ�) �� ���� 18�ڸ��� �������
	 * carry�� �߻����� ���� �ڸ� ���궧 1�� �� //..///'[�մϴ�.
	 * 
	 * @param one ������ ù��° �ǿ�����(���ڿ�)
	 * @param two ������ ù��° �ǿ�����(���ڿ�)
	 * @return one + two;
	 * 
	 * 
	 * 
	 */
	public static String add(String one, String two) {

		
		one = isProPerNumber(one) ? one : replaceProPerNumber(one);
		two = isProPerNumber(two) ? two : replaceProPerNumber(two);

		subNumber(one, two);

		byte carry = 0;

		int length = one.length() > two.length() ? two.length() / 18 + 1 : one.length() / 18 + 1;
		long[] temp = one.length() > two.length() ? CBigIntegerOP.one : CBigIntegerOP.two;

		StringBuilder builder = new StringBuilder();

		long t_value;

		for (int i = 0; i < length; i++) {
			t_value = CBigIntegerOP.one[i] + CBigIntegerOP.two[i] + carry;
			carry = 0;
			if (t_value / compareDigit == 1) {
				carry = 1;
				String t_number = String.valueOf(t_value).substring(1);
				builder.insert(0, t_number);
				continue;
			}
			
			if(t_value == 0) break;

			builder.insert(0, t_value);
		}

		// �ǿ����ڳ��� �ڸ����� �ȸ������ ū �ڸ����� ���� �ǿ������� ���� ���� �ֽ��ϴ�.
		for (int i = length; i < temp.length; i++) {
			t_value = temp[i] + carry;
			if(t_value == 0) break;
			builder.insert(0, t_value);
		}

		return builder.toString();
	}
	

	/**
	 * 
	 * ���� ���� ���� ���� �����Ҽ� �ִ� longŸ���� �ִ� 19�ڸ����� ������ �� �����Ƿ� �� ���ڸ� 18�ڸ� ������ longŸ�� �迭��
	 * �����մϴ�. �� �ڸ��� ���� �� ������ ������ ���� �� �ڸ��ø��� ���Ͽ� �߻��ϴ� �����÷ο츦 �����ϱ� �����Դϴ�. ��Ȯ�� �ڸ��� ����
	 * ���ڸ������� 18�ڸ��� ������ �����մϴ�.
	 * 
	 * @param one ������ ù��° �ǿ�����(���ڿ�) ���ڿ��� ������ ������ long ������ ����� ���ڿ� ���� ������ ��������
	 *            �����Դϴ�.
	 * @param two ������ ù��° �ǿ�����(���ڿ�)
	 * 
	 */
	private static void subNumber(String one, String two) {	
		int oneLength = one.length() / 18 + 1;
		int twoLength = two.length() / 18 + 1;
		int index = 0;
		
		CBigIntegerOP.one = new long[oneLength];
		CBigIntegerOP.two = new long[twoLength];
		for (index = one.length(); index > 0 ; index -= 18) { 
	
			CBigIntegerOP.one[(oneLength - 1) - index / 18] = Long
					.parseLong(one.substring(index - 18 < 0 ? 0 : index-18, index));
		}
		for (index = two.length(); index > 0 ; index -= 18)
			CBigIntegerOP.two[(twoLength - 1) - index / 18] = Long
					.parseLong(two.substring(index - 18 < 0 ? 0 : index-18, index));
	}

	/**
	 * ���ڿ��� ���� ���ڰ� �ùٸ��� Ȯ��
	 * 
	 * @param str
	 * @return �ùٸ� ���ڶ�� true�� ���� �ƴϸ� false
	 * 
	 */
	private static boolean isProPerNumber(String str) {

		// ���� ���� ���� null�� ���
		if (str == null)
			return false;

		// �߰��� ���ڿ��� ���ڰ� ���� �ִ°��
		for (char m : str.toCharArray()) {
			if (m < '0' && m > '9')
				return false;
		}

		// �տ� �������� 0�� �� ���
		if (str.charAt(0) == '0' && str.length() > 1)
			return false;

		return true;
	}

	/**
	 * 
	 * �߸��� ���ڸ� �ùٸ� ���ڷ� �ٲ��ش� �ùٸ� ���ڰ� �ƴ϶�� �ܼ���
	 * exception �� �߻���Ű�� ����� �����Ƿ� ���ڿ����� ������� �޸� �Һ� ���ϹǷ� �״���
	 * �����ִ� �޼ҵ�� �ƴ�
	 * 
	 * @param str
	 * @return �ùٸ��� �ٲ� ���ڸ� ����, �׷��� ���� ������ �ùٸ� ���ڰ� ���� ��� �״�� ����
	 * 
	 * 
	 */
	@Deprecated
	private static String replaceProPerNumber(String str) {
		if (isProPerNumber(str))
			return str; // �ùٸ� ������ ��� �ٲ� �ʿ䰡 ������ ����

		final String s_0 = "0";
		final char c_0 = '0';

		// if Null
		if (str == null)
			return s_0;

		// if 000000000
		boolean isZero = true;
		for (char c : str.toCharArray()) {
			if (c != c_0)
				isZero = false;
		}
		if (isZero)
			return s_0;

		// if �������� 0
		if (str.charAt(0) == c_0) {
			int index = 0;
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) != c_0) {
					index = i;
					break;
				}
			}
			return str.substring(index);
		}

		System.out.println("ó������ ���� �߸��� ���ڰ� �ֽ��ϴ�.");
		return str;
	}

	/**
	 * ���� ������ݴϴ�.
	 * 
	 * @param one     ù��° �ǿ�����
	 * @param op      �߰��� ���� �����ȣ
	 * @param two     �ι�° �ǿ�����
	 * @param builder ��� ��
	 * @return ���ڵ��� ���� ���� ���ڿ� ���� �����մϴ�.
	 * 
	 * 
	 */
	private static String printExpression(String one, String op, String two, StringBuilder builder) {

		builder.insert(0, " = ");
		builder.insert(0, two);
		builder.insert(0, " ");
		builder.insert(0, op);
		builder.insert(0, " ");
		builder.insert(0, one);
		return builder.toString();
	}	

	/**
	 * ���� ������ݴϴ�.
	 * 
	 * @param one     ù��° �ǿ�����
	 * @param op      �߰��� ���� �����ȣ
	 * @param two     �ι�° �ǿ�����
	 * @param builder ��� ��
	 * @return ���ڵ��� ���� ���� ���ڿ� ���� �����մϴ�.
	 * 
	 * 
	 */
	@SuppressWarnings("unused")
	private static String printExpression(String one, String op, String two, String value) {

		StringBuilder builder = new StringBuilder();

		builder.append(one);
		builder.append(" ");
		builder.append(op);
		builder.append(" ");
		builder.append(two);
		builder.append(" = ");
		builder.append(value);

		return value.toString();
	}


}
