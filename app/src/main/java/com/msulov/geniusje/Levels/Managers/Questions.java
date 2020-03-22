package com.msulov.geniusje.Levels.Managers;

import java.util.Random;

public class Questions {


    public final static int TYPE_MATH = 0,TYPE_CHEMIC = 1,TYPE_INF = 2;

    private static String[][] questions = {
            {
                    "Что такое биссектриса?",
                    "Продолжите ряд: делитель , делимое , ?",
                    "Формула дискременанта:",
                    "Синус - это",
                    "Теорема косинусов",
                    "Теорема Пифагора",
                    "Промежуток [1,4] - это",
                    "Сумма углов в треугольнике",
                    "Основание пирамиды",
                    "Острый угол"
            },
            {
                    "Из чего состоят молекулы?",
                    "Как обозначается Свинец?",
                    "Что из этого химическое явление?",
                    "Что из этого физическое явление?",
                    "Как обозначается Ртуть?",
                    "Что из этого ложь?",
                    "Как звали Менделеева?",
                    "Формула хлороводорода",
                    "Что умеет кислота?",
                    "Что означает 'валентные электроны'?"
            },
            {
                    "2 в двоичной это",
                    "26 в шестнадцатеричной это",
                    "256 в двоичной это",
                    "Что такое электрический ток?",
                    "1024 мегабайт это",
                    "Что больше:байт или бит?",
                    "Что делает транзистор?",
                    "1 нанометр - это",
                    "Что больше:1000(в двоичной) или 8(в десятичной)?",
                    "Чему равняется 1 в булевой логике?",
            }
    };

    private static String[][][] answers = {
            {
                    {"Крыса , бегущая по углам","Окружность","Число , с плавающей точкой","Луч, делящий угол пополам"},
                    {"Кенгуру","Причастное","Причастие","Частное"},
                    {"a^2+b^2","b^2+4ac","d=abc","b^2-4ac"},
                    {"отношение гипотенузы к прилежащему катету","отношение противолежащего катета к гипотенузе","отношение противолежащего катека к прилжащему","отношение прилежащего катета к противолежащего"},
                    {"a^2+b^2+c^2","b^2+4ac","a^2+b^2+c^2-abc","a^2+b^2-2abcos(A)"},
                    {"b^2+4ac","b^2-4ac","V=abc","a^2+b^2"},
                    {"1,2,3","2,3","2,3,4","1,2,3,4"},
                    {"270","90","360","180"},
                    {"Прямоугольник","Круг","Параллелограмм","Квадрат"},
                    {"<45","<=45","<90",">=90"}
            },
            {
                    {"Из воды","Из азона","Из бозонов","Из атомов"},
                    {"Mg","Na","Sb","Pb"},
                    {"Распространение запахов","Растворение сахара","Превращение воды в лед","Горение"},
                    {"Коррозия","Скисание молока","Почернение серебра","Растворение сахара"},
                    {"Ra","Fr","Ru","Hg"},
                    {"Ртуть-металл","Вода состоит из водорода и кислорода","Число атомов записывается с помощью индексов","в молекуле серной кислоты 3 атома водорода"},
                    {"Алексей","Сергей","Иван","Дмитрий"},
                    {"HOCl","HgSO","BaS","HCl"},
                    {"Способна принимать бозон углерода","Способна принимать анод кислорода","Способна отдавать протон серы","Способна отдавать катион водорода"},
                    {"электроны-протоны","бозоновские электроны","свободные электроны","электронная пара"}
            },
            {
                    {"101","2","100","10"},
                    {"28","36","32","38"},
                    {"1000110000","100001001","101000101","100000000"},
                    {"Движение незаряженных атомов водорода","Движение незаряженных электродов","Движение заряженных диодов","Движение заряженных частиц"},
                    {"1 Пбайт","1 Кбайт","1024 Байтов","1 Гигабайт"},
                    {"Бит","Равны","Нет правильного ответа","Байт"},
                    {"Изолирует микросхему от внешнего поля","Упорядочивает ток","Нейтрализует перекос фаз","Управляет большим потоком тока посредством маленького"},
                    {"10 в минус 6 степени метра","10 в минус 12 степени метра","10 в минус 8 степени метра","10 в минус 9 степени метра"},
                    {"1000","8","Равны","Нет правильного ответа"},
                    {"false","Отсутствие условия задачи","Нет правильного ответа","true"},
            }

    };



    public static String[][] getRandomData(int type){
        Random random = new Random();
        String[][] data;
        int current_type = 0;
        switch (type){
            case TYPE_MATH:
                current_type = TYPE_MATH;
                break;
            case TYPE_CHEMIC:
                current_type = TYPE_CHEMIC;
                break;
            case TYPE_INF:
                current_type = TYPE_INF;
                break;
        }

        int number = random.nextInt(answers[current_type].length);
        String[] answers = getAnswers(current_type,number);
        assert answers != null;
        String answer = answers[answers.length-1];
        Memory_game.getShakedArray(answers);
        data = new String[][]{{getQuestion(current_type, number)}, answers, {Shaked_words.getIndexOfRandomWord(answer,answers)}};

        return data;
    }


    private static String[] getAnswers(int type,int number_of_question){
        switch (type){
            case TYPE_MATH:
                return answers[TYPE_MATH][number_of_question];
            case TYPE_CHEMIC:
                return answers[TYPE_CHEMIC][number_of_question];
            case TYPE_INF:
                return answers[TYPE_INF][number_of_question];
        }
        return null;
    }


    private static String getQuestion(int type, int number_of_question){
        switch (type){
            case TYPE_MATH:
                return questions[TYPE_MATH][number_of_question];
            case TYPE_CHEMIC:
                return questions[TYPE_CHEMIC][number_of_question];
            case TYPE_INF:
                return questions[TYPE_INF][number_of_question];
        }
        return null;
    }



}
