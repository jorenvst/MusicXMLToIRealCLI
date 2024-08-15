import core.Converter;
import core.FromFormat;
import core.ToFormat;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.Scanner;

@Command(
        name = "convert",
        description = "convert musicxml files to IReal Pro files"
)
public class Convert implements Runnable {

    @Parameters(
            description = "path to musicxml file to convert",
            index = "0"
    )
    private File file;

    @Option(
            names = {"-p", "--save-path"},
            description = "path to save the exported IReal Pro chart to"
    )
    private String savePath;

    @Override
    public void run() {
        if (savePath == null) {
            new Converter().setResolver(this::resolve).convertThenExport(FromFormat.MUSICXML, ToFormat.IREAL_PRO, file);
        } else {
            new Converter().setResolver(this::resolve).convertThenExport(FromFormat.MUSICXML, ToFormat.IREAL_PRO, file, savePath);
        }
    }

    private boolean resolve() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("replace already existing file?(y/n) ");
            return scanner.nextLine().equals("y");
        }
    }
}
