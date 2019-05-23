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
	pos.X = x;             //������
	pos.Y = y;            //������
	SetConsoleCursorPosition(hOut, pos);
}
HANDLE hOut = GetStdHandle(STD_OUTPUT_HANDLE);//������ʾ���������


int main()
{
	//����������
	srand((unsigned int)time(NULL));
	//�Ƿ�������־
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

	cout << "�÷�Ϊ��" << snake.getSorce() << "��" << endl;
	//gotoxy(hOut, 10, 5);//��Ϊ�������" " ����x*2

	//�����û�����
	while (!isDead)
	{
		char key = _getch();

		//�����һ�ΰ������ ���ܼ�����Ϸ
		if (preKey == NULL && key == snake.LEFT)
		{
			continue;
		}

		do
		{
			//�ж��Ƿ��ǰ���������
			if (key == snake.UP || key == snake.DOWN || key == snake.LEFT || key == snake.RIGHT)
			{
				//�жϱ��ΰ����Ƿ����ϴγ�ͻ
				if ((key == snake.LEFT && preKey == snake.RIGHT) ||
					(key == snake.RIGHT && preKey == snake.LEFT) ||
					(key == snake.UP && preKey == snake.DOWN) ||
					(key == snake.DOWN && preKey == snake.UP))
				{
					key = preKey;
				}
				else
				{
					//���ǳ�ͻ����
					preKey = key;
				}


				preKey = key;

				if (snake.move(key) == true)
				{
					//�ƶ��ɹ�
					//system("cls");
					//wall.drawWall();
					gotoxy(hOut, 0, Wall::ROW);
					cout << "�÷�Ϊ��" << snake.getSorce() << "��" << endl;
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
				key = preKey; //�����������������Ϊ�ϴ��ƶ��ķ���
			}


		} while (!_kbhit()); //��û�м�������ʱ������0
	}


	

/*
  ����
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


