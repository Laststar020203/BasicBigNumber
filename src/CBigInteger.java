/**
 * 
 * @author �ֽų�
 * @since 2019.04.02
 * 
 * ���� ���ϱ� �ۿ� ������ ���Ŀ� ���� �߰� ����
 *
 */
public class CBigInteger {

	private static long[] one;
	private static long[] two;

	private CBigInteger() {

	}

	/**
	 * 
	 * �ǿ����ڸ� �� �ڸ������ ������ longŸ�� �迭 one, two�� ���� �ε��� ���� ���Ѵ��� ���� ���� ���ڿ��� �ٽ� ��ȯ�� ����
	 * �ڸ������� ū �ڸ��� �������� ���ʴ�� ���Դϴ�. ����) ���ڸ����� �迭�� ����ִ� ���������� : 1 1 + 1 2 >>
	 * (1+1)+""+(1+2); result = 23 ���� ���� ���� ���� �ڸ� ���� �Ѿ�� ���(18�ڸ�) �� ���� 18�ڸ��� �������
	 * carry�� �߻����� ���� �ڸ� ���궧 1�� �� �մϴ�.
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

		boolean carry = false;

		int length = one.length() > two.length() ? two.length() / 18 + 1 : one.length() / 18 + 1;
		long[] temp = one.length() > two.length() ? CBigInteger.one : CBigInteger.two;

		final long compareDigit = 1000000000000000000L;
		StringBuilder builder = new StringBuilder();

		long t_value;

		for (int i = 0; i < length; i++) {
			t_value = CBigInteger.one[i] + CBigInteger.two[i] + (carry ? 1 : 0);
			carry = false;
			if (t_value / compareDigit == 1) {
				carry = true;
				String t_number = String.valueOf(t_value).substring(1);
				builder.insert(0, t_number);
				continue;
			}
			builder.insert(0, t_value);
		}

		// �ǿ����ڳ��� �ڸ����� �ȸ������ ū �ڸ����� ���� �ǿ������� ���� ���� �ֽ��ϴ�.
		for (int i = length; i < temp.length; i++) {
			t_value = temp[i] + (carry ? 1 : 0);
			builder.insert(0, t_value);
		}

		return printExpression(one, "+", two, builder);
	}

	/**
	 * 
	 * ���� ���� ���� ���� �����Ҽ� �ִ� longŸ���� �ִ� 19�ڸ����� ������ �� �����Ƿ� �� ���ڸ� 18�ڸ� ������ longŸ�� �迭��
	 * �����մϴ�. �� �ڸ��� ���� �� ������ ������ ���� �� �ڸ��ø��� ���Ͽ� �߻��ϴ� �����÷ο츦 �����ϱ� �����Դϴ�. ��Ȯ�� �ڸ��� ����
	 * ���ڸ������� 18�ڸ��� ������ �����մϴ�. �� �� ����� ���Ǹ� ���� ������ �迭�� reverse�մϴ�.
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

		CBigInteger.one = new long[oneLength];
		CBigInteger.two = new long[twoLength];

		for (index = 0; index < oneLength * 18; index += 18)
			CBigInteger.one[oneLength - index / 18 - 1] = Long
					.parseLong(one.substring(index, index + 18 > one.length() ? one.length() : index + 18));
		for (index = 0; index < twoLength * 18; index += 18)
			CBigInteger.two[twoLength - index / 18 - 1] = Long
					.parseLong(two.substring(index, index + 18 > two.length() ? two.length() : index + 18));

		// reverse
		for (int i = 0; i < CBigInteger.one.length / 2; i++) {
			long temp = CBigInteger.one[CBigInteger.one.length - 1 - i];
			CBigInteger.one[CBigInteger.one.length - 1 - i] = CBigInteger.one[i];
			CBigInteger.one[i] = temp;
		}

		for (int i = 0; i < CBigInteger.two.length / 2; i++) {
			long temp = CBigInteger.two[CBigInteger.two.length - 1 - i];
			CBigInteger.two[CBigInteger.two.length - 1 - i] = CBigInteger.two[i];
			CBigInteger.two[i] = temp;
		}

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

	public static void main(String[] args) {
		System.out.println(add("1", "0000000000000000000000000000000000000000000000000000000000"));

	}

}
