import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.List;

public class JPanelTest {

    public int correctNumber = getRandomNumber();
    public int wrongNumber = getRandomNumber();
    private JLabel animeInfoLabel;

    public void createPanel() {
        List<Anime> animeList = Anime.getAnimes();
        int correctNumber = this.correctNumber;
        int wrongNumber = this.wrongNumber;
        int firstNumber = 0;
        int secondNumber = 0;
        Random rand = new Random();
        int iterator = rand.nextInt()%2;
        switch (iterator) {
            case 0:
                firstNumber = wrongNumber;
                secondNumber = correctNumber;
            case 1:
                secondNumber = wrongNumber;
                firstNumber = correctNumber;
            default:
                break;
        }

        JFrame frame = new JFrame("Pick the right number");
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1500, 700);
        panel.setBackground(Color.lightGray);
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        JButton buttonLeft = new JButton(Integer.toString(firstNumber));
        buttonLeft.setBackground(Color.white);
        JButton buttonRight = new JButton(Integer.toString(secondNumber));
        buttonRight.setBackground(Color.white);
        panel.add(buttonLeft);
        panel.add(buttonRight);
        ChuckNorrisJokes chuckNorrisJokes = new ChuckNorrisJokes();
        JLabel label = new JLabel(chuckNorrisJokes.getJoke());
        panel.add(label);
        JButton buttonJoke = new JButton("GET JOKE");
        buttonJoke.setBackground(Color.white);
        buttonJoke.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                label.setText(chuckNorrisJokes.getJoke());
            }
        });
        panel.add(buttonJoke);

        animeInfoLabel = new JLabel("Select an anime to view details.");
        panel.add(animeInfoLabel);

        JButton buttonShowAnime = new JButton("Show Anime Info");
        buttonShowAnime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!animeList.isEmpty()) {
                    Anime anime = animeList.get(0);
                    animeInfoLabel.setText(anime.toString());
                } else {
                    animeInfoLabel.setText("No anime available.");
                }
            }
        });
        panel.add(buttonShowAnime);
        frame.add(panel);
        frame.setLayout(null);
        frame.setSize(1500, 700);
        frame.setVisible(true);
        buttonLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                boolean result = checkValue(buttonLeft.getText());
                if (result) {
                    System.out.println("You have selected the right number");
                } else {
                    System.out.println("You have selected the wrong number");
                }
            }
        });

        buttonRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                boolean result = checkValue(buttonRight.getText());
                if (result) {
                    System.out.println("You have selected the wrong number");
                } else {
                    System.out.println("You have selected the wrong number");
                }
            }
        });

    }

    public static int getRandomNumber() {
        Random random = new Random();
        int lowNumber = 1;
        int highNumber = 11;
        return random.nextInt(highNumber - lowNumber) + lowNumber;
    }

    public boolean checkValue(String value) {
        return Objects.equals(value, Integer.toString(this.correctNumber));
    }
}
