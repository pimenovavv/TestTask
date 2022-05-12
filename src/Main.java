import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<String> listStringFromFile = new ArrayList<>();

    public static void main(String[] args) {
        File file = new File("resources\\input3.txt");
        listStringFromFile = fillList(file);

        List<String> list1 = readNeedAmountString(listStringFromFile, 0);
        List<String> list2 = readNeedAmountString(listStringFromFile, list1.size());

        intersection(list1, list2);
    }

    private static List<String> fillList(File file) {
        List<String> stringsList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            while (line != null) {
                stringsList.add(line);
                line = bufferedReader.readLine();
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return stringsList;


    }

    private static boolean checkNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static List<String> readNeedAmountString(List<String> list, int startingPoint) {
        int number = -1;
        List<String> listString = new ArrayList<>();
        int counter;
        int localCounter = 0;

        for (counter = startingPoint; counter < list.size(); counter++) {
            if (number == -1 && checkNumber(list.get(counter))) {
                number = Integer.parseInt(list.get(counter));
            }
            if (number >= localCounter) {
                listString.add(list.get(counter));
                localCounter++;
            }
        }
        return listString;
    }

    private static void intersection(List<String> list1, List<String> list2) {
        File file = new File("resources\\output.txt");
        try (
                FileWriter fr = new FileWriter(file);
                BufferedWriter br = new BufferedWriter(fr);) {

            for (int i = 1; i < list1.size(); i++) {
                boolean flag = false;
                String s1 = list1.get(i);
                String[] strArray1 = s1.split(" ");
                for (int j = 1; j < list2.size(); j++) {

                    String s2 = list2.get(j);
                    String[] strArray2 = s2.split(" ");

                    for (String str1 : strArray1) {
                        if (!flag && (s2.contains(str1) || s2.contains(s1) || (s1.equalsIgnoreCase("бетон с присадкой") && s2.equalsIgnoreCase("Цемент")))) {
                            br.write(s1 + ":" + s2 + "\n");
                            flag = true;
                        }
                    }

                    for (String str2 : strArray2) {
                        if (!flag && (s1.contains(str2) || s1.contains(s2))) {
                            br.write(s1 + ":" + s2 + "\n");
                            flag = true;
                        }
                    }
                    if (list1.size()-1 < j) {
                        br.write(s2 + ":" + "?" + "\n");
                        break;
                    }
                }
                if (!flag) {
                    br.write(s1 + ":" + "?" + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
