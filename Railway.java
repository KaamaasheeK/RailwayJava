import java.util.*;

class Node {
    String name;
    int age;
    String gender;
    int ticketNo;
    int trainNo;
    Node next;

    Node(String name, int age, String gender, int ticketNo, int trainNo) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.ticketNo = ticketNo;
        this.trainNo = trainNo;
        this.next = null;
    }
}

class WaitingTicket {
    String name;
    int age;
    String gender;
    int trainNo;

    WaitingTicket(String name, int age, String gender, int trainNo) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.trainNo = trainNo;
    }
}

class Linked {
    Node head = null;
    int ticketCounter = 1000;
    final int MAX_BOOKINGS = 5;
    Queue<WaitingTicket> waitingList = new LinkedList<>();

    public int countConfirmedTickets() {
        int count = 0;
        Node temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    public void append(String name, int age, String gender, int trainNo) {
        if (countConfirmedTickets() < MAX_BOOKINGS) {
            Node obj = new Node(name, age, gender, ticketCounter++, trainNo);
            if (head == null) {
                head = obj;
            } else {
                Node temp = head;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = obj;
            }
            System.out.println("Ticket booked successfully for " + name);
        } else {
            waitingList.add(new WaitingTicket(name, age, gender, trainNo));
            System.out.println("Booking full. " + name + " added to the waiting list.");
        }
    }

    public void delete(String name) {
        if (head == null) {
            System.out.println("No bookings found.");
            return;
        }

        if (head.name.equalsIgnoreCase(name)) {
            System.out.println("Ticket deleted for: " + head.name);
            head = head.next;
            promoteFromWaitingList();
            return;
        }

        Node curr = head;
        Node prev = null;

        while (curr != null && !curr.name.equalsIgnoreCase(name)) {
            prev = curr;
            curr = curr.next;
        }

        if (curr == null) {
            System.out.println("No booking found for: " + name);
            return;
        }

        prev.next = curr.next;
        System.out.println("Ticket deleted for: " + name);
        promoteFromWaitingList();
    }

    public void promoteFromWaitingList() {
        if (!waitingList.isEmpty()) {
            WaitingTicket wt = waitingList.poll();
            append(wt.name, wt.age, wt.gender, wt.trainNo);
            System.out.println(wt.name + " from waiting list has been promoted to confirmed ticket.");
        }
    }

    public void display() {
        if (head == null) {
            System.out.println("No confirmed bookings available.");
        } else {
            Node temp = head;
            System.out.println("\nConfirmed Bookings:");
            while (temp != null) {
                System.out.println("----------------------------------------");
                System.out.println("Name      : " + temp.name);
                System.out.println("Age       : " + temp.age);
                System.out.println("Gender    : " + temp.gender);
                System.out.println("Ticket No : " + temp.ticketNo);
                System.out.println("Train No  : " + temp.trainNo);
                temp = temp.next;
            }
        }

        if (waitingList.isEmpty()) {
            System.out.println("\nNo passengers in the waiting list.");
        } else {
            System.out.println("\nWaiting List:");
            for (WaitingTicket wt : waitingList) {
                System.out.println("----------------------------------------");
                System.out.println("Name     : " + wt.name);
                System.out.println("Age      : " + wt.age);
                System.out.println("Gender   : " + wt.gender);
                System.out.println("Train No : " + wt.trainNo);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Linked bookingList = new Linked();

        while (true) {
            System.out.println("\n=== Train Ticket Booking System ===");
            System.out.println("1. Book Ticket");
            System.out.println("2. Delete Ticket by Name");
            System.out.println("3. Display All Bookings");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Name       : ");
                    String name = sc.next();
                    System.out.print("Enter Age        : ");
                    int age = sc.nextInt();
                    System.out.print("Enter Gender     : ");
                    String gender = sc.next();
                    System.out.print("Enter Train No   : ");
                    int trainNo = sc.nextInt();

                    bookingList.append(name, age, gender, trainNo);
                    break;

                case 2:
                    System.out.print("Enter Name to Delete Ticket: ");
                    String delName = sc.next();
                    bookingList.delete(delName);
                    break;

                case 3:
                    bookingList.display();
                    break;

                case 4:
                    System.out.println("Exiting... Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
