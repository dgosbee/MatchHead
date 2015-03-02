package matchhead.outputformatter;

public class ConsoleOutputFormatter implements ConsoleOutputFormatable {

    @Override
    public void formatOutput(String output) {
        System.out.println(output);
    }

}
