package org.satadev.nio;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static MetaCommandType doMetaCommand(String value) {
        switch (value) {
            case ".exit" -> {
                System.out.println("Well thats an end");
                return MetaCommandType.META_COMMAND_EXIT;
            }
            default -> {
                System.out.println("unrecognized command: " + value);
                return MetaCommandType.META_COMMAND_UNKNOWN;
            }
        }
    }

    public static PreparedPair doPrepareResult(String value) {

        if (value.toLowerCase().startsWith("insert")) {
            var stateMentWithoutInstruction =
                    value.substring(
                            StatementType.STATEMENT_INSERT.getStatementName().length() - 1,
                            value.length() - 1);
            var insertValues = stateMentWithoutInstruction.split(" ");

            if (insertValues.length != 3) {
                return new PreparedPair(PreparedResult.PREPARE_SYNTAX_ERROR, null);
            }
            var uid = Integer.parseInt(insertValues[0]);

            var row = new Row(uid, insertValues[1].toCharArray(), insertValues[2].toCharArray());
            var statement = new Statement(StatementType.STATEMENT_INSERT, row);
            return new PreparedPair(PreparedResult.PREPARE_SUCCESS, StatementType.STATEMENT_INSERT);
        }
        if (value.toLowerCase().startsWith("select")) {


            return new PreparedPair(PreparedResult.PREPARE_SUCCESS, StatementType.STATEMENT_SELECT);
        }
        return new PreparedPair(PreparedResult.PREPARE_UNRECOGNIZED_STATEMENT, null);


    }

    public static void executeStatement(PreparedPair preparedPair) {
        switch (preparedPair.statementType()) {
            case STATEMENT_INSERT -> {
                System.out.println("This is where we do insert");
                break;
            }
            case STATEMENT_SELECT -> {
                System.out.println("This is where we do select");
                break;
            }
            default -> {
                System.out.println("doesnot exists");
            }
        }

    }

    public static void main(String[] args) {

        while (true) {
            System.out.println(".db >");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                var value = bufferedReader.readLine();

                if (value.startsWith(".")) {
                    switch (doMetaCommand(value)) {
                        case META_COMMAND_EXIT -> {
                            System.exit(1);
                            continue;
                        }
                        case META_COMMAND_UNKNOWN -> {
                            System.out.println("We said it's unknown to us");
                            continue;
                        }
                        default -> System.exit(1);

                    }

                }
                var preparedPair = doPrepareResult(value);
                switch (preparedPair.preparedResult()) {
                    case PREPARE_SUCCESS -> {
                        break;
                    }
                    case PREPARE_UNRECOGNIZED_STATEMENT -> {
                        System.out.println("Unknown keyword");
                        continue;

                    }

                }

                executeStatement(preparedPair);
                System.out.println("executed");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}