
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

        public class test {
            private static final Map<String, Integer> digitsConverter;
            static {
                digitsConverter = new HashMap<>();
                digitsConverter.put("I", 1);
                digitsConverter.put("II", 2);
                digitsConverter.put("III", 3);
                digitsConverter.put("IV", 4);
                digitsConverter.put("V", 5);
                digitsConverter.put("VI", 6);
                digitsConverter.put("VII", 7);
                digitsConverter.put("VIII", 8);
                digitsConverter.put("IX", 9);
                digitsConverter.put("X", 10);
            }
            public static void main(String[] args) throws Exception {

                String x1 = "";
                String x2 = "";
                String tempResult = "";
                String operator = "";
                String operators = "+-*/";
                int result = 0;
                NumberFormat numberFormat = NumberFormat.UNKNOWN;


                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String s = br.readLine();

                for(int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if(numberFormat != NumberFormat.ROME && Character.isAlphabetic(c)) {
                        if (numberFormat == NumberFormat.ARABIC) {
                            throw new Exception("Уже были использованы арабские цифры, использование римских запрещено");
                        }
                        numberFormat = NumberFormat.ROME;
                    } else if (numberFormat != NumberFormat.ARABIC && Character.isDigit(c)) {
                        if (numberFormat == NumberFormat.ROME) {
                            throw new Exception("Уже были использованы римские цифры, использование арабских запрещено");
                        }
                        numberFormat = NumberFormat.ARABIC;
                    }
                    if(numberFormat == NumberFormat.ARABIC ? Character.isDigit(c) : Character.isAlphabetic(c)) {
                        tempResult = tempResult + c;
                        continue;
                    }
                    if(operators.contains(String.valueOf(c))) {
                        if(!operator.isEmpty()) {
                            throw new Exception("Ошибка! Недопустимо использование двух операторов!");
                        }
                        x1 = tempResult;
                        operator = String.valueOf(c);
                        tempResult = "";
                        continue;
                    }
                    if(!Character.isSpaceChar(c)) {
                        throw new Exception("Ошибка, некорректный символ!");
                    }
                }
                if(operator.isEmpty()) {
                    throw new Exception("Пропущен знак операции!");
                }
                x2 = tempResult;
/*      System.out.println("x1 = " + x1);
        System.out.println("x2 = " + x2);
        System.out.println("operator = " + operator);
 */
                switch (operator) {
                    case("+"):
                        result = getIntFromString(x1, numberFormat) + getIntFromString(x2, numberFormat);
                        break;
                    case("-"):
                        result = getIntFromString(x1, numberFormat) - getIntFromString(x2, numberFormat);
                        break;
                    case("*"):
                        result = getIntFromString(x1, numberFormat) * getIntFromString(x2, numberFormat);
                        break;
                    case("/"):
                        result = getIntFromString(x1, numberFormat) / getIntFromString(x2, numberFormat);
                        break;
                }

                System.out.println(getStringFromInt(result, numberFormat));
            }
            public static int getIntFromString(String number, NumberFormat numberFormat) throws Exception {
                if (numberFormat == NumberFormat.ARABIC){
                    int  result = Integer.valueOf(number);
                    if(result > 10) {
                        throw new Exception("Ошибка. Некорректное арабское число!");
                    }
                    else return result;
                } else if (numberFormat == NumberFormat.ROME){
                    if (!digitsConverter.containsKey(number)){
                        throw new Exception("Ошибка. Некорректное римское число!");
                    }
                    return digitsConverter.get(number);
                }
                return -1;
            }
            public static String getStringFromInt(int number, NumberFormat numberFormat) throws Exception {
                if (numberFormat == NumberFormat.ARABIC) {
                    return String.valueOf(number);
                } else if (numberFormat == NumberFormat.ROME) {
                    if(number < 0) {
                        throw new Exception("Ошибка! Отрицательный результат при римских цифрах");
                    }
                    String result = "";
                    while(number >= 10) {
                        result = result + "X";
                        number = number - 10;
                    }
                    if(number > 0) {
                        for (Map.Entry<String, Integer> entry : digitsConverter.entrySet()) {
                            if (Objects.equals(number, entry.getValue())) {
                                result = result + entry.getKey();
                            }
                        }
                    }
                    return result;
                }
                return null;
            }

            enum NumberFormat {
                ARABIC,
                ROME,
                UNKNOWN
            }
        }