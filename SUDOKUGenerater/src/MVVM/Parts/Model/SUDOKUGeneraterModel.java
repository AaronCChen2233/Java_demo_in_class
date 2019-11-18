package MVVM.Parts.Model;

import Bootstrap.Tools.CSVReaderWriter;
import Bootstrap.Tools.RandomTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SUDOKUGeneraterModel {
    public SUDOKUGeneraterModel() {

    }

    public void generate(int difficulty) {
        String[][] SUDOKUTable = new String[9][9];
        String[][] SUDOKUQuestionTable = new String[9][9];
        ArrayList<String> numberList = new ArrayList(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
        int difficultyPercent = 46 + (difficulty * 4);
        String choseNumber = "";
        int tryTime = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                numberList = new ArrayList(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
                do {
                    if (numberList.size() != 0) {
                        choseNumber = numberList.get(RandomTools.getRandomIntbetween(numberList.size() - 1, 0));
                        /*chosen number remove*/
                        numberList.remove(choseNumber);
                    } else {
                        tryTime++;
                        /*if numberList.size() is 0 mean it try all number and all number couldn't allow so this line generate again*/
                        j = 0;
                        numberList = new ArrayList(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
                        SUDOKUTable[i] = new String[9];

                        /*try too many times this SUDOKUTable generate fail regenerate again*/
                        /*maybe this isn't a good solution but at least now will leave Infinite loop */
                        if (tryTime > 15) {
                            i = 0;
                            j = 0;
                            tryTime = 0;
                            SUDOKUTable = new String[9][9];
                        }
                        continue;
                    }
                } while (!allowPutChecker(SUDOKUTable, i, j, choseNumber));

                /*after allowPutChecker put number*/
                SUDOKUTable[i][j] = choseNumber;
            }
            tryTime = 0;
        }

        ArrayList<String> list1;
        String csvPath = System.getProperty("user.dir") + "\\SUDOKUTable.csv";

        /*Random take out numbers to become question*/
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SUDOKUQuestionTable[i][j] = RandomTools.getRandomBooleanByPercent(difficultyPercent) ? "" : SUDOKUTable[i][j];
            }
        }

        /*Create file*/
        /*Write Question first*/
        CSVReaderWriter.writer(csvPath, "Question");
        for (int i = 0; i < 9; i++) {
            list1 = new ArrayList<String>();
            Collections.addAll(list1, SUDOKUQuestionTable[i]);
            CSVReaderWriter.pushWriter(csvPath, String.join(",", list1));
        }

        CSVReaderWriter.pushWriter(csvPath, " ");

        /*Write Answer*/
        CSVReaderWriter.pushWriter(csvPath, "Answer");
        for (int i = 0; i < 9; i++) {
            list1 = new ArrayList<String>();
            Collections.addAll(list1, SUDOKUTable[i]);
            CSVReaderWriter.pushWriter(csvPath, String.join(",", list1));
        }
    }

    private boolean allowPutChecker(String[][] table, int checki, int checkj, String checkNumberString) {
        try {
            /*check Row and Col*/
            for (int j = 0; j < 8; j++) {
                if (checkNumberString.equals(table[checki][j])) {
                    return false;
                }
            }

            for (int i = 0; i < 8; i++) {
                if (checkNumberString.equals(table[i][checkj])) {
                    return false;
                }
            }

            /*Check in SmallBlock*/
            int indexInSmallBlocki = (checki / 3);
            int indexInSmallBlockj = (checkj / 3);

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (checkNumberString.equals(table[indexInSmallBlocki * 3 + i][indexInSmallBlockj * 3 + j])) {
                        return false;
                    }
                }
            }
        } catch (Exception ex) {
            return false;
        }

        return true;
    }
}
