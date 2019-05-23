#define _CRT_SECURE_NO_WARNINGS
#include<iostream>
#include "Wall.h"
#include"snake.h"
#include "food.h"
#include <ctime>
#include<conio.h>
#include<Windows.h>

using namespace std;


void gotoxy(HANDLE hOut, int x, int y)
{
	COORD pos;
	pos.X = x;             //横坐标
	pos.Y = y;            //纵坐标
	SetConsoleCursorPosition(hOut, pos);
}
HANDLE hOut = GetStdHandle(STD_OUTPUT_HANDLE);//定义显示器句柄变量


int main()
{
	//添加随机种子
	srand((unsigned int)time(NULL));
	//是否死亡标志
	bool isDead = false;
	char preKey = NULL;

	Wall wall;
	wall.initWall();
	wall.drawWall();

	Food food(wall);
	food.setFood();

	Snake snake(wall, food);
	snake.initSnake();

	/*snake.move('w');
	snake.move('w');
	snake.move('a');*/




	gotoxy(hOut, 0, Wall::ROW);

	cout << "得分为：" << snake.getSorce() << "分" << endl;
	//gotoxy(hOut, 10, 5);//因为多输出了" " 所以x*2

	//接收用户输入
	while (!isDead)
	{
		char key = _getch();

		//如果第一次按了左键 不能激活游戏
		if (preKey == NULL && key == snake.LEFT)
		{
			continue;
		}

		do
		{
			//判断是否是按了其它键
			if (key == snake.UP || key == snake.DOWN || key == snake.LEFT || key == snake.RIGHT)
			{
				//判断本次按键是否与上次冲突
				if ((key == snake.LEFT && preKey == snake.RIGHT) ||
					(key == snake.RIGHT && preKey == snake.LEFT) ||
					(key == snake.UP && preKey == snake.DOWN) ||
					(key == snake.DOWN && preKey == snake.UP))
				{
					key = preKey;
				}
				else
				{
					//不是冲突按键
					preKey = key;
				}


				preKey = key;

				if (snake.move(key) == true)
				{
					//移动成功
					//system("cls");
					//wall.drawWall();
					gotoxy(hOut, 0, Wall::ROW);
					cout << "得分为：" << snake.getSorce() << "分" << endl;
					Sleep(snake.getSleepTime());//300ms
				}
				else
				{
					isDead = true;
					break;
				}
			}
			else
			{
				key = preKey; //如果按了其他键，变为上次移动的方向
			}


		} while (!_kbhit()); //当没有键盘输入时，返回0
	}


	

/*
  测试
  wall.setWall(5, 4, '=');
  wall.setWall(5, 5, '=');
  wall.setWall(5, 6, '@');
*/


/*
  cout << wall.getWall(0, 0) << endl;
  cout << wall.getWall(5, 4) << endl;
  cout << wall.getWall(5, 6) << endl;
  cout << wall.getWall(1, 1) << endl;
*/

	system("pause");
	return EXIT_SUCCESS;
}


