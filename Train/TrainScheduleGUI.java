import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainScheduleGUI extends JFrame {
    private TrainSchedule trainSchedule;
    private JTextArea outputTextArea;
    private JTextField inputField;
    private int currentChoice;

    public TrainScheduleGUI() {
        trainSchedule = new TrainSchedule();

        setTitle("Train Schedule App");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu Panel
        JPanel menuPanel = new JPanel(new GridLayout(6, 1));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Menu Items
        Map<Integer, String> menuItems = new HashMap<>();
        menuItems.put(1, "View train schedule");
        menuItems.put(2, "Add train to schedule");
        menuItems.put(3, "Add destination to schedule");
        menuItems.put(4, "View destinations");
        menuItems.put(5, "About us");
        menuItems.put(6, "Exit");

        // Add menu buttons
        for (Map.Entry<Integer, String> menuItem : menuItems.entrySet()) {
            JButton button = new JButton(menuItem.getValue());
            button.addActionListener(new MenuButtonListener(menuItem.getKey()));
            menuPanel.add(button);
        }

        add(menuPanel, BorderLayout.WEST);

        // Output Text Area
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputField = new JTextField();
        inputField.addActionListener(new EnterButtonListener());
        inputPanel.add(inputField, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void start() {
        displayOutput("Menu\n-------------------");
        displayOutput("1. View train schedule");
        displayOutput("2. Add train to schedule");
        displayOutput("3. Add destination to schedule");
        displayOutput("4. View destinations");
        displayOutput("5. About us");
        displayOutput("6. Exit");
    }

    private void displayOutput(String output) {
        outputTextArea.append(output + "\n");
    }

    private void processChoice() {
        switch (currentChoice) {
            case 1:
                trainSchedule.viewTrainSchedule();
                break;
            case 2:
                displayOutput("Enter train name:");
                break;
            case 3:
                displayOutput("Enter destination:");
                break;
            case 4:
                trainSchedule.viewDestinations();
                break;
            case 5:
                aboutUs();
                break;
            case 6:
                exit();
                break;
        }
    }

    private void aboutUs() {
        displayOutput("About Us\n-------------------\nThis app was created by student KN-23 Hirkovyi Roman.");
    }

    private void exit() {
        displayOutput("Exiting the application.");
        System.exit(0);
    }
    private class MenuButtonListener implements ActionListener {
        private int choice;
        public MenuButtonListener(int choice) {
            this.choice = choice;
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            currentChoice = choice;
            processChoice();
        }
    }
    
    private class EnterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = inputField.getText().trim();
            inputField.setText("");
            if (currentChoice == 2) {
                addTrain(input);
            } else if (currentChoice == 3) {
                addDestination(input);
            }
        }
    
        private void addTrain(String trainName) {
            displayOutput("Enter departure time (hh:mm):");
            String departureTime = inputField.getText().trim();
            inputField.setText("");
    
            displayOutput("Enter destination:");
            String destination = inputField.getText().trim();
            inputField.setText("");
    
            Train train = new Train(trainName, departureTime, destination);
            trainSchedule.addTrain(train);
            displayOutput("Train added to schedule.");
        }
    
        private void addDestination(String destination) {
            trainSchedule.addDestination(destination);
            displayOutput("Destination added to schedule.");
        }
    }
    
    class Train {
        private String name;
        private String departureTime;
        private String destination;
    
        public Train(String name, String departureTime, String destination) {
            this.name = name;
            this.departureTime = departureTime;
            this.destination = destination;
        }
    
        public String getName() {
            return name;
        }
    
        public String getDepartureTime() {
            return departureTime;
        }
    
        public String getDestination() {
            return destination;
        }
    }
    
    class TrainSchedule {
        private List<Train> trains;
        private List<String> destinations;
    
        public TrainSchedule() {
            trains = new ArrayList<>();
            destinations = new ArrayList<>();
            Train train1 = new Train("743 Lviv", "10:00", "Kyiv");
            Train train2 = new Train("91 Kyiv", "21:00", "Donetsk");
            trains.add(train1);
            trains.add(train2);
            destinations.add("Kyiv");
            destinations.add("Donetsk");
        }
    
        public void addTrain(Train train) {
            trains.add(train);
        }
    
        public void addDestination(String destination) {
            destinations.add(destination);
        }
    
        public void viewTrainSchedule() {
            if (trains.size() == 0) {
                displayOutput("No trains available.");
            } else {
                displayOutput("-------------------\nTrain Schedule\n-------------------");
                for (Train train : trains) {
                    displayOutput(train.getName() + " (" + train.getDepartureTime() + ") - " + train.getDestination());
                }
            }
        }
    
        public void viewDestinations() {
            if (destinations.size() == 0) {
                displayOutput("No destinations available.");
            } else {
                displayOutput("Destinations\n-------------------");
                for (String destination : destinations) {
                    displayOutput(destination);
                }
            }
        }
    
        private void displayOutput(String output) {
            outputTextArea.append(output + "\n");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TrainScheduleGUI trainScheduleGUI = new TrainScheduleGUI();
                trainScheduleGUI.start();
            }
        });
    }
}    
