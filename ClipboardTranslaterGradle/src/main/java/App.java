import Bootstrap.App.EApp;
import Bootstrap.Parts.AbstractBootstrap;
import Bootstrap.Tools.Alog;
import MVVM.Parts.Model.DetectClipboard;
import MVVM.Parts.Model.GetTranslateInfo;

import java.util.Scanner;

/***
 * @author Aaron Chen
 */
/*
* App Description:
*   This Application is for people learning vocabulary in English. Couldn't for commercial use
*   When user copy a vocabulary will Popup a small window automatically
*   that show some information about this vocabulary.
*   User can save this vocabulary and use "Anki" app for study those vocabulary which you had saved.
*
* To Do List:
* I.Research:
*   V1.How to get clipboard string
*   V2.How to get website info
*   3.Popup a small window at corner
*   4.How to open web browser
*   5.Save in a .txt file use html format
*
* II.Function:
*   1.Copy a vocabulary then show a small popup window at corner
*   2.Information include
*       1.Vocabulary definition in English
*       2.Vocabulary definition in Chinese
*       3.Vocabulary example
*       4.Vocabulary image
*   3.Click will show on web browser
*   4.Save this Vocabulary use a special html format which can import by use "Anki" app
*   5.Setting function-let user can setting
*       1.is windows popup automatically?
*       2.Max showing Example count
* * */
public class App extends AbstractBootstrap {
    static App app;

    public App(String[] CommandLineArguments) {
        super(CommandLineArguments);
    }

    public static void main(String[] args) {
        Alog.logStartup("Started App Main");
        app = new App(args);
    }

    @Override
    public void OnInitialized() {
        DetectClipboard.DetectClipboardThread timeSetter = new DetectClipboard.DetectClipboardThread() {
            @Override
            public void detectClipboardStringChange(String newString) {
                System.out.println(newString);
                GetTranslateInfo.getByVoiceTube(newString);
                GetTranslateInfo.getByOxford(newString);
            }
        };
        Thread t = new Thread(timeSetter);
        t.start();
    }

    @Override
    public void OnShutdown() {

    }

    @Override
    public void OnApplicationUpdate() {
        Scanner input = new Scanner(System.in);
        String exit = input.nextLine();
        if (exit.toLowerCase().equals("exit")) {
            setAppState(EApp.ShuttingDown);
        }
    }
}