package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.minesweeper.Minesweeper;
import cleancode.minesweeper.tobe.minesweeper.config.GameConfig;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.Advanced;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.Middle;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleOutputHandler;

public class GameApplication {

    public static void main(String[] args) {

        GameConfig gameConfig = new GameConfig(
                new Advanced(),
                new ConsoleInputHandler(),
                new ConsoleOutputHandler()
        );
         Minesweeper minesweeper = new Minesweeper(gameConfig);
         minesweeper.initialize();
         minesweeper.run();
    }
    /**
     *  DIP(Dependency Inversion Principle) : 의존성 역전
     *
     *  DI(Dependency Injection) : 의존성 주입 - "제 3자가 의존성을 주입해주는 것"
     *  -> 주입은 IOC Container가 해준다.(Runtime시에 주입)
     *  -> 또한, IoC Container는 객체의 생성 및 생명 주기를 관리하여, 개발자는 객체의 생성 및 생명 주기를 신경 쓰지 않아도 된다.
     *  IoC(Inversion of Control) : 제어의 역전 - "제 3자가 제어권을 가지고 있는 것"
     *  -> 프로그램의 제어 흐름을 개발자가 결정하는 것이 아니라 프레임워크에서 결정하는 것
     *  -> 이미 만들어진 프레임 워크(Ex. Spring)에서 개발자가 코드를 작성해서 해당 프레임 워크를 실행하는 것(즉 실질적인 제어권을 프레임 워크가 가지고 있는 것)
     *
     */

}
