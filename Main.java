package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Integer.valueOf;

public class Main extends JFrame implements  KeyListener{ // Приложение – наследник JFrame (окна)
    public Main(String title) {// Конструктор приложения
        super(title); // создать окно с указанным заголовком
        setSize(1920, 1080);// задаем размер окна приложения
        setDefaultCloseOperation( EXIT_ON_CLOSE ); // при закрытии окна заканчиваться
        JMenuBar menuBar = new JMenuBar(); // Создание строки главного меню
        menuBar.add(createFileMenu()); // Добавление в главное меню выпадающих пунктов
        setJMenuBar(menuBar); // Подключаем меню к интерфейсу приложения
        MyPanel panel = new MyPanel(); // создаем Панель, на которой можно будет рисовать
        add(panel);// подключаем Панель к текущему JFrame (окну)
        addKeyListener(this);
        setVisible(true); // делаем окно видимым
    }
    private JMenu createFileMenu() // создание меню приложения
    {
        JMenu file = new JMenu("Файл"); // Создание выпадающего меню
        JMenuItem open = new JMenuItem("Открыть"); // Пункт меню "Открыть"
        JMenuItem exit = new JMenuItem("Выход"); // Пункт меню "Выход"
        file.add(open); // Добавим в меню пункта "Открыть"
        file.addSeparator(); // Добавим в меню разделитель
        file.add(exit); // Добавим в меню пункт "Выход"
        JFileChooser fileChooser = new JFileChooser(); // Создадим объект,
        // умеющий показывать диалог выбора файла
        addKeyListener(this);
        open.addActionListener(new ActionListener() { // Действие при выборе меню "Открыть"
            @Override
            public void actionPerformed(ActionEvent arg0) {
                fileChooser.setDialogTitle("Открытие файла");// Заголовок окна диалога
                if (fileChooser.showOpenDialog(Main.this) ==
                        JFileChooser.APPROVE_OPTION) { // если файл был выбран
                    System.out.println("Выбран файл " + fileChooser.getSelectedFile());//Вывод имени выбранного файла
                }
                try {

                } catch (Exception e) {//Внутри команды код, который показывает что нужно делать в незапланированной ситуации
                    e.printStackTrace();
                }
            }
        });
        exit.addActionListener(new ActionListener() { // Действие при выборе меню "Выход"
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        return file; // возвращаем построенное меню как результат метода
    }

    public static void main(String[] args) throws IOException {
        Main mw = new Main("Delone");//Задание названия файла
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {//Нажатие клавиши
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            MyPanel.status = true;
            System.out.println(MyPanel.status);
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {//Отпускание клавиши

    }
}