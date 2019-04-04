/**
 * 
 * @author 최신념
 * @since 2019.04.02
 * 
 * 아직 더하기 밖에 못만듬 추후에 뺄셈 추가 예정
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
	 * @param number 피연산자
	 * @param square 피연산자의 몇승을 구할건지?
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
	 * 피연산자를 각 자리별대로 저장한 long타입 배열 one, two를 같은 인덱스 끼리 더한다음 나온 값을 문자열로 다시 변환해 작은
	 * 자리수부터 큰 자리수 방향으로 차례대로 붙입니다. 예시) 한자리마다 배열에 들어있다 가정했을때 : 1 1 + 1 2 >>
	 * (1+1)+""+(1+2); result = 23 만약 더한 값이 지정 자리 수를 넘어갔을 경우(18자리) 그 값을 18자리로 맞춘다음
	 * carry를 발생시켜 다음 자리 연산때 1을 더 //..///'[합니다.
	 * 
	 * @param one 들어오는 첫번째 피연산자(문자열)
	 * @param two 들어오는 첫번째 피연산자(문자열)
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

		// 피연산자끼리 자리수가 안맞을경우 큰 자리수를 가진 피연산자의 남은 값을 넣습니다.
		for (int i = length; i < temp.length; i++) {
			t_value = temp[i] + carry;
			if(t_value == 0) break;
			builder.insert(0, t_value);
		}

		return builder.toString();
	}
	

	/**
	 * 
	 * 가장 많은 정수 값을 저장할수 있는 long타입이 최대 19자리까지 저장할 수 있으므로 각 숫자를 18자리 나누어 long타입 배열에
	 * 저장합니다. 한 자리를 적게 한 이유는 연산을 했을 때 자리올림에 대하여 발생하는 오버플로우를 방지하기 위합입니다. 정확한 자리를 위해
	 * 뒷자리수부터 18자리씩 나누어 저장합니다.
	 * 
	 * @param one 들어오는 첫번째 피연산자(문자열) 문자열에 저장한 이유는 long 범위를 벗어나는 숫자에 대한 정보를 가져오기
	 *            위함입니다.
	 * @param two 들어오는 첫번째 피연산자(문자열)
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
	 * 문자열로 들어온 숫자가 올바른지 확인
	 * 
	 * @param str
	 * @return 올바른 숫자라면 true를 리턴 아니면 false
	 * 
	 */
	private static boolean isProPerNumber(String str) {

		// 들어온 문자 값이 null인 경우
		if (str == null)
			return false;

		// 중간에 숫자외의 문자가 섞여 있는경우
		for (char m : str.toCharArray()) {
			if (m < '0' && m > '9')
				return false;
		}

		// 앞에 쓸데없는 0을 쓴 경우
		if (str.charAt(0) == '0' && str.length() > 1)
			return false;

		return true;
	}

	/**
	 * 
	 * 잘못된 숫자를 올바른 숫자로 바꿔준다 올바른 숫자가 아니라면 단순히
	 * exception 을 발생시키는 방법이 있으므로 문자열수가 많을경우 메모리 소비가 심하므로 그다지
	 * 쓸모있는 메소드는 아님
	 * 
	 * @param str
	 * @return 올바르게 바꾼 숫자를 리턴, 그러나 인자 값으로 올바른 숫자가 들어올 경우 그대로 리턴
	 * 
	 * 
	 */
	@Deprecated
	private static String replaceProPerNumber(String str) {
		if (isProPerNumber(str))
			return str; // 올바른 숫자인 경우 바꿀 필요가 없으니 리턴

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

		// if 쓸데없는 0
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

		System.out.println("처리되지 않은 잘못된 숫자가 있습니다.");
		return str;
	}

	/**
	 * 식을 만들어줍니다.
	 * 
	 * @param one     첫번째 피연산자
	 * @param op      중간에 넣을 연산부호
	 * @param two     두번째 피연산자
	 * @param builder 결과 값
	 * @return 인자들을 통해 만든 문자열 식을 리턴합니다.
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
	 * 식을 만들어줍니다.
	 * 
	 * @param one     첫번째 피연산자
	 * @param op      중간에 넣을 연산부호
	 * @param two     두번째 피연산자
	 * @param builder 결과 값
	 * @return 인자들을 통해 만든 문자열 식을 리턴합니다.
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
