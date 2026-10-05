import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ServerRunnable implements Runnable {
    private final Scanner in;
    private final PrintWriter out;

    public ServerRunnable(Socket socket) {
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        out.println("Author: Maxim Balinskij | Group: 4144 | Variant: 2 | Date and time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        out.println("Enter your array(separated by spaces):");

        while (in.hasNextLine()) {
            String msgClient = in.nextLine();

            try {
                if (msgClient.matches(".*[a-zA-Z].*")) {
                    out.println("- Please write only numbers separated by spaces!");
                    continue;
                }

                int[] array = Arrays.stream(msgClient.trim().split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                List<Integer> resultArray = getResult(array);

                out.println("Result: " + resultArray);

            } catch (NumberFormatException e) {
                out.println("Error: " + e.getMessage());
            }

            out.println("Enter your array(separated by spaces):");
        }
    }

    private static List<Integer> getResult(int[] array) {
        List<Integer> resultArray = new ArrayList<>();

        for (int i : array) {
            int curNumber = i;
            int sum = 0;

            while (curNumber > 0) {
                sum += curNumber % 10;
                curNumber /= 10;
            }

            resultArray.add(sum);
        }
        return resultArray;
    }

}
