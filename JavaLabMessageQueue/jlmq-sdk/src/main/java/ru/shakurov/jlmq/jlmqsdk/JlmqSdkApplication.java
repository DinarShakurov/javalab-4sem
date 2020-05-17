package ru.shakurov.jlmq.jlmqsdk;

import ru.shakurov.jlmq.jlmqsdk.objects.JlmqConnector;
import ru.shakurov.jlmq.jlmqsdk.objects.JlmqConsumer;
import ru.shakurov.jlmq.jlmqsdk.objects.JlmqProducer;

import java.util.Scanner;


public class JlmqSdkApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JlmqConnector connector = Jlmq.connector()
                .withStomp()
                .port("8080")
                .connect();

        JlmqProducer producer = connector.producer()
                .toQueue("documents_for_generate")
                .create();

        JlmqConsumer consumer = connector.consumer()
                .subscribe("documents_for_generate")
                .onReceive(message -> {
                    message.accepted();
                    System.out.print("handling message: ");
                    System.out.println(message.getBody());
                    message.completed();
                })
                .create();


        while (scanner.hasNext()) {

            producer.send(scanner.nextLine());

        }
    }

}
