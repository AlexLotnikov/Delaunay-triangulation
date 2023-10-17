package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class MyPanel extends JPanel implements MouseListener {
    public MyPanel() {
        addMouseListener(this); // добавляем к текущей Панели обработчик мыши
    }
    static public int[][] dot = new int[1001][3]; // объявление массива точек (0 - координата по Х, 1 -координата по Y)
    static public int[][][] stretch = new int[1001][1001][4]; //объявление массива отрезков
    static public int num = 0, goodnum;
    public static boolean status = false; // объявление статусной переменной
    public void paint(Graphics g) {//Рисование окна
        super.paint(g);// Очищение окна, нарисованного до этого
        for (int i = 0; i< num; i++) // вырисование точек в окне
		{
            g.setColor(Color.blue);
            g.drawOval(dot[i][0]-3, dot[i][1]-3, 5, 5);
            g.fillOval(dot[i][0]-3, dot[i][1]-3, 5, 5);

        }
        if (status == true) { // запуск программы при нажатии Enter
            sortbyOy(); // сортировка массива точек по коорденате y


            otronConv(); //поиск отрезка на выпуклой оболочке множества точек
            stretch[0][goodnum][3] = 1; // изменяем статуст отрезка на выпуклой облочке на "взят в триангуляцию"
            stretch[goodnum][0][3] = 1;
            g.setColor(Color.BLUE); // прорисовка отрезка 
            g.drawLine(dot[0][0], dot[0][1], dot[goodnum][0], dot[goodnum][1]);
            dfs(0, goodnum);// восстанавливаем триангуляию
            double a1, b1, c1, a2, b2, c2, x0, y0, r; // прорисовка полученных отрезков и окружностей
            double  x1, y1, x2, y2, x3, y3;
            for (int x = 0; x < num; x++) {
                for (int y = 0; y < num; y++) {
                    if (stretch[x][y][3] == 1) {



                            g.setColor(Color.BLUE);
                            g.drawLine(dot[x][0], dot[x][1], dot[y][0], dot[y][1]); // прорисовка отрезка

                            x1 = dot[x][0]; // запоминаем коорлинаты
                            y1 = dot[x][1];
                            x2 = dot[y][0];
                            y2 = dot[y][1];
                            x3 = dot[ stretch[x][y][1] ][0];
                            y3 = dot[ stretch[x][y][1] ][1];
                            a1 = x2-x1; // проводим серединные перпендикуляры к сторонам треугольника
                            b1 = y2-y1;
                            c1 = (x1*x1-x2*x2)*0.5+(y1*y1-y2*y2)*0.5;
                            a2 = x2-x3; // проводим серединные перпендикуляры к сторонам треугольника
                            b2 = y2-y3;
                            c2 = (x3*x3-x2*x2)*0.5+(y3*y3-y2*y2)*0.5; 
                            x0 = (c2*b1-c1*b2)/(a1*b2-a2*b1); //пересекаем серединные перпендикуляры
                            y0 = (c2*a1-c1*a2)/(b1*a2-b2*a1);
                            r = Math.sqrt((x1-x0)*(x1-x0)+(y1-y0)*(y1-y0)); // считаем ралиус описанной окружности
                            g.setColor(Color.GREEN); // рисуем окружность
                            g.drawOval((int)Math.floor(x0+0.5)-(int)Math.floor(r+0.5), (int)Math.floor(y0+0.5)-(int)Math.floor(r+0.5), 2*(int)Math.floor(r+0.5), 2*(int)Math.floor(r+0.5));
							// проделываем аналогичные операции со второй окружностью
                            x3 = dot[ stretch[x][y][2] ][0];
                            y3 = dot[ stretch[x][y][2] ][1];
                            a1 = x2-x1;
                            b1 = y2-y1;
                            c1 = (x1*x1-x2*x2)*0.5+(y1*y1-y2*y2)*0.5;
                            a2 = x2-x3;
                            b2 = y2-y3;
                            c2 = (x3*x3-x2*x2)*0.5+(y3*y3-y2*y2)*0.5;
                            x0 = (c2*b1-c1*b2)/(a1*b2-a2*b1);
                            y0 = (c2*a1-c1*a2)/(b1*a2-b2*a1);
                            r = Math.sqrt((x1-x0)*(x1-x0)+(y1-y0)*(y1-y0));
                            g.setColor(Color.GREEN);
                            g.drawOval((int)Math.floor(x0+0.5)-(int)Math.floor(r+0.5), (int)Math.floor(y0+0.5)-(int)Math.floor(r+0.5), 2*(int)Math.floor(r+0.5), 2*(int)Math.floor(r+0.5));



                    }
                }
            }

        }
    }
    static public void match(int a, int b)
    {
            double minup,mindown, cs, A, B, C, alpha, beta;
            int mindotup, mindotdown, goodnum, x1, y1, x2, y2, x3, y3;
            x1 = dot[a][0]; // запоминанием координаты первого конца отрезка
            y1 = dot[a][1];
            x2 = dot[b][0]; // запоминание коорлинаты второго конца отрезка
            y2 = dot[b][1];
            A = y1 - y2; //определяем коэфициенты прямой содержащей данный отрезок
            B = x2 - x1;
            C = x1 * y2 - x2 * y1;
            if (a != b) // елси две точки несовпадаем начинаем перебирать точки
                {
                    minup = 2; // минимальный косинус соответсвующего для точек НАД прямой
                    mindotup = -1; // номер точки с минимальным косином угла среди тоек НАД прямой
                    mindown = 2; // минимальный косинус соответсвующего для точек ПОД прямой
                    mindotdown = -1; // минимальный номер точки с минимальным косином угла среди тоек ПОД прямой
                    for (int c = 0; c < num; c++)
                    {
                        x3 = dot[c][0]; // запоминаем координаты точки
                        y3 = dot[c][1];
                        double x11 = x1 - x3; // сдвигаем вектора из с в концы отерзкы так, чтобы с попала в (0,0)
                        double y11 = y1 - y3;
                        double x21 = x2 - x3;
                        double y21 = y2 - y3;
                        cs = (x11 * x21 + y11 * y21) / (Math.sqrt(x11 * x11 + y11 * y11) * Math.sqrt(x21 * x21 + y21 * y21)); //считаем косинус угла между векторами по формуле
                        if (A * x3 + B * y3 + C >= 0) // определение расположение точки относитлеьно прямой
                        {
                            if (cs < minup) // обновление минимального косинуса
                            {
                                minup = cs;
                                mindotup = c;
                            }
                        } else { // определение расположение точки относитлеьно точки
                            if (cs < mindown) // обновление минимального косинуса
                            {
                                mindown = cs;
                                mindotdown = c;
                            }
                        }

                    }

                    if (mindotup == -1) //дозаполнение парных точек в случае их отсутствия
                    {
                        mindotup = mindotdown;

                    }
                    if (mindotdown == -1) {
                        mindotdown = mindotup;
                    }
                    stretch[a][b][1] = mindotup; //запоминаем найденные парные точки
                    stretch[a][b][2] = mindotdown;
                    stretch[b][a][1] = mindotup; //запоминаем найденные парные точки
                    stretch[b][a][2] = mindotdown;
                    if (minup != 2 && mindown != 2) { // проверка возможности нахождения отрезка в триангуляции
                        alpha = Math.acos(minup);
                        beta = Math.acos(mindown);
                        if (alpha + beta >= Math.PI) {
                            stretch[a][b][0] = 1;
                        }
                    }


                }



    }

    static public void dfs (int a, int b) // реализует метод dfs
    {
        match(a, b); // поиск соответствующих точек
        stretch[a][b][3]=1; // постановка маркера наличия в триангуляции
        stretch[b][a][3]=1;
        if(stretch[a][stretch[a][b][1]][3]==0) // проверка наличии в триангуляции, в противном случае - запуск dfs
        {
            dfs(a, stretch[a][b][1] );
        }
        if(stretch[a][stretch[a][b][2]][3]==0) // проверка наличии в триангуляции, в противном случае - запуск dfs
        {
            dfs(a, stretch[a][b][2] );
        }
        if(stretch[b][stretch[a][b][1]][3]==0) // проверка наличии в триангуляции, в противном случае - запуск dfs
        {
            dfs(b, stretch[a][b][1] );
        }
        if(stretch[b][stretch[a][b][2]][3]==0) // проверка наличии в триангуляции, в противном случае - запуск dfs
        {
            dfs(b, stretch[a][b][2] );
        }

    }

    static public void sortbyOy() //сортировка массива точек по коорлинате y поплавковым методом
    {
        int a, b, c, d;
        for (int i = 0; i < num - 1; i++) {
            if (dot[i][1] > dot[i + 1][1]) {
                a = dot[i][0];
                b = dot[i][1];
                c = dot[i + 1][0];
                d = dot[i + 1][1];
                dot[i][0] = c;
                dot[i][1] = d;
                dot[i + 1][0] = a;
                dot[i + 1][1] = b;
                sortbyOy();
            }

        }
    }
    static public void otronConv( ) // поиск отрезка на выпуклой оболочке методом заметающей прямой
    {
        double xx = dot[0][0]; // берем самую левую точку
        double yy = dot[0][1];
        double t = -1, t0;
        double x0, y0;
        goodnum = -1;
        for (int i = 0; i < num; i++) { // ищем точек отрезок от начлаьно до которой наименее отклонен от оси y
            x0 = dot[i][0]; //запоминаем координаты
            y0 = dot[i][1];
            t0 = Math.abs(x0 - xx)*1.0 / Math.sqrt((xx - x0) * (xx - x0) + (yy - y0) * (yy - y0)); // считаем косинус искомого угла
            if (t0 > t)
            {
                t = t0;
                goodnum = i;
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent mouseEvent) {//Нажатие мыши
        if(mouseEvent.getButton() == MouseEvent.BUTTON1){
            dot[num][0] = mouseEvent.getX();
            dot[num][1] = mouseEvent.getY();
            System.out.println(dot[num][0] + " " + dot[num][1] + "/");
            num++;
            repaint();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {//Отпускание мыши

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
