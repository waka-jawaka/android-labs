package algie.lab1.calc;

/**
 * Created by me on 01.10.17.
 */

class Calculus {
    private StringBuilder x = new StringBuilder();
    private StringBuilder y = new StringBuilder();
    private String operation;

    boolean isInitialized() {
        if (!x.toString().isEmpty() && !y.toString().isEmpty() &&
                (operation != null && !operation.isEmpty())) {
            return true;
        }
        return false;
    }

    void updateArguments(String digit) {
        if (operation == null || operation.isEmpty()) {
            this.x.append(digit);
        } else {
            this.y.append(digit);
        }
    }

    void updateOperation(String operation) {
        this.operation = operation;
    }

    void restart() {
        this.x = new StringBuilder();
        this.y = new StringBuilder();
        this.operation = "";
    }

    double calculate() {
        double x = Double.parseDouble(this.x.toString());
        double y = Double.parseDouble(this.y.toString());
        double result = 0;
        switch (this.operation) {
            case "+": result = x + y; break;
            case "-": result = x - y; break;
            case "*": result = x * y; break;
            case "/": result = x / y;
        }
        restart();
        return result;
    }
}
